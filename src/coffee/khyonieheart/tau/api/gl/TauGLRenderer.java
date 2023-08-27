package coffee.khyonieheart.tau.api.gl;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.tau.api.TauGameContainer;

public interface TauGLRenderer
{
	public void setShader(
		@NotNull TauGLShaderProgram shader
	);

	public void bind(
		@NotNull TauGameContainer container
	);

	public void unbind(
		@NotNull TauGameContainer container
	);
}
