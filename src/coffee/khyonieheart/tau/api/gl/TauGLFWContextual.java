package coffee.khyonieheart.tau.api.gl;

public interface TauGLFWContextual extends TauGLHandled<Long>
{
	/**
	 * Acquires a GLFW context.
	 */
	public void acquireContext();

	/**
	 * Deletes the GLFW context belonging to this object.
	 */
	public void destroyContext();
}
