package nl.ealse.javafx.mappers;

import javafx.scene.control.Label;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LabelIntegerMapperTest extends FXBase {

  private final LabelMapper<Integer> sut = new LabelMapper<>(s -> Integer.valueOf(s));

  @Test
  void getPropertyFromJavaFx() {
    Label lbl = new Label();
    lbl.setText("1234");
    Integer result = sut.getPropertyFromJavaFx(lbl);
    Assertions.assertEquals(Integer.valueOf(1234), result);
  }

  @Test
  void mapPropertyToJavaFx() {
    Label lbl = new Label();
    sut.mapPropertyToJavaFx(Integer.valueOf(1234), lbl);
    Assertions.assertEquals("1234", lbl.getText());
  }

}
