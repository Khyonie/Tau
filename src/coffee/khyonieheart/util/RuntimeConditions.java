package coffee.khyonieheart.util;

public class RuntimeConditions
{
	public static void requireBounds(
		int input,
		int min,
		int max
	) {
		if (input < min || input > max)
		{
			throw new IllegalArgumentException("Integer " + input + " does not fit within expected range [" + min + "-" + max + "]");
		}
	}

	public static void requireMinimum(
		int input,
		int min
	) {
		requireBounds(input, min, Integer.MAX_VALUE);
	}
}
