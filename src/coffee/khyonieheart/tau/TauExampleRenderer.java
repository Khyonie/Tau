package coffee.khyonieheart.tau;

import java.util.Objects;

import org.lwjgl.opengl.GL20;

import coffee.khyonieheart.tau.api.TauGameContainer;
import coffee.khyonieheart.tau.api.gl.TauGLRenderer;
import coffee.khyonieheart.tau.api.gl.TauGLShaderProgram;

public class TauExampleRenderer implements TauGLRenderer
{
	private TauGLShaderProgram shader;

	@Override
	public void setShader(TauGLShaderProgram shader) 
	{
		Objects.requireNonNull(shader);
		this.shader = shader;
	}

	@Override
	public void bind(TauGameContainer container) 
	{
		GL20.glUseProgram(shader.getHandle());
	}

	@Override
	public void unbind(TauGameContainer container) 
	{
		GL20.glUseProgram(0);
	}
}
