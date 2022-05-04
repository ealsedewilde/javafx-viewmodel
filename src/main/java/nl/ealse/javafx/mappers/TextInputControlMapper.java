package nl.ealse.javafx.mappers;

import java.util.function.Function;
import javafx.scene.control.TextInputControl;

public class TextInputControlMapper<T> implements PropertyMapper<TextInputControl, T> {
  
  private final Function<String, T> f;
  
  TextInputControlMapper(Function<String, T> f){
    this.f = f;
  }

  @Override
  public T getPropertyFromJavaFx(TextInputControl javaFx) {
    String text = javaFx.getText();
    if (text == null || text.isEmpty()) {
      return null;
    }
    return f.apply(text.trim());
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, TextInputControl javaFx) {
    javaFx.setText(modelProperty == null ? "" : modelProperty.toString());
  }

}
