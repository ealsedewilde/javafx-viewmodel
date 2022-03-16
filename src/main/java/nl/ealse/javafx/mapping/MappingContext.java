package nl.ealse.javafx.mapping;

import javafx.scene.control.Control;
import nl.ealse.javafx.mappers.PropertyMapper;

/**
 * Meta dat needed to map one property.
 * @author ealse
 *
 */
public class MappingContext implements Comparable<MappingContext> {
  
  private final PropertyContext modelContext;
  private final ControlContext viewContext;
  
  private final PropertyMapper<Control, Object> propertyMapper;
  
  public MappingContext(PropertyMapper<Control, Object> propertyMapper, ControlContext viewContext, PropertyContext modelContext) {
    this.modelContext = modelContext;
    this.viewContext = viewContext;
    this.propertyMapper = propertyMapper;
  }
  
  @Override
  public int hashCode() {
    return modelContext.getFullName().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MappingContext) {
      return compareTo((MappingContext) obj) == 0;
    }
    return false;
  }

  @Override
  public int compareTo(MappingContext o) {
    return modelContext.getFullName().compareTo(o.modelContext.getFullName());
  }

  public PropertyContext getModelContext() {
    return modelContext;
  }

  public ControlContext getViewContext() {
    return viewContext;
  }

  public PropertyMapper<Control, Object> getPropertyMapper() {
    return propertyMapper;
  }

}
