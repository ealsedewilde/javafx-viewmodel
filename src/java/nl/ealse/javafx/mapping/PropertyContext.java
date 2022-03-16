package nl.ealse.javafx.mapping;

import java.beans.PropertyDescriptor;

public class PropertyContext {
  
  private final PropertyContext parentContext;
  
  private final PropertyDescriptor property;
  
  public PropertyContext(PropertyContext parentContext, PropertyDescriptor property) {
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

  public PropertyContext getParentContext() {
    return parentContext;
  }

}
