package nl.ealse.test.view;

import javafx.scene.control.TextField;

public class AddressController {
  
  private TextField street;
  
  private TextField number;
  
  private TextField city;
  
  public AddressController() {
    initControls();
  }
  
  private void initControls() {
    street = new TextField();
    number = new TextField();
    city = new TextField();
  }

  public TextField getStreet() {
    return street;
  }

  public TextField getNumber() {
    return number;
  }

  public TextField getCity() {
    return city;
  }


}
