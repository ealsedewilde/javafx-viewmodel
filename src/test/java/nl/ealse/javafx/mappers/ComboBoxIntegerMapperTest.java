package nl.ealse.javafx.mappers;

import javafx.scene.control.ComboBox;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComboBoxIntegerMapperTest extends FXBase {
  
  private ComboBoxMapper<Integer> sut = new ComboBoxMapper<>();
  
  @Test
  void getPropertyFromJavaFx() {
    ComboBox<Integer> box = new ComboBox<>();
    box.setValue(1234);
    int result = sut.getPropertyFromJavaFx(box);
    Assertions.assertEquals(1234, result);
  }
  
  @Test
  void mapPropertyToJavaFx() {
    ComboBox<Integer> box = new ComboBox<>();
    sut.mapPropertyToJavaFx(1234, box);
    int result = box.getValue();    
    Assertions.assertEquals(1234, result);
  }



}
