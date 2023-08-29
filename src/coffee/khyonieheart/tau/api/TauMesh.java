package coffee.khyonieheart.tau.api;

import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.tau.Tau;
import coffee.khyonieheart.tau.api.gl.TauGLBuffered;
import coffee.khyonieheart.tau.api.gl.TauGLHandleOwner;
import coffee.khyonieheart.tau.api.gl.TauGLRenderer;
import coffee.khyonieheart.tau.api.texture.Texture;

public class TauMesh implements TauGLBuffered<Integer>
{
	private int vaoHandle = Integer.MIN_VALUE;
	private int vboHandle = Integer.MIN_VALUE;
	private float[] verticies;
	private Texture texture;
	private float[] texCoords;

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
		GL30.glEnableVertexAttribArray(0);
		
		if (this.texture != null)
		{
			GL30.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
			GL30.glEnableVertexAttribArray(1);

			TauGLRenderer renderer = Tau.getContainer(Thread.currentThread()).getRenderer();
			renderer.bind(null);
			GL20.glUniform1i(GL20.glGetUniformLocation(renderer.getShader().getHandle(), "meshTexture"), this.texture.getHandle());
		}

		GL30.glBindVertexArray(0);
	}

	public void setTexture(
		@NotNull Texture texture,
		float... texCoords
	) {
		Objects.requireNonNull(texture);
		if (texCoords.length / 2 != this.verticies.length / 3)
		{
			throw new IllegalArgumentException("Unequal number of vertex and texture coordinates");
		}

		this.texture = texture;
		this.texCoords = texCoords;
	}

	public void applyTexture() 
	{
		if (this.vaoHandle == Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Cannot apply a texture to a mesh without a VAO handle");
		}

		if (this.vboHandle != Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Cannot apply a texture to a mesh that has already been buffered into memory");
		}
		
		GL30.glBindVertexArray(this.vaoHandle);

		float[] newVerticies = new float[(this.verticies.length / 3) * 5];
		for (int i = 0; i < newVerticies.length / 5; i++)
		{
			// Copy vertex data
			newVerticies[i * 5] = this.verticies[i * 3];
			newVerticies[(i * 5) + 1] = this.verticies[(i * 3) + 1];
			newVerticies[(i * 5) + 2] = this.verticies[(i * 3) + 2];

			// Copy texture coordinates
			newVerticies[(i * 5) + 3] = this.texCoords[i * 2];
			newVerticies[(i * 5) + 4] = this.texCoords[(i * 2) + 1];
		}

		this.verticies = newVerticies;

		logFormat();

		GL30.glBindVertexArray(0);
	}

	public void logFormat()
	{
		if (this.texture != null)
		{
			for (int i = 0; i < verticies.length / 5; i++)
			{
				TauLogger.log(this, "Vertex " + i + ": [ " + this.verticies[i * 5] + ", " + this.verticies[(i * 5) + 1] + ", " + this.verticies[(i * 5) + 2] + " ], Tex coords: [" + this.verticies[(i * 5) + 3] + ", " + this.verticies[(i * 5) + 4] + "]");
			}

			return;
		}

		for (int i = 0; i < verticies.length / 3; i++)
		{
			TauLogger.log(this, "Vertex " + i + ": [ " + this.verticies[i * 5] + ", " + this.verticies[(i * 5) + 1] + ", " + this.verticies[(i * 5) + 2] + " ]");
		}
	}
	
	public void render()
	{
		if (this.texture != null)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getHandle());
		}

		GL30.glBindVertexArray(this.vaoHandle);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, this.verticies.length);
		GL30.glBindVertexArray(0);
	}
}
