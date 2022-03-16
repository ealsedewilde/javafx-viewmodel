package nl.ealse.test.view;

import javafx.scene.control.Label;

public class RelationView {
  
  private Label relationNumber;
  
  public RelationView() {
    initControls();
  }
   
  private void initControls() {
    relationNumber = new Label();
  }

  public Label getRelationNumber() {
    return relationNumber;
  }

}
