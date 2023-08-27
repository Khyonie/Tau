package coffee.khyonieheart.tau.api;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.annotation.Range;
import coffee.khyonieheart.tau.api.gl.TauGLFWContextual;
import coffee.khyonieheart.tau.api.gl.TauGLHandleOwner;
import coffee.khyonieheart.tau.api.gl.TauGLHandled;
import coffee.khyonieheart.util.RuntimeConditions;

public class TauGameContainer implements TauGLFWContextual, TauGLHandleOwner
{
	// GL properties 
	private long handle = Long.MIN_VALUE;
	private Set<TauGLHandled<?>> ownedHandles = new HashSet<>(256); // Allocate a reasonable space to prevent constant re-allocation later

	// Container properties
	private int width;
	private int height;
	private boolean fullscreen = false;
	private String title;

	private boolean isRunning = false;
	
	//
	// OpenGL/GLFW layer
	//

	@Override
	public Long allocate(
		TauGLHandleOwner owner
	) {
		if (this.handle != Long.MIN_VALUE)
		{
			return this.handle;
		}

		if (this.title == null)
		{
			throw new IllegalStateException("No title set for container.");
		}

		this.handle = GLFW.glfwCreateWindow(this.width, this.height, this.title, this.fullscreen ? GLFW.glfwGetPrimaryMonitor() : MemoryUtil.NULL, MemoryUtil.NULL);

		if (this.handle == MemoryUtil.NULL)
		{
			throw new IllegalStateException("Failed to create window.");
		}

		if (this.fullscreen)
		{
			GLFW.glfwSetWindowMonitor(this.handle, GLFW.glfwGetPrimaryMonitor(), 0, 0, this.width, this.height, GLFW.GLFW_DONT_CARE);
		}

		return this.handle;
	}

	@Override
	public void release(
		TauGLHandleOwner owner
	) {
		for (TauGLHandled<?> h : ownedHandles)
		{
			h.release(this);
		}

		this.handle = Long.MIN_VALUE;
	}

	@Override
	public Long getHandle() 
	{
		return this.handle;
	}

	@Override
	public void addHandle(TauGLHandled<?> handle) 
	{
		Objects.requireNonNull(handle);

		this.ownedHandles.add(handle);
	}

	@Override
	public void removeHandle(TauGLHandled<?> handle) 
	{
		Objects.requireNonNull(handle);

		this.ownedHandles.remove(handle);
	}

	@Override
	public void acquireContext() 
	{
		GLFW.glfwMakeContextCurrent(this.getHandle());
		GL.createCapabilities();

		GL11.glViewport(0, 0, this.width, this.height);
		GLFW.glfwSwapInterval(1);

		this.isRunning = true;
	}

	@Override
	public void destroyContext() 
	{
		GLFW.glfwSetWindowShouldClose(this.getHandle(), true);
		GLFW.glfwDestroyWindow(this.getHandle());

		release(null);
		this.isRunning = false;
	}
	
	// API layer
	
	public TauGameContainer setDimensions(
		@Range(min = 1) int width,
		@Range(min = 1) int height
	) {
		RuntimeConditions.requireMinimum(width, 1);
		RuntimeConditions.requireMinimum(height, 1);

		this.width = width;
		this.height = height;

		return this;
	}

	public boolean isRunning()
	{
		return this.isRunning;
	}

	public TauGameContainer setTitle(
		@NotNull String title
	) {
		Objects.requireNonNull(title);

		this.title = title;

		return this;
	}

	public TauGameContainer setFullscreen(
		boolean fullscreen
	) {
		this.fullscreen = fullscreen;

		return this;
	}

	public int getWidth()
	{
		return this.width;	
	}

	public int getHeight()
	{
		return this.height;
	}

	public String getTitle()
	{
		return this.title;
	}
}
