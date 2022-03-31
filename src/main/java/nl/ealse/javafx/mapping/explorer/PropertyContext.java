package nl.ealse.javafx.mapping.explorer;

import java.beans.PropertyDescriptor;

public class PropertyContext extends GenerictPropertyContext<PropertyContext>{
  
  public PropertyContext(PropertyContext parentContext, PropertyDescriptor property) {
    super(parentContext, property);
  }

}
