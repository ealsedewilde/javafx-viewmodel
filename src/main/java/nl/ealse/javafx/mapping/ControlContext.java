package nl.ealse.javafx.mapping;

import java.beans.PropertyDescriptor;

public class ControlContext {
  
  private final ControlContext parentContext;
  
  private final PropertyDescriptor property;
  
  private final Mapping mapping;
  
  public ControlContext(Mapping mapping, ControlContext parentContext, PropertyDescriptor property) {
    this.parentContext = parentContext;
    this.property = property;
    this.mapping = mapping;
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
  
  public String getId() {
    String id = getMappingName();
    int ix = id.lastIndexOf('.');
    if (ix != -1) {
      id = id.substring(++ix);
    }
    return id;
  }

  public String getMappingName() {
    String mappingName;
    if (mapping != null && !mapping.mapOn().isEmpty()) {
      mappingName = mapping.mapOn();
    } else {
      mappingName = property.getName();
    }
    if (parentContext != null) {
      return String.format("%s.%s", parentContext.getMappingName(), mappingName);
    }
    return mappingName;
  }

  public PropertyDescriptor getProperty() {
    return property;
  }

  public Mapping getMapping() {
    return mapping;
  }

  public ControlContext getParentContext() {
    return parentContext;
  }


}
