package coffee.khyonieheart.tau.api;

import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.tau.api.gl.TauGLBuffered;
import coffee.khyonieheart.tau.api.gl.TauGLHandleOwner;
import coffee.khyonieheart.tau.api.texture.Texture;

public class TauMesh implements TauGLBuffered<Integer>
{
	private static int globalIndex = 0;

	private int vaoHandle = Integer.MIN_VALUE;
	private int vboHandle = Integer.MIN_VALUE;
	private float[] verticies;
	private int textureHandle;
	private Texture texture;
	private float[] texCoords;
	private int index;

	public TauMesh(
		float... verticies
	) {
		if (verticies.length == 0)
		{
			throw new IllegalArgumentException("Cannot buffer a mesh with 0 verticies");
		}

		this.verticies = verticies;
	}

	@Override
	public Integer allocate(TauGLHandleOwner owner) 
	{
		this.vaoHandle = GL30.glGenVertexArrays();
		owner.addHandle(this);

		return vaoHandle;
	}

	@Override
	public void release(TauGLHandleOwner owner) 
	{
		GL30.glDeleteVertexArrays(this.vaoHandle);
		GL15.glDeleteBuffers(this.vboHandle);

		owner.removeHandle(this);
	}

	@Override
	public Integer getHandle() 
	{
		return this.vaoHandle;
	}

	@Override
	public void buffer() 
	{
		if (this.vaoHandle == Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Cannot buffer a mesh into memory without a VAO handle");
		}

		GL30.glBindVertexArray(this.vaoHandle);

		this.vboHandle = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vboHandle);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticies, GL15.GL_STATIC_DRAW);

		GL30.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, (3 + (texture == null ? 0 : 2)) * Float.BYTES, 0);
		GL30.glEnableVertexAttribArray(this.index = globalIndex++); // I both love and hate that this works
		GL30.glBindVertexArray(0);
	}

	public void setTexture(
		@NotNull Texture texture,
		float... texCoords
	) {
		Objects.requireNonNull(texture);

		if (this.vaoHandle == Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Cannot set a texture to a mesh without a VAO handle");
		}
		
		GL30.glBindVertexArray(this.vaoHandle);

		this.texCoords = texCoords;
		this.textureHandle = GL11.glGenTextures();

		GL30.glBindVertexArray(0);
	}
	
	public void render()
	{
		GL30.glEnableVertexAttribArray(this.index);
		GL30.glBindVertexArray(this.vaoHandle);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.verticies.length);
		GL30.glBindVertexArray(0);
	}
}
