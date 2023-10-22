package nl.ealse.javafx.mapping;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import nl.ealse.javafx.mappers.PropertyMapper;

/**
 * Optional annotation to steer the data mapping process.
 *
 * @author ealse
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Mapping {
  
  public String mapOn() default "";

  /**
   * Ignore this javafx control in the data mapping.
   * This is a Field annotation attribute
   *
   * @return
   */
  public boolean ignore() default false;

  /**
   * Link a specific {@link PropertyMapper} to this javafx control. Use this option in case where one
   * of the standard PropertyMappers can't handle this javafx control.
   * This is a Field annotation attribute
   *
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Class<? extends PropertyMapper> propertyMapper() default PropertyMapper.class;

}
