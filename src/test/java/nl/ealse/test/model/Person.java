package nl.ealse.test.model;

import java.time.LocalDate;

public class Person  extends Relation {
  
  private String Name;
  
  private MartialState martialState;
  
  private String foo;
  
  private LocalDate dateOfBirth;  
  
  private Address homeAddress;

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public MartialState getMartialState() {
    return martialState;
  }

  public void setMartialState(MartialState martialState) {
    this.martialState = martialState;
  }

  public String getFoo() {
    return foo;
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Address getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(Address homeAddress) {
    this.homeAddress = homeAddress;
  }

}
