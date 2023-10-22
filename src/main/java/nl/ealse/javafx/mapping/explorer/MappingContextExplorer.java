package nl.ealse.javafx.mapping.explorer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Control;
import nl.ealse.javafx.mappers.MapperRegistry;
import nl.ealse.javafx.mappers.PropertyMapper;
import nl.ealse.javafx.mapping.Mapping;
import nl.ealse.javafx.mapping.MappingContext;
import nl.ealse.javafx.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Make a description of both View structure that matches the Model structure.
 *
 * @author ealse
 *
 */
public class MappingContextExplorer {
  private static final Logger LOGGER = LoggerFactory.getLogger(MappingContextExplorer.class);

  private static final String REFLECTION_ERROR = "Reflection error";

  private final Set<MappingContext> mappingDescription = new HashSet<>();

  private final Map<String, List<PropertyContext>> modelClassDescriptionMap;
  private final List<ViewClassPropertyContext> viewDescription;

  public MappingContextExplorer(Class<?> viewClass, Class<?> modelClass) {
    this.modelClassDescriptionMap = processModelClass(modelClass);
    this.viewDescription = processViewClass(viewClass);
  }

  private Map<String, List<PropertyContext>> processModelClass(Class<?> modelClass) {
    BeanClassExplorer modelBeanExplorer = new BeanClassExplorer(modelClass);
    List<PropertyContext> modelClassDescription = modelBeanExplorer.describeBean();

    Map<String, List<PropertyContext>> modelClassDescriptionMap = new HashMap<>();
    for (PropertyContext pc : modelClassDescription) {
      List<PropertyContext> pcList = modelClassDescriptionMap.get(pc.getName());
      if (pcList == null) {
        pcList = new ArrayList<>();
        modelClassDescriptionMap.put(pc.getName(), pcList);
      }
      pcList.add(pc);
    }
    return modelClassDescriptionMap;
  }

  private List<ViewClassPropertyContext> processViewClass(Class<?> viewClass) {
    BeanClassExplorer viewBeanExplorer = new BeanClassExplorer(viewClass);
    List<PropertyContext> viewClassDescription = viewBeanExplorer.describeBean();

    for (Iterator<PropertyContext> itr = viewClassDescription.iterator(); itr.hasNext();) {
      PropertyContext vc = itr.next();
      if (!Control.class.isAssignableFrom(vc.getProperty().getPropertyType())) {
        itr.remove();
      }
    }
    ViewClassExplorer be = new ViewClassExplorer(viewClassDescription);
    return be.describeBean();
  }

  public Set<MappingContext> describeMapping() {
    for (ViewClassPropertyContext viewContext : viewDescription) {
      List<PropertyContext> modelPropertyList = modelClassDescriptionMap.get(viewContext.getId());
      if (modelPropertyList == null) {
        LOGGER.warn(
            String.format(
                "Unable to find property in model for %s. \n "
                + "Is annotation '@Mapping(ignore = true)' missing on the property in the view?",
            viewContext.getId()));
      }
      if (modelPropertyList.size() == 1) {
        PropertyContext modelContext = modelPropertyList.get(0);
        mappingDescription.add(buildMappingContext(viewContext, modelContext));
      } else {
        String mappingName = viewContext.getMappingName();
        int matches = 0;
        int ix = 0;
        for (int i = 0; i < modelPropertyList.size(); i++) {
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

  private MappingContext buildMappingContext(ViewClassPropertyContext viewContext,
      PropertyContext modelContext) {
    PropertyDescriptor viewProperty = viewContext.getProperty();
    String modelPropertyType = modelContext.getProperty().getPropertyType().getSimpleName();
    PropertyMapper<Control, Object> pm =
        findPropertyMapper(viewProperty, viewContext.getMapping(), modelPropertyType);
    MappingContext mappingContext = new MappingContext(pm, viewContext, modelContext);
    return mappingContext;
  }

  private PropertyMapper<Control, Object> findPropertyMapper(PropertyDescriptor viewProperty,
      Optional<Mapping> mapping, String modelPropertyType) {
    PropertyMapper<Control, Object> propertyMapper;
    if (!mapping.isPresent() || mapping.get().propertyMapper().isInterface()) {
      String key = viewProperty.getPropertyType().getSimpleName() + mappedType(modelPropertyType);
      Optional<PropertyMapper<Control, Object>> opt = MapperRegistry.getPropertyMapper(key);
      if (opt.isPresent()) {
        propertyMapper = opt.get();
      } else {
        throw new MappingException("no PropertyMapper for key: " + key);
      }
    } else {
      try {
        Object instance = mapping.get().propertyMapper().getConstructors()[0].newInstance();
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
