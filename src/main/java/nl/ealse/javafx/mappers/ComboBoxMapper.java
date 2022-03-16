package nl.ealse.javafx.mappers;

import javafx.scene.control.ComboBox;

public class ComboBoxMapper<T> implements PropertyMapper<ComboBox<T>, T> {

  @Override
  public T getPropertyFromJavaFx(ComboBox<T> javaFx) {
    return javaFx.getValue();
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, ComboBox<T> javaFx) {
    javaFx.setValue(modelProperty);

  }

}
