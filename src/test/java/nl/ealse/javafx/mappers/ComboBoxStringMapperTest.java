package nl.ealse.javafx.mappers;

import javafx.scene.control.ComboBox;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComboBoxStringMapperTest extends FXBase {
  
  private ComboBoxMapper<String> sut = new ComboBoxMapper<>();
  
  @Test
  void getPropertyFromJavaFx() {
    ComboBox<String> box = new ComboBox<>();
    box.setValue("1234");
    String result = sut.getPropertyFromJavaFx(box);
    Assertions.assertEquals("1234", result);
  }
  
  @Test
  void mapPropertyToJavaFx() {
    ComboBox<String> box = new ComboBox<>();
    sut.mapPropertyToJavaFx("1234", box);
    String result = box.getValue();    
    Assertions.assertEquals("1234", result);
  }



}
