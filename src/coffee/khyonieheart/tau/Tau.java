package coffee.khyonieheart.tau;

/*
Tau, a simple mixed 2D/3D OpenGL game library.
Copyright (C) 2023 http://khyonieheart.coffee

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import coffee.khyonieheart.tau.api.TauGameContainer;

public class Tau
{
	public static void main(String[] args)
	{
		System.out.println("« Tau »");
		System.out.println("Initializing GLFW...");

		if (!GLFW.glfwInit())
		{
			System.out.println("Failed to initialize GLFW.");
			return;
		}

		GLFW.glfwSetErrorCallback((error, description) -> {
			System.out.println("GLFW error: " + GLFWErrorCallback.getDescription(description) + " (code " + error + ")");
		});

		System.out.println("GLFW initialized.");

		TauGameContainer testContainer = new TauGameContainer()
			.setDimensions(800, 600)
			.setTitle("Test GLFW Window")
			.setFullscreen(false);

		TauGame game = new TauExampleGame(testContainer, new TauExampleRenderer());
		game.start();

		System.out.println("Window terminated.");

		GLFW.glfwTerminate();

		System.out.println("GLFW terminated.");
	}
}
