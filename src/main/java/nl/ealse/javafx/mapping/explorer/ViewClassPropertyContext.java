package nl.ealse.javafx.mapping.explorer;

import java.beans.PropertyDescriptor;
import java.util.Optional;
import nl.ealse.javafx.mapping.Mapping;

/**
 * PropertyContext with @Mapping data.
 * @author ealse
 *
 */
public class ViewClassPropertyContext extends GenerictPropertyContext<ViewClassPropertyContext> {
  
  private final Optional<Mapping> mapping;
  
  public ViewClassPropertyContext(Optional<Mapping> mapping, ViewClassPropertyContext parentContext, PropertyDescriptor property) {
    super(parentContext, property);
    this.mapping = mapping;
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
    if (mapping.isPresent() && !mapping.get().mapOn().isEmpty()) {
      mappingName = mapping.get().mapOn();
    } else {
      mappingName = getProperty().getName();
    }
    if (getParentContext() != null) {
      return String.format("%s.%s", getParentContext().getMappingName(), mappingName);
    }
    return mappingName;
  }

  public Optional<Mapping> getMapping() {
    return mapping;
  }


}
