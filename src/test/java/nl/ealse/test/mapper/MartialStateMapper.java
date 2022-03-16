package nl.ealse.test.mapper;

import javafx.scene.control.ChoiceBox;
import nl.ealse.javafx.mappers.PropertyMapper;
import nl.ealse.test.model.MartialState;

public class MartialStateMapper implements PropertyMapper<ChoiceBox<String>, MartialState> {

  @Override
  public MartialState getPropertyFromJavaFx(ChoiceBox<String> javaFx) {
    String choice = javaFx.getValue();
    for (MartialState v : MartialState.values()) {
      if (choice.equals(v.getDescription())) {
        return v;
      }
    }
    return null;
  }

  @Override
  public void mapPropertyToJavaFx(MartialState modelProperty, ChoiceBox<String> javaFx) {
    javaFx.setValue(modelProperty.getDescription());
  }

}
