package src.com.showtimedev.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Flag annotation, implies a contract that the parameter and/or its contents will not be modified
 * in any way. Allows the caller to have confidence that the data integrity will not be damaged.
 */
@Target({ElementType.PARAMETER})
public @interface Immutable{

}
