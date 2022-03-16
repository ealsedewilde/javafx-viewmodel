package nl.ealse.javafx.mapping;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

class ViewModelTest {
  
  ViewModelTest() {
    new JFXPanel();
  }
  
  @Test
  void testMapping1() {
    ViewA view = new ViewA(true);
    ModelA model = new ModelA();
    model.setModelC(null);
    ViewModel.viewToModel(view, model);
  }
  
  @Test
  void testMapping2() {
    ViewA view = new ViewA(true);
    ModelA model = new ModelA();
    model.setModelC(null);
    ViewModel.modelToView(view, model);
  }
  
  @Test
  void testMapping3() {
    ViewA view = new ViewA(false);
    view.getViewC().getViewD().getFoo().setText("bla");
    ModelA model = new ModelA();
    ViewModel.viewToModel(view, model);
  }
  
  @Test
  void testMapping4() {
    ViewA view = new ViewA(false);
    view.getViewC().getViewD().getFoo().setText("bla");
    ModelA model = new ModelA();
    ViewModel.modelToView(view, model);
  }
  
  public static class ViewA extends ViewB {
    
    public ViewA(boolean empty) {
      if (empty) {
        viewC = null;
      }
     }
    
  }
  
  public static class ViewB {
    protected ViewC viewC = new ViewC();

    public ViewC getViewC() {
      return viewC;
    }
  }
  
  public static class ViewC {
    private ViewD viewD = new ViewD();

    public ViewD getViewD() {
      return viewD;
    }
    
  }
  
  public static class ViewD {
    private TextField foo = new TextField();

    public TextField getFoo() {
      return foo;
    }
  }
  
  public static class ModelA extends ModelB {
    
  }
  
  public static class ModelB {
    public ModelC getModelC() {
      return modelC;
    }

    public void setModelC(ModelC modelC) {
      this.modelC = modelC;
    }

    private ModelC modelC = new ModelC();
  }
  
  public static class ModelC {
    private ModelD modelD = new ModelD();

    public ModelD getModelD() {
      return modelD;
    }

    public void setModelD(ModelD modelD) {
      this.modelD = modelD;
    }
    
  }
  
  public static class ModelD {
    private String foo = "Test";

    public String getFoo() {
      return foo;
    }

    public void setFoo(String foo) {
      this.foo = foo;
    }
  }

}
