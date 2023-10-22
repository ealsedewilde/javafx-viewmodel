package nl.ealse.javafx.mapping.explorer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import nl.ealse.javafx.mapping.MappingException;
import nl.ealse.javafx.mapping.ViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Retrieve all information on a Java Bean Class that is needed for the {@link ViewModel}.
 *
 * @author ealse
 *
 */
public class BeanClassExplorer {
  private static final Logger LOGGER = LoggerFactory.getLogger(BeanClassExplorer.class);

  private static final String REFLECTION_ERROR = "Reflection error";

  /**
   * This List contains the result of the Java beanClass exploration.
   */
  private final List<PropertyContext> propertyInfoList = new ArrayList<>();

  /**
   * The Java beanClass to explore.
   */
  private final Class<?> beanClass;

  /**
   * Construct an instance around a model beanClass.
   *
   * @param modelBean - The Java beanClass to explore.
   */
  public BeanClassExplorer(Class<?> beanClass) {
    this.beanClass = beanClass;

  }

  /**
   * Make a description of the Java beanClass.
   *
   * @return - the description of the beanClass
   */
  public List<PropertyContext> describeBean() {
    examineBean(beanClass, null);
    return propertyInfoList;
  }

  /**
   * Examine a Java beanClass can be an iterative process. A beanClass may contain child Java
   * beanClass which must be examined as well.
   *
   * @param parentClass - the optional parent of the beanClass
   * @param beanClass - of the beanClass
   */
  private void examineBean(Class<?> beanClass, PropertyContext parent) {
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
      for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
        Method readMethod = property.getReadMethod();
        if (readMethod != null) {
          examineBeanProperty(beanClass, property, parent);
        }
      }
    } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | SecurityException e) {
      LOGGER.error(REFLECTION_ERROR, e);
      throw new MappingException(REFLECTION_ERROR, e);
    }
  }

  /**
   * Examine a property of the Java beanClass that is currently examined.
   *
   * @param clazz - of aBean
   * @param property - description of the property
   * @throws IllegalAccessException - n/a
   * @throws InvocationTargetException - n/a
   */
  private void examineBeanProperty(Class<?> clazz, PropertyDescriptor property,
      PropertyContext parent) throws IllegalAccessException, InvocationTargetException {
    Class<?> type = property.getPropertyType();
    if (isBeanToExplore(type)) {
      examineBean(type, newPropertyContext(parent, property, true));
    } else if (!"java.lang.Class".contentEquals(type.getName())) {
      PropertyContext t = newPropertyContext(parent, property, false);
      if (t != null) {
        propertyInfoList.add(t);
      }
    }
  }

  /**
   * Determine if the property is a Java beanClass that has to be explored.
   *
   * @param type - property type
   * @return
   */
  private boolean isBeanToExplore(Class<?> type) {
    if (type.getPackageName().startsWith("java")) {
      return false;
    }
    return !type.isEnum();
  }

  private PropertyContext newPropertyContext(PropertyContext parent, PropertyDescriptor property,
      boolean bean) {
    return new PropertyContext(parent, property);
  }
}
