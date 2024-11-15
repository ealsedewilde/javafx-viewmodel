package nl.ealse.javafx.mvvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import javafx.scene.control.Control;
import nl.ealse.javafx.mappers.PropertyMapper;
import nl.ealse.javafx.mapping.MappingContext;
import nl.ealse.javafx.mapping.MappingException;
import nl.ealse.javafx.mapping.explorer.MappingContextExplorer;
import nl.ealse.javafx.mapping.explorer.PropertyContext;
import nl.ealse.javafx.mapping.explorer.ViewClassPropertyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ViewModel in the MVVM paradigm.
 *
 * @author ealse
 *
 */
public class ViewModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(ViewModel.class);
  private static final String MAPPING_ERROR = "Mapping error";

  private static final Map<Class<?>, ViewModel> INSTANCES = new HashMap<>();

  private final Set<MappingContext> mappingDescription;

  private ViewModel(Class<?> viewClass, Class<?> modelClass) {
    mappingDescription = initialize(viewClass, modelClass);
  }

  public static <M> M viewToModel(Object view, M model) {
    Class<?> viewClass = view.getClass();
    ViewModel vm = INSTANCES.get(viewClass);
    if (vm == null) {
      vm = new ViewModel(viewClass, model.getClass());
      INSTANCES.put(viewClass, vm);
    }
    return vm.internalViewToModel(view, model);
  }

  public static void modelToView(Object view, Object model) {
    Class<?> viewClass = view.getClass();
    ViewModel vm = INSTANCES.get(viewClass);
    if (vm == null) {
      vm = new ViewModel(viewClass, model.getClass());
      INSTANCES.put(viewClass, vm);
    }
    vm.internalModelToView(view, model);
  }

  /**
   * Map the View to the Model. Missing beans in the Model structure are created via the default
   * constructor.
   *
   * @param <M> model class
   * @param view
   * @param model
   * @return the mapped model
   */
  private <M> M internalViewToModel(Object view, M model) {
    for (MappingContext context : mappingDescription) {
      PropertyMapper<Control, Object> mapper = context.getPropertyMapper();
      try {
        Control javaFxControl = (Control) javaFXControl(view, context.getViewContext());
        if (javaFxControl != null) {
          Object value = mapper.getPropertyFromJavaFx(javaFxControl);
          Method writeMethod = context.getModelContext().getProperty().getWriteMethod();

          Object target;
          if (context.getModelContext().getParentContext() != null) {
            target =
                modelTarget(model, context.getModelContext().getParentContext(), value != null);
          } else {
            target = model;
          }
          if (target != null) {
            writeMethod.invoke(target, value);
          }
        }
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        LOGGER.error(MAPPING_ERROR, e);
        throw new MappingException(MAPPING_ERROR, e);
      }
    }
    return model;
  }

  private Object modelTarget(Object model, PropertyContext modelContext, boolean create) {
    if (modelContext.getParentContext() != null) {
      Object obj = modelTarget(model, modelContext.getParentContext(), create);
      if (obj == null && !create) {
        return null;
      }
      return getTarget(obj, modelContext, create);
    }
    return getTarget(model, modelContext, create);
  }

  private Object getTarget(Object model, PropertyContext modelContext, boolean create) {
    Method m = modelContext.getProperty().getReadMethod();
    Object target = invokeRead(model, m);
    if (target == null && create) {
      target = createTarget(m.getReturnType());
      Method w = modelContext.getProperty().getWriteMethod();
      invokeWrite(model, w, target);
    }
    return target;
  }

  private Object createTarget(Class<?> clazz) {
    try {
      return clazz.getConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      LOGGER.error(MAPPING_ERROR, e);
      throw new MappingException(MAPPING_ERROR, e);
    }
  }

  /**
   * Map to model to the View.
   *
   * @param view
   * @param model
   */
  private void internalModelToView(Object view, Object model) {
    for (MappingContext context : mappingDescription) {
      PropertyMapper<Control, Object> mapper = context.getPropertyMapper();
      try {
        Control javaFxControl = (Control) javaFXControl(view, context.getViewContext());
        if (javaFxControl != null) {
          Object modelProperty = modelProperty(model, context.getModelContext());
          mapper.mapPropertyToJavaFx(modelProperty, javaFxControl);
        }
      } catch (IllegalArgumentException e) {
        LOGGER.error(MAPPING_ERROR, e);
        throw new MappingException(MAPPING_ERROR, e);
      }
    }
  }

  private Object javaFXControl(Object view, ViewClassPropertyContext viewContext) {
    if (viewContext.getParentContext() != null) {
      Object obj = javaFXControl(view, viewContext.getParentContext());
      return invokeRead(obj, viewContext.getProperty().getReadMethod());
    }
    Method m = viewContext.getProperty().getReadMethod();
    return invokeRead(view, m);
  }

  private Object modelProperty(Object model, PropertyContext modelContext) {
    if (modelContext.getParentContext() != null) {
      Object obj = modelProperty(model, modelContext.getParentContext());
      return invokeRead(obj, modelContext.getProperty().getReadMethod());
    }
    Method m = modelContext.getProperty().getReadMethod();
    return invokeRead(model, m);
  }

  private Object invokeRead(Object source, Method m) {
    if (source == null) {
      return null;
    }
    try {
      return m.invoke(source);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LOGGER.error(MAPPING_ERROR, e);
      throw new MappingException(MAPPING_ERROR, e);
    }
  }

  private void invokeWrite(Object source, Method m, Object parm) {
    try {
      if (source != null && m != null) {
        m.invoke(source, parm);
      }
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      LOGGER.error(MAPPING_ERROR, e);
      throw new MappingException(MAPPING_ERROR, e);
    }
  }


  private static Set<MappingContext> initialize(Class<?> viewClass, Class<?> modelClass) {
    MappingContextExplorer viewExplorer = new MappingContextExplorer(viewClass, modelClass);
    return viewExplorer.describeMapping();
  }

  /**
   * Test method.
   * <p>
   * Mapping is based on name equality of properties in both the view and the model.
   * </p>
   * This method helps finding mapping errors during development phase.
   *
   * @param view - input for the explain report
   * @param model - to relate to the view
   */
  public static String explain(Class<?> viewClass, Class<?> modelClass) {
    Set<MappingContext> mappingDescription = new TreeSet<>();
    mappingDescription.addAll(initialize(viewClass, modelClass));
    StringJoiner sj = new StringJoiner("\n");
    for (MappingContext mc : mappingDescription) {
      String cn = mc.getModelContext().getProperty().getReadMethod().getDeclaringClass().getName();
      String name = mc.getModelContext().getProperty().getName();
      String mappingName = mc.getViewContext().getMappingName();
      if (name.equals(mappingName)) {
        sj.add(String.format("[INFO] Property %s.%s \t\t uses PropertyMapper %s", cn, name,
            mc.getPropertyMapper().getClass().getSimpleName()));
      } else {
        sj.add(String.format("[INFO] Property %s.%s (%s)\t uses PropertyMapper %s", cn, name, mappingName,
            mc.getPropertyMapper().getClass().getSimpleName()));
      }
    }
    return sj.toString();
  }



}
