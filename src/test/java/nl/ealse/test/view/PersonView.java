package nl.ealse.test.view;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import nl.ealse.javafx.mapping.Mapping;
import nl.ealse.test.mapper.MartialStateMapper;

public class PersonView extends RelationView {
  
  private TextField name;
  
  @Mapping(ignore = true)
  private TextField foo;
  
  @Mapping(propertyMapper = MartialStateMapper.class)
  private ChoiceBox<String> martialState;
  
  private DatePicker dateOfBirth;
  
  public PersonView() {
    initControls();
  }
  
  private void initControls() {
    name = new TextField();
    foo = new TextField();
    martialState = new ChoiceBox<>();
    dateOfBirth = new DatePicker();
  }

  public TextField getName() {
    return name;
  }

  public TextField getFoo() {
    return foo;
  }

  public ChoiceBox<String> getMartialState() {
    return martialState;
  }

  public DatePicker getDateOfBirth() {
    return dateOfBirth;
  }

 

}
