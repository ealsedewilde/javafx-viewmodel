package nl.ealse.javafx.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Control;
import nl.ealse.javafx.mappers.MapperRegistry;
import nl.ealse.javafx.mappers.PropertyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Make a description of both View structure that matches the Model structure.
 * 
 * @author ealse
 *
 */
public class ViewExplorer {
  private static final Logger LOGGER = LoggerFactory.getLogger(ViewExplorer.class);

  private static final String REFLECTION_ERROR = "Reflection error";

  private final Map<String, List<PropertyContext>> modelDescription = new HashMap<>();
  private final Set<MappingContext> mappingDescription = new HashSet<>();

  private final List<ControlContext> viewDescription;

  public ViewExplorer(List<PropertyContext> modelDescription, Class<?> viewClass) {
    this.viewDescription = initializeView(viewClass);
    initializeModel(modelDescription);
  }

  private void initializeModel(List<PropertyContext> modelList) {
    for (PropertyContext pc : modelList) {
      List<PropertyContext> pcList = modelDescription.get(pc.getName());
      if (pcList == null) {
        pcList = new ArrayList<>();
        modelDescription.put(pc.getName(), pcList);
      }
      pcList.add(pc);
    }
  }

  private List<ControlContext> initializeView(Class<?> viewClass) {
    ViewBeanExplorer be = new ViewBeanExplorer(viewClass);
    return be.describeBean();
  }

  public Set<MappingContext> describeBean() {
    for (ControlContext viewContext : viewDescription) {
      List<PropertyContext> modelPropertyList = modelDescription.get(viewContext.getId());
      if (modelPropertyList.size() == 1) {
        PropertyContext modelContext = modelPropertyList.get(0);
        mappingDescription.add(buildMappingContext(viewContext, modelContext));
      } else {
        String mappingName = viewContext.getMappingName();
        int matches = 0;
        int ix = 0;
        for (int i = 0 ; i < modelPropertyList.size() ; i++) {
          PropertyContext modelContext = modelPropertyList.get(i);
          String fullName = modelContext.getFullName();
          int count = countMatches(mappingName, fullName);
          if (count > matches) {
            matches = count;
            ix = i;
          }
        }
        mappingDescription.add(buildMappingContext(viewContext, modelPropertyList.get(ix)));
      }
    }
    return mappingDescription;
  }
  
  private int countMatches(String viewName, String modelName) {
    String[] viewSplit = viewName.split("[.]");
    int viewIx = viewSplit.length;
    String[] modelSplit = modelName.split("[.]");
    int modelIx = modelSplit.length;
    int count = 0;
    while (viewIx > 0 && modelIx > 0 && viewSplit[--viewIx].equals(modelSplit[--modelIx])) {
      count++;
    }
    
    return count;
  }

  private MappingContext buildMappingContext(ControlContext viewContext,
      PropertyContext modelContext) {
    PropertyDescriptor viewProperty = viewContext.getProperty();
    String modelPropertyType = modelContext.getProperty().getPropertyType().getSimpleName();
    PropertyMapper<Control, Object> pm =
        findPropertyMapper(viewProperty, viewContext.getMapping(), modelPropertyType);
    MappingContext mappingContext =
        new MappingContext(pm, viewContext, modelContext);
    return mappingContext;
  }

  private PropertyMapper<Control, Object> findPropertyMapper(PropertyDescriptor viewProperty,
      Mapping mapping, String modelPropertyType) {
    PropertyMapper<Control, Object> propertyMapper;
    if (mapping == null || mapping.propertyMapper().isInterface()) {
      String key = viewProperty.getPropertyType().getSimpleName() + mappedType(modelPropertyType);
      Optional<PropertyMapper<Control, Object>> opt = MapperRegistry.getPropertyMapper(key);
      if (opt.isPresent()) {
        propertyMapper = opt.get();
      } else {
        throw new MappingException("no PropertyMapper for key: " + key);
      }
    } else {
      try {
        Object instance = mapping.propertyMapper().getConstructors()[0].newInstance();
        @SuppressWarnings("unchecked")
        PropertyMapper<Control, Object> pm = (PropertyMapper<Control, Object>) instance;
        propertyMapper = pm;
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException | SecurityException e) {
        LOGGER.error(REFLECTION_ERROR, e);
        throw new MappingException(REFLECTION_ERROR, e);
      }
    }
    return propertyMapper;
  }
  
  private String mappedType(String type) {
    switch (type) {
      default:
        return type;
      case "int":
        return "Integer";
      case "boolean":
        return "Boolean";
      case "byte":
        return "Byte";
      case "short":
        return "Short";
      case "long":
        return "Long";
      case "float":
        return "Float";
      case "double":
        return "Double";
    }
  }

}
