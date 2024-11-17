package nl.ealse.javafx.mappers;

import java.math.BigInteger;
import javafx.scene.control.TextField;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TextInputControlBigIntegerMapperTest extends FXBase {

  private TextInputControlMapper<BigInteger> sut = 
      new TextInputControlMapper<>(s -> new BigInteger(s));

  @Test
  void getPropertyFromJavaFx() {
    TextField tf = new TextField();
    tf.setText("1234");
    BigInteger result = sut.getPropertyFromJavaFx(tf);
    Assertions.assertEquals(new BigInteger("1234"), result);
  }

  @Test
  void mapPropertyToJavaFx() {
    BigInteger source = new BigInteger("1234");
    TextField tf = new TextField();
    sut.mapPropertyToJavaFx(source, tf);
    Assertions.assertEquals("1234", tf.getText());
  }

}
