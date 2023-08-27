package coffee.khyonieheart.tau.api.texture;

import org.lwjgl.opengl.GL11;

public class TextureOptions
{
	public TextureOptions setWrapping(
		int option
	) {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, option);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, option);

		return this;
	}

	public TextureOptions setTextureFiltering(
		int option
	) {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, option);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, option);

		return this;
	}

	public void setBorderColor(
		float red,
		float green,
		float blue,
		float alpha
	) {
		GL11.glTexParameterfv(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { red, green, blue, alpha });
	}
}
