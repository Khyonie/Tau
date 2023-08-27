package coffee.khyonieheart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
public @interface Range
{
	public int min();
	public int max() default Integer.MAX_VALUE;
}
