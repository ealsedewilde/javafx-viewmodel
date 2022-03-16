package nl.ealse.javafx.mappers;

import javafx.scene.control.ChoiceBox;

public class ChoiceBoxMapper<T> implements PropertyMapper<ChoiceBox<T>, T> {

  @Override
  public T getPropertyFromJavaFx(ChoiceBox<T> javaFx) {
    return javaFx.getValue();
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, ChoiceBox<T> javaFx) {
    javaFx.setValue(modelProperty);

  }

}
