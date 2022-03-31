package nl.ealse.test.view;

import nl.ealse.javafx.mapping.Mapping;
import nl.ealse.javafx.mapping.ViewModel;
import nl.ealse.test.model.Person;

public class PersonController extends PersonView {
  
  @Mapping(mapOn = "billingAddress")
  private AddressController billingAddressController;

  @Mapping(mapOn = "homeAddress")
  private AddressController homeAddressController;
  
  // Don't define a getter on the model!
  // This will break the mapping
  private Person model;
  
  public PersonController() {
    initControls();
  }
  
  private void initControls() {
    this.billingAddressController = new AddressController();
    this.homeAddressController = new AddressController();
  }
  
  public void initialize() {
    // when using fxml, this method will be called by the javafx.fxml.FXMLLoader
    // it will initialize all javafx controls.
    
    // now initialize/load the model 
    ViewModel.modelToView(this, model);
  }
  
  public void save() {
    ViewModel.viewToModel(this, model);
    // and call a service to persist the model.
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

  public Person getModel() {
    return model;
  }

}
