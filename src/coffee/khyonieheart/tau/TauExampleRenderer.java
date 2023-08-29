package coffee.khyonieheart.tau;

import java.util.Objects;

import org.lwjgl.opengl.GL20;

import coffee.khyonieheart.tau.api.TauGameContainer;
import coffee.khyonieheart.tau.api.gl.TauGLRenderer;
import coffee.khyonieheart.tau.api.gl.TauGLShaderProgram;
import coffee.khyonieheart.tau.api.texture.TextureOptions;

public class TauExampleRenderer implements TauGLRenderer
{
	private TauGLShaderProgram shader;
	private TextureOptions textureOptions = new TextureOptions();

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

	@Override
	public TextureOptions getTextureOptions() 
	{
		return this.textureOptions;
	}

	@Override
	public TauGLShaderProgram getShader() 
	{
		return this.shader;
	}
}
