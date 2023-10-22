package nl.ealse.javafx.mappers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import javafx.scene.control.Control;
import nl.ealse.javafx.mapping.MappingException;

public class MapperRegistry {
  private static FormatConfig formatConfig = new FormatConfig();

  /**
   * All registered standard {@link PropertyMapper}s.
   */
  private static final Map<String, PropertyMapper<Control, Object>> mappers = new HashMap<>();

  /**
   * The complete set of standard {@link PropertyMapper}s.
   */
  static {
    mappers.put("CheckBoxBoolean", handle(new CheckBoxMapper()));

    mappers.put("DatePickerLocalDate", handle(new DatePickerMapper()));

    mappers.put("ChoiceBoxString", handle(new ChoiceBoxMapper<String>()));
    mappers.put("ChoiceBoxInteger", handle(new ChoiceBoxMapper<Integer>()));

    mappers.put("ComboBoxString", handle(new ComboBoxMapper<String>()));
    mappers.put("ComboBoxInteger", handle(new ComboBoxMapper<Integer>()));

    mappers.put("LabelString", handle(new LabelMapper<String>(s -> s)));
    mappers.put("LabelInteger", handle(new LabelMapper<Integer>(s -> ValueOf.integerValueOf(s))));
    mappers.put("LabelLong", handle(new LabelMapper<Long>(s -> ValueOf.longValueOf(s))));

    BiFunction<String, DecimalFormat, BigDecimal> from = (s, f) -> {
      try {
        return new BigDecimal(f.parse(s).toString());
      } catch (ParseException e) {
        throw new MappingException("Invalid decimal input " + s);
      }
    };
    mappers.put("TextFieldBigDecimal",
        handle(new TextInputControlFormattingMapper<BigDecimal, DecimalFormat>(
            formatConfig.getDecimalFormat(), from, (t, f) -> f.format(t))));

    mappers.put("TextFieldString", handle(new TextInputControlMapper<String>(s -> s)));
    mappers.put("TextFieldInteger",
        handle(new TextInputControlMapper<Integer>(s -> ValueOf.integerValueOf(s))));
    mappers.put("TextFieldLong",
        handle(new TextInputControlMapper<Long>(s -> ValueOf.longValueOf(s))));

    mappers.put("TextFieldLocalDate",
        handle(new TextInputControlFormattingMapper<LocalDate, DateTimeFormatter>(
            formatConfig.getDateFormatter(), (s, f) -> LocalDate.parse(s, f),
            (t, f) -> t.format(f))));

    mappers.put("TextAreaString", handle(new TextInputControlMapper<String>(s -> s)));
  }

  private MapperRegistry() {
    // Utility class
  }

  @SuppressWarnings("unchecked")
  private static PropertyMapper<Control, Object> handle(PropertyMapper<? extends Control, ?> p) {
    return (PropertyMapper<Control, Object>) p;
  }

  /**
   * Add an additional standard {@link PropertyMapper}.
   *
   * @param propertyMapper - the {@link PropertyMapper} to register
   * @param javaFxClass - class to link with the {@link PropertyMapper}
   * @param modelClass - class to link with the {@link PropertyMapper}
   */
  public static void registerPropertyMapper(
      PropertyMapper<? extends Object, ? extends Object> propertyMapper, Class<?> javaFxClass,
      Class<?> modelClass) {
    String key = javaFxClass.getSimpleName() + modelClass.getSimpleName();
    mappers.put(key, handle(propertyMapper));
  }

  public static Optional<PropertyMapper<Control, Object>> getPropertyMapper(String key) {
    return Optional.ofNullable(mappers.get(key));
  }

  public static void setFormatConfig(FormatConfig formatConfig) {
    MapperRegistry.formatConfig = formatConfig;
  }

  private static class ValueOf {
    private static Integer integerValueOf(String s) {
      try {
        return Integer.valueOf(s);
      } catch (NumberFormatException e) {
        return 0;
      }
    }

    private static Long longValueOf(String s) {
      try {
        return Long.valueOf(s);
      } catch (NumberFormatException e) {
        return 0L;
      }
    }
  }

}
