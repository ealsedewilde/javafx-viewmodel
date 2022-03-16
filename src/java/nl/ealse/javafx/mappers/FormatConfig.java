package nl.ealse.javafx.mappers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatConfig {
  
  private Locale locale;
  
  private DateTimeFormatter dateFormatter;
  
  private DecimalFormat decimalFormat;
  
  public FormatConfig() {
    initialize();
  }
  
  private void initialize() {
    locale = Locale.getDefault();
    dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(locale);
    decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
    decimalFormat.applyPattern("###,##0.00");
    
  }

  public DateTimeFormatter getDateFormatter() {
    return dateFormatter;
  }

  public void setDateFormatter(DateTimeFormatter dateFormatter) {
    this.dateFormatter = dateFormatter;
  }

  public DecimalFormat getDecimalFormat() {
    return decimalFormat;
  }

  public void setDecimalFormat(DecimalFormat decimalFormat) {
    this.decimalFormat = decimalFormat;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

}
