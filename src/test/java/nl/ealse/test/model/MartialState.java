package nl.ealse.test.model;


public enum MartialState {
  SINGLE("Allenstaand"),
  MARRIED("Getrouwd"),
  DIVORCED("Gescheiden"),
  PARTNERSHIP("geregistreed partnerschap");
  
   private final String description;
  
  private MartialState(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
