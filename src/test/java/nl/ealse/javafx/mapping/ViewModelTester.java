package nl.ealse.javafx.mapping;

import java.time.LocalDate;
import javafx.embed.swing.JFXPanel;
import nl.ealse.test.model.Address;
import nl.ealse.test.model.MartialState;
import nl.ealse.test.model.Person;
import nl.ealse.test.view.PersonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ViewModelTester {
  
  private PersonController controller;
  
  ViewModelTester() {
    new JFXPanel();
  }
  
  @Test
  void testMapping() {
    controller.initialize();
  }
  
  @BeforeEach
  void setup() {
    controller = new PersonController();
    Person model = new Person();
    controller.setModel(model);
    
    Address billingAddress = new Address();
    Address homeAddress = new Address();
    homeAddress.setStreet("Helena Hoeve");
    homeAddress.setNumber(26);
    homeAddress.setCity("Gouda");
    model.setBillingAddress(billingAddress);
    model.setHomeAddress(homeAddress);
    
    model.setDateOfBirth(LocalDate.of(1954, 2, 16));
    model.setMartialState(MartialState.Married);
    
    model.setFoo("nvt");
    model.setRelationNumber(1234L);
  }

}
