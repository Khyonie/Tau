package coffee.khyonieheart.tau.api.texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.annotation.Range;
import coffee.khyonieheart.tau.api.TauLogger;
import coffee.khyonieheart.tau.api.gl.TauGLBuffered;
import coffee.khyonieheart.tau.api.gl.TauGLHandleOwner;
import coffee.khyonieheart.util.RuntimeConditions;

public class Texture implements TauGLBuffered<Integer>
{
	private int handle = Integer.MIN_VALUE;
	private ByteBuffer data;
	private int width;
	private int height;

	public Texture(
		@NotNull ByteBuffer data,
		@Range(min = 1) int width,
		@Range(min = 1) int height
	) {
		Objects.requireNonNull(data);
		RuntimeConditions.requireMinimum(width, 1);
		RuntimeConditions.requireMinimum(height, 1);

		this.data = data;
		this.width = width;
		this.height = height;
	}

	public ByteBuffer getImageData()
	{
		return this.data;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	@Override
	public Integer allocate(TauGLHandleOwner owner) 
	{
		if (this.handle != Integer.MIN_VALUE)
		{
			return this.handle;
		}

		this.handle = GL11.glGenTextures();

		owner.addHandle(this);
		return this.handle;
	}

	@Override
	public void release(TauGLHandleOwner owner) 
	{
		GL11.glDeleteTextures(this.handle);

		owner.removeHandle(this);

		this.handle = Integer.MIN_VALUE;
	}

	@Override
	public Integer getHandle() 
	{
		return this.handle;
	}

	@Override
	public void buffer() 
	{
		if (this.handle == Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Cannot buffer a texture into memory without a handle.");
		}

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.handle);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

		TauLogger.log(this, "Texture has been loaded");

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static Texture loadFromPNG(
		@NotNull String filepath
	) throws 
		FileNotFoundException,
		IllegalArgumentException,
		IOException
	{
		Objects.requireNonNull(filepath);
		File file = new File(filepath);

		if (!file.exists())
		{
			throw new FileNotFoundException("No such file \"" + file.getAbsolutePath() + "\"");
		}

		try (MemoryStack stack = MemoryStack.stackPush())
		{
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);

			ByteBuffer data = STBImage.stbi_load(filepath, w, h, channels, 4);

			return new Texture(data, w.get(), h.get());
		}
	}
}
