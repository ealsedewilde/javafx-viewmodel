package nl.ealse.javafx.mappers;

import java.util.function.Function;
import javafx.scene.control.Label;

public class LabelMapper<T> implements PropertyMapper<Label, T> {
  
  private final Function<String, T> f;
  
  LabelMapper(Function<String, T> f){
    this.f = f;
  }

  @Override
  public T getPropertyFromJavaFx(Label javaFx) {
    String text = javaFx.getText();
    if (text == null || text.isEmpty()) {
      return null;
    }
    return f.apply(text);
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, Label javaFx) {
    javaFx.setText(modelProperty == null ? "" : modelProperty.toString());
  }

}
