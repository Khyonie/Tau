package coffee.khyonieheart.tau.api.texture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.annotation.Range;
import coffee.khyonieheart.util.RuntimeConditions;

public class Texture
{
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

	private ByteBuffer data;
	private int width;
	private int height;

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
