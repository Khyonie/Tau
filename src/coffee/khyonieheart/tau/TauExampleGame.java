package coffee.khyonieheart.tau;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;

import coffee.khyonieheart.tau.api.TauGameContainer;
import coffee.khyonieheart.tau.api.TauMesh;
import coffee.khyonieheart.tau.api.gl.TauGLRenderer;
import coffee.khyonieheart.tau.api.texture.Texture;

public class TauExampleGame extends TauGame
{
	public TauExampleGame(TauGameContainer container, TauGLRenderer renderer) 
	{
		super(container, renderer);
	}

	private	TauMesh mesh;

	@Override
	public void init(TauGameContainer container) 
	{
		this.mesh = new TauMesh(
			0.5f, 0.5f, 1.0f,
			0.5f, -0.5f, 1.0f,
			-0.5f, 0.5f, 1.0f,
			0.5f, -0.5f, 1.0f,
			-0.5f, -0.5f, 1.0f,
			-0.5f, 0.5f, 1.0f
		);

		mesh.allocate(container);
		mesh.buffer();

		try {
			Texture tex = Texture.loadFromPNG("test.png");

			tex.getImageData().rewind();
			
			while (tex.getImageData().position() < tex.getImageData().capacity())
			{
				System.out.println("[R" + tex.getImageData().get() + ",G" + tex.getImageData().get() + ",B" + tex.getImageData().get() + ",A" + tex.getImageData().get() + "]");
			}
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(TauGameContainer container, TauGLRenderer renderer) 
	{
		renderer.bind(container);

		mesh.render();

		renderer.unbind(container);
	}

	@Override
	public void update(TauGameContainer container) 
	{
		if (GLFW.glfwGetKey(container.getHandle(), GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
		{
			System.out.println("Destroying window.");
			container.destroyContext();
		}
	}
}
