package nl.ealse.javafx.mappers;

import javafx.scene.control.Label;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LabelStringMapperTest extends FXBase {

  private final LabelMapper<String> sut = new LabelMapper<>(s -> s);

  private final String s = "Test data";

  @Test
  void getPropertyFromJavaFx() {
    Label lbl = new Label();
    lbl.setText(s);
    String result = sut.getPropertyFromJavaFx(lbl);
    Assertions.assertEquals(s, result);
  }

  @Test
  void mapPropertyToJavaFx() {
    Label lbl = new Label();
    sut.mapPropertyToJavaFx(s, lbl);
    Assertions.assertEquals(s, lbl.getText());
  }

}
