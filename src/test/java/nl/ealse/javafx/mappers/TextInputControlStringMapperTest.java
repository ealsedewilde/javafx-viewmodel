package nl.ealse.javafx.mappers;

import javafx.scene.control.TextField;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextInputControlStringMapperTest extends FXBase {

  private final TextInputControlMapper<String> sut = new TextInputControlMapper<>(s -> s);

  private final String s = "Test data";

  @Test
  void getPropertyFromJavaFx() {
    TextField tf = new TextField();
    tf.setText(s);
    String result = sut.getPropertyFromJavaFx(tf);
    Assertions.assertEquals(s, result);
  }

  @Test
  void mapPropertyToJavaFx() {
    TextField tf = new TextField();
    sut.mapPropertyToJavaFx(s, tf);
    Assertions.assertEquals(s, tf.getText());
  }

}
