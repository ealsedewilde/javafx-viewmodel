package nl.ealse.javafx.mapping.explorer;

import java.beans.PropertyDescriptor;

public class GenerictPropertyContext<T extends GenerictPropertyContext<?>> {

  private final T parentContext;
  
  private final PropertyDescriptor property;
  
  public GenerictPropertyContext(T parentContext, PropertyDescriptor property) {
    this.parentContext = parentContext;
    this.property = property;
  }
  
  public String getName() {
    return property.getName();
  }
  
  public String getFullName() {
    if (parentContext != null) {
      return String.format("%s.%s", parentContext.getFullName(), getName());
    }
    return getName();
  }


  public PropertyDescriptor getProperty() {
    return property;
  }

  public T getParentContext() {
    return parentContext;
  }
  
  @Override
  public int hashCode() {
    return getProperty().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof GenerictPropertyContext) {
      GenerictPropertyContext<?> ref = (GenerictPropertyContext<?>) obj;
      String refName = ref.getProperty().getName();
      Class<?> refClass = ref.getProperty().getReadMethod().getDeclaringClass();
      String thisName = getProperty().getName();
      Class<?> thisClass = getProperty().getReadMethod().getDeclaringClass();
      return refName.equals(thisName) && refClass.equals(thisClass);
    }
    return false;
  }



}
