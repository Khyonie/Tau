package coffee.khyonieheart.tau.api.gl;

import coffee.khyonieheart.annotation.NotNull;

public interface TauGLHandleOwner
{
	/**
	 * Registers an object bearing a GL handle to this object.
	 *
	 * @param handle Object with handle.
	 */
	public void addHandle(
		@NotNull TauGLHandled<?> handle
	);

	/**
	 * Unregisters an object bearing a GL handle which has been previously registered to this object.
	 *
	 * @param handle Object with handle.
	 */
	public void removeHandle(
		@NotNull TauGLHandled<?> handle
	);
}
