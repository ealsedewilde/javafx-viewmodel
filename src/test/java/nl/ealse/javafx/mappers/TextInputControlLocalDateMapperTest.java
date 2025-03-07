package nl.ealse.javafx.mappers;

import java.time.LocalDate;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextInputControlLocalDateMapperTest extends FXBase {

  private PropertyMapper<Control, Object> sut =
      MapperRegistry.getPropertyMapper("TextFieldLocalDate").get();

  @Test
  void getPropertyFromJavaFx() {
    TextField tf = new TextField();
    tf.setText("05-12-2020");
    LocalDate result = (LocalDate) sut.getPropertyFromJavaFx(tf);
    Assertions.assertEquals(LocalDate.of(2020, 12, 5), result);
  }

  @Test
  void mapPropertyToJavaFx() {
    LocalDate source = LocalDate.of(2020, 12, 5);
    TextField tf = new TextField();
    sut.mapPropertyToJavaFx(source, tf);
    Assertions.assertEquals("05-12-2020", tf.getText());
  }

}
