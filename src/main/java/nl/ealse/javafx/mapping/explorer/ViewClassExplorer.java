package nl.ealse.javafx.mapping.explorer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.ealse.javafx.mapping.Mapping;
import nl.ealse.javafx.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enrich the {@link PropertyContext} with optional @Mapping annotation data.
 *
 * @author ealse
 */
public class ViewClassExplorer {
  private static final Logger LOGGER = LoggerFactory.getLogger(MappingContextExplorer.class);

  private static final String REFLECTION_ERROR = "Reflection error";

  private final List<PropertyContext> viewClassDescription;

  ViewClassExplorer(List<PropertyContext> viewClassDescription) {
    this.viewClassDescription = viewClassDescription;
  }

  public List<ViewClassPropertyContext> describeBean() {
    List<ViewClassPropertyContext> vpcList = new ArrayList<>();
    for (PropertyContext pc : viewClassDescription) {
      ViewClassPropertyContext vpc = toViewPropertyContext(pc);
      if (vpc != null) {
        vpcList.add(vpc);
      }
    }
    return vpcList;
  }

  /**
   * Construct a <code>ViewClassPropertyContext</code> by adding
   *
   * @param pc
   * @return
   */
  protected ViewClassPropertyContext toViewPropertyContext(PropertyContext pc) {
    ViewClassPropertyContext parent = null;
    if (pc.getParentContext() != null) {
      parent = toViewPropertyContext(pc.getParentContext());
    }
    PropertyDescriptor property = pc.getProperty();
    Method readMethod = property.getReadMethod();
    String name = property.getName();
    Optional<Mapping> mapping = findMapping(readMethod, name);
    if (mapping.isEmpty() || !mapping.get().ignore()) {
      return new ViewClassPropertyContext(mapping, parent, property);
    }
    // ignoreable javafx Control
    return null;
  }

  /**
   * Find possible {@link Mapping} annotation on the java fx Control.
   *
   * @param readMethod
   * @param name
   * @return @Mapping annotation or null
   */
  private Optional<Mapping> findMapping(Method readMethod, String name) {
    Mapping mapping = readMethod.getAnnotation(Mapping.class);
    if (mapping != null) {
      return Optional.empty();
    } else {
      Class<?> declaringClass = readMethod.getDeclaringClass();
      try {
        Field field = declaringClass.getDeclaredField(name);
        mapping = field.getAnnotation(Mapping.class);
        return Optional.ofNullable(mapping);
      } catch (NoSuchFieldException | SecurityException e) {
        LOGGER.error(REFLECTION_ERROR, e);
        throw new MappingException(REFLECTION_ERROR, e);
      }
    }

  }


}
