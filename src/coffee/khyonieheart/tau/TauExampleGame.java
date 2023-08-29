package coffee.khyonieheart.tau;

import java.io.IOException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

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

		this.getRenderer().getTextureOptions().setWrapping(GL11.GL_REPEAT);
		this.getRenderer().getTextureOptions().setTextureFiltering(GL11.GL_NEAREST);

		mesh.allocate(container);

		try {
			Texture tex = Texture.loadFromPNG("test.png");
			tex.allocate(container);
			tex.buffer();

			mesh.setTexture(tex, 
				1.0f, 1.0f,
				1.0f, -1.0f,
				-1.0f, 1.0f,
				1.0f, -1.0f,
				-1.0f, -1.0f,
				-1.0f, 1.0f
			);

			mesh.applyTexture();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}

		mesh.buffer();
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
