package nl.ealse.javafx.mappers;

import java.math.BigDecimal;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextInputControlBigDecimalMapperTest {

  private PropertyMapper<Control, Object> sut =
      MapperRegistry.getPropertyMapper("TextFieldBigDecimal").get();

  @Test
  void getPropertyFromJavaFx() {
    new JFXPanel();
    TextField tf = new TextField();
    tf.setText("12,34");
    BigDecimal result = (BigDecimal) sut.getPropertyFromJavaFx(tf);
    Assertions.assertEquals(BigDecimal.valueOf(12.34), result);
  }

  @Test
  void mapPropertyToJavaFx() {
    new JFXPanel();
    BigDecimal source = BigDecimal.valueOf(12.34);
    TextField tf = new TextField();
    sut.mapPropertyToJavaFx(source, tf);
    Assertions.assertEquals("12,34", tf.getText());
  }

}
