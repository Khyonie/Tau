package coffee.khyonieheart.tau;

import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import coffee.khyonieheart.tau.api.gl.TauGLHandleOwner;
import coffee.khyonieheart.tau.api.gl.TauGLShaderProgram;

public class TauBasicShader implements TauGLShaderProgram
{
	private int handle = Integer.MIN_VALUE;

	private static final String VERTEX_SHADER = 
"""
#version 330 core
layout (location = 0) in vec3 position;

void main()
{
	gl_Position = vec4(position.x, position.y, position.z, 1.0);
}
""";

	private static final String FRAGMENT_SHADER = 
"""
#version 330 core
out vec4 FragColor;

void main()
{
	FragColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
}
""";

	private String vertexShaderSource = VERTEX_SHADER;
	private String fragmentShaderSource = FRAGMENT_SHADER;

	@Override
	public Integer allocate(TauGLHandleOwner owner) 
	{
		this.handle = GL20.glCreateProgram();

		owner.addHandle(this);

		return this.handle;
	}

	@Override
	public void release(TauGLHandleOwner owner) 
	{
		GL20.glDeleteProgram(this.handle);
	}

	@Override
	public Integer getHandle()
	{
		return this.handle;
	}

	@Override
	public void setSource(ShaderType type, String source)
	{
		Objects.requireNonNull(type);
		Objects.requireNonNull(source);

		switch (type)
		{
			case VERTEX -> this.vertexShaderSource = source;
			case FRAGMENT -> this.fragmentShaderSource = source;
		}
	}

	@Override
	public void compile()
	{
		int vertexShader = GL20.glCreateShader(ShaderType.VERTEX.getGlBacking());
		int fragmentShader = GL20.glCreateShader(ShaderType.FRAGMENT.getGlBacking());

		GL20.glShaderSource(vertexShader, vertexShaderSource);
		GL20.glCompileShader(vertexShader);
		
		if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			throw new IllegalStateException("Failed to compile vertex shader. (Error: " + GL20.glGetShaderInfoLog(vertexShader) + ")");
		}

		GL20.glShaderSource(fragmentShader, fragmentShaderSource);
		GL20.glCompileShader(fragmentShader);

		if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			throw new IllegalStateException("Failed to compile fragment shader. (Error: " + GL20.glGetShaderInfoLog(fragmentShader) + ")");
		}

		if (this.handle == Integer.MIN_VALUE)
		{
			throw new IllegalStateException("Shader program must be allocated with allocate() before shaders can be attached");
		}

		GL20.glAttachShader(this.handle, vertexShader);
		GL20.glAttachShader(this.handle, fragmentShader);
		GL20.glLinkProgram(this.handle);

		if (GL20.glGetProgrami(this.handle, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
		{
			throw new IllegalStateException("Failed to link shader program. (Error: " + GL20.glGetProgramInfoLog(this.handle) + ")");
		}

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);

		System.out.println("Shader successfully compiled and linked with ID " + this.handle);
	}
}
