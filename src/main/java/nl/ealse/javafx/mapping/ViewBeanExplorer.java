package nl.ealse.javafx.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javafx.scene.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewBeanExplorer extends BeanExplorer<ControlContext> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ViewExplorer.class);

  private static final String REFLECTION_ERROR = "Reflection error";

  ViewBeanExplorer(Class<?> beanClass) {
    super(beanClass);
  }

  @Override
  protected ControlContext newInstance(ControlContext parent, PropertyDescriptor property,
      boolean bean) {
    if (bean || Control.class.isAssignableFrom(property.getPropertyType())) {
      Method readMethod = property.getReadMethod();
      String name = property.getName();
      Mapping mapping = findMapping(readMethod, name);
      if (mapping == null || !mapping.ignore()) {
        return new ControlContext(mapping, parent, property);
      }
    }
    return null;
  }

  private Mapping findMapping(Method readMethod, String name) {
    Mapping mapping = readMethod.getAnnotation(Mapping.class);
    if (mapping != null) {
      return mapping;
    } else {
      Class<?> declaringClass = readMethod.getDeclaringClass();
      try {
        Field field = declaringClass.getDeclaredField(name);
        mapping = field.getAnnotation(Mapping.class);
        return mapping;
      } catch (NoSuchFieldException | SecurityException e) {
        LOGGER.error(REFLECTION_ERROR, e);
        throw new MappingException(REFLECTION_ERROR, e);
      }
    }

  }


}
