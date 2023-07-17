package serializer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation indicates that the annotated field
 * is serializable.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Serialize { }
