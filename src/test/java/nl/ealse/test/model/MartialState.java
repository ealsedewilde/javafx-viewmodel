package nl.ealse.test.model;


public enum MartialState {
  Single("Allenstaand"),
  Married("Getrouwd"),
  Divorced("Gescheiden");
  
   private final String description;
  
  private MartialState(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
