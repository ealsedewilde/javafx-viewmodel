package nl.ealse.javafx.mappers;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import nl.ealse.test.FXBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DatePickerMapperTest extends FXBase {

  private DatePickerMapper sut = new DatePickerMapper();

  private final LocalDate date = LocalDate.of(2020, 12, 5);

  @Test
  void getPropertyFromJavaFx() {
    DatePicker dp = new DatePicker();
    dp.setValue(date);
    LocalDate result = sut.getPropertyFromJavaFx(dp);
    Assertions.assertEquals(date, result);
  }

  @Test
  void mapPropertyToJavaFx() {
    DatePicker dp = new DatePicker();
    sut.mapPropertyToJavaFx(date, dp);
    Assertions.assertEquals(date, dp.getValue());
  }

}
