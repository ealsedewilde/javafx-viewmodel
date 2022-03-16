package nl.ealse.javafx.mapping;

import java.beans.PropertyDescriptor;

public class ModelBeanExplorer extends BeanExplorer<PropertyContext> {

  ModelBeanExplorer(Class<?> beanClass) {
    super(beanClass);
  }

  @Override
  protected PropertyContext newInstance(PropertyContext parent, PropertyDescriptor property,
      boolean bean) {
    return new PropertyContext(parent, property);
  }

}
