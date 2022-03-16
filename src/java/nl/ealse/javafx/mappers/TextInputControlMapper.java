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
    String text = javaFx.getText().trim();
    if (text == null || text.isEmpty()) {
      return null;
    }
    return f.apply(text);
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, TextInputControl javaFx) {
    javaFx.setText(modelProperty.toString());
  }

}
