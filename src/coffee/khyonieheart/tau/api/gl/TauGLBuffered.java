package coffee.khyonieheart.tau.api.gl;

public interface TauGLBuffered<T extends Number> extends TauGLHandled<T>
{
	/**
	 * Loads data into the buffer this object holds a handle to.
	 */
	public void buffer();
}
