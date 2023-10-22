package nl.ealse.javafx.mappers;

import javafx.scene.control.Control;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Non function placeholder mapper.
 *
 * @author ealse
 *
 */
public class DefaultMapper implements PropertyMapper<Control, Object> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMapper.class);

  @Override
  public Object getPropertyFromJavaFx(Control javaFx) {
    LOGGER.warn("No mapping for {}", javaFx.getClass().getSimpleName());
    return null;
  }

  @Override
  public void mapPropertyToJavaFx(Object modelProperty, Control javaFx) {
    LOGGER.warn("No mapping for {}", modelProperty.getClass().getSimpleName());
  }

}
