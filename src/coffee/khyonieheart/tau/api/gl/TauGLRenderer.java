package coffee.khyonieheart.tau.api.gl;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.annotation.Nullable;
import coffee.khyonieheart.tau.api.TauGameContainer;
import coffee.khyonieheart.tau.api.texture.TextureOptions;

public interface TauGLRenderer
{
	public void setShader(
		@NotNull TauGLShaderProgram shader
	);

	@Nullable
	public TauGLShaderProgram getShader();

	@NotNull
	public TextureOptions getTextureOptions();

	public void bind(
		@NotNull TauGameContainer container
	);

	public void unbind(
		@NotNull TauGameContainer container
	);
}
