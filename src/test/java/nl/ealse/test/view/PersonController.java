package nl.ealse.test.view;

import nl.ealse.javafx.mapping.Mapping;
import nl.ealse.javafx.mapping.ViewModel;
import nl.ealse.test.model.Person;

public class PersonController extends PersonView {
  
  @Mapping(mapOn = "billingAddress")
  private AddressController billingAddressController;

  @Mapping(mapOn = "homeAddress")
  private AddressController homeAddressController;
  
  private Person model;
  
  public PersonController() {
    initControls();
  }
  
  private void initControls() {
    this.billingAddressController = new AddressController();
    this.homeAddressController = new AddressController();
  }
  
  public void initialize() {
    ViewModel.modelToView(this, model);
  }
  
  public void save() {
    ViewModel.viewToModel(this, model);
  }

  public AddressController getBillingAddressController() {
    return billingAddressController;
  }

  public AddressController getHomeAddressController() {
    return homeAddressController;
  }

  public void setModel(Person model) {
    this.model = model;
  }

}
