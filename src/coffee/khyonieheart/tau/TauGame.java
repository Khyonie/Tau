package coffee.khyonieheart.tau;

import java.util.Objects;

import org.lwjgl.glfw.GLFW;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.tau.api.TauGameContainer;
import coffee.khyonieheart.tau.api.gl.TauGLRenderer;
import coffee.khyonieheart.tau.api.gl.TauGLShaderProgram;

public abstract class TauGame
{
	private TauGameContainer container;
	private TauGLRenderer renderer;

	public TauGame(
		@NotNull TauGameContainer container,
		@NotNull TauGLRenderer renderer
	) {
		Objects.requireNonNull(container);
		Objects.requireNonNull(renderer);

		this.container = container;
		this.renderer = renderer;
	}

	public abstract void init(
		@NotNull TauGameContainer container
	);

	public abstract void render(
		@NotNull TauGameContainer container, 
		@NotNull TauGLRenderer renderer
	);

	public abstract void update(
		@NotNull TauGameContainer container
	);

	public void start()
	{
		this.container.allocate(null);
		this.container.acquireContext();

		TauGLShaderProgram shader = new TauBasicShader();
		shader.allocate(this.container);
		shader.compile();

		this.renderer.setShader(shader);

		this.init(this.container);

		while (this.container.isRunning())
		{
			GLFW.glfwSwapBuffers(this.container.getHandle());

			this.update(this.container);
			this.render(this.container, this.renderer);

			GLFW.glfwPollEvents();
		}
	}

	@NotNull
	public TauGLRenderer getRenderer()
	{
		return this.renderer;
	}

	@NotNull
	public TauGameContainer getContainer()
	{
		return this.container;
	}
}
