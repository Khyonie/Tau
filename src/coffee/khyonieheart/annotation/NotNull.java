package coffee.khyonieheart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
public @interface NotNull
{

}
