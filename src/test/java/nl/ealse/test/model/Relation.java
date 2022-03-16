package nl.ealse.test.model;

public class Relation {
  
  private long relationNumber;
  
  private Address BillingAddress;

  public long getRelationNumber() {
    return relationNumber;
  }

  public void setRelationNumber(long relationNumber) {
    this.relationNumber = relationNumber;
  }

  public Address getBillingAddress() {
    return BillingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    BillingAddress = billingAddress;
  }

}
