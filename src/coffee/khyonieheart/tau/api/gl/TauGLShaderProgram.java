package coffee.khyonieheart.tau.api.gl;

import org.lwjgl.opengl.GL20;

public interface TauGLShaderProgram extends TauGLHandled<Integer>
{
	public void setSource(
		ShaderType type,
		String source
	);

	public void compile();

	public static enum ShaderType
	{
		VERTEX(GL20.GL_VERTEX_SHADER),
		FRAGMENT(GL20.GL_FRAGMENT_SHADER)
		;

		private int glBackedInt;

		private ShaderType(
			int glBackedInt
		) {
			this.glBackedInt = glBackedInt;
		}

		public int getGlBacking()
		{
			return this.glBackedInt;
		}
	}
}
