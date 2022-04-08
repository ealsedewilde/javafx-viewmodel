package nl.ealse.javafx.mappers;

import java.util.function.BiFunction;
import javafx.scene.control.TextInputControl;

public class TextInputControlFormattingMapper<T,F>
    implements PropertyMapper<TextInputControl, T> {

  private final F formatter;
  
  private final BiFunction<String, F, T> from;
  private final BiFunction<T, F, String> to;
  
  TextInputControlFormattingMapper(F formatter, BiFunction<String, F, T> from, BiFunction<T, F, String> to) {
    this.formatter = formatter;
    this.from = from;
    this.to = to;
  }

  @Override
  public T getPropertyFromJavaFx(TextInputControl javaFx) {
    String text = javaFx.getText();
    if (text == null || text.isEmpty()) {
      return null;
    }
    return from.apply(text.trim(), formatter);
  }

  @Override
  public void mapPropertyToJavaFx(T modelProperty, TextInputControl javaFx) {
    if (modelProperty != null) {
      javaFx.setText(to.apply(modelProperty, formatter));
    }
  }

}
