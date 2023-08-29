package coffee.khyonieheart.tau.api;

import java.util.Objects;

import coffee.khyonieheart.annotation.NotNull;
import coffee.khyonieheart.annotation.Nullable;
import coffee.khyonieheart.tau.api.gl.TauGLHandled;

public class TauLogger
{
	public static void log(
		@NotNull TauGLHandled<?> handle,
		@Nullable String message
	) {
		Objects.requireNonNull(handle);

		System.out.println("Tau » " + handle.getClass().getSimpleName() + " #"  + handle.getHandle() + " » " + message);
	}
}
