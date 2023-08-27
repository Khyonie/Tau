package coffee.khyonieheart.tau.api.gl;

import coffee.khyonieheart.annotation.Nullable;

public interface TauGLHandled<T extends Number>
{
	T allocate(
		@Nullable TauGLHandleOwner owner
	); 

	void release(
		@Nullable TauGLHandleOwner owner
	);

	T getHandle();
}
