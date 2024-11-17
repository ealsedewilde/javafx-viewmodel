package nl.ealse.javafx.mapping;

import java.time.LocalDate;
import nl.ealse.javafx.mvvm.ViewModel;
import nl.ealse.test.FXBase;
import nl.ealse.test.model.Address;
import nl.ealse.test.model.MartialState;
import nl.ealse.test.model.Person;
import nl.ealse.test.view.PersonController;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewModelTest extends FXBase {
  
  private PersonController controller;
  private Person model;
  
  @Test
  void testExplain() {
     String s = ViewModel.explain(PersonController.class, Person.class);
     System.out.println(s);
     int count = StringUtils.countMatches(s, "uses");
     Assertions.assertEquals(10, count);
  }
  
  @Test
  void testModelToView() {
    String pre = controller.getHomeAddressController().getStreet().getText();
    Assertions.assertEquals("", pre);
    controller.initialize();
    String post = controller.getHomeAddressController().getStreet().getText();
    Assertions.assertEquals("Helena Hoeve", post);
  }
  
  @Test
  void testViewToModel() {
    controller.initialize();
    String pre = controller.getHomeAddressController().getStreet().getText();
    Assertions.assertEquals("Helena Hoeve", pre);
    controller.getHomeAddressController().getStreet().setText("Crabethstraat");
    controller.save();
    String post = model.getHomeAddress().getStreet();
    Assertions.assertEquals("Crabethstraat", post);
  }
  
  @BeforeEach
  void setup() {
    controller = new PersonController();
    model = new Person();
    controller.setModel(model);
    
    Address billingAddress = new Address();
    Address homeAddress = new Address();
    homeAddress.setStreet("Helena Hoeve");
    homeAddress.setNumber(26);
    homeAddress.setCity("Gouda");
    model.setBillingAddress(billingAddress);
    model.setHomeAddress(homeAddress);
    
    model.setDateOfBirth(LocalDate.of(1954, 2, 16));
    model.setMartialState(MartialState.MARRIED);
    
    model.setFoo("nvt");
    model.setRelationNumber(1234L);
  }

}
