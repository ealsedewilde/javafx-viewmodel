# javafx-viewmodel
ViewModel for JavaFX according the Model-View-ViewModel paradigm.

### Overview

This framework has two methods which are used in the javafx Controller:

1. `ViewModel.modelToView(this, model);`
2. `ViewModel.viewToModel(this, model);`

The Controller is the object that holds the javafx controls (e.g. TextField).

The Model (a structure op POJO's) holds the (persistent) data. The Controller must has a reference to the Model object.

### Basic principle

The mapping is basically done on name equality of a javafx control with a property in the model.

So a unique TextField  named 'firstName' will map on a unique property 'firstName' in the model. Whenever  the uniqueness can't be met the `@Mapping` annotation must be used to guide the mapping.

The actual mapping is done by classes that implement the `nl.ealse.javafx.mappers.PropertyMapper` interface. This framework comes with a set of default implementations.

### PropertyMappers

There are two types of property mappers:

1. Generic
2. Specific for a specific javafx control / model property combination

The class `nl.ealse.javafx.mappers.MapperRegistry` holds the generic property mappers and is initialized  with the default implementations.

The generic property mappers are lookedup by a key that consists of the simple class name of the javafx control and the simple class name of the model property (e.g. TextFieldString).

You can override or add your own property mapper implementation by calling:

`MapperRegistry.registerPropertyMapper(<yourPropertyMapper>, <javafx control class>, <model property class>);`

A <u>specific</u> propertymapper is linked to a specific javafx control via a `@Mapping` annotation. You typically need this when the property in the model is an enum.

### @Mapping annotation

This annotation can be put on a javafx control or the getter for a javafx control.

The mapping has three attributes:

1. ignore=true; the control will be exclude from mapping
2. mapsOn; a String indicating the name of the POJO in the model to map on.
3. propertyMapper; the class name of a dedicated `PropertyMapper` for this javafx control.

### Restrictions

* The properties in the Model must have a getter and setter
* The javafx controls in the Controller must have a getter.
* The javafx controls in the Controller must all be initialized before calling `ViewModel.modelToView(this, model);`
* The model reference in the controller must not have a getter or a have `@mapping(ignore=true)`.
* Whenever a needed POJO in the model is not initialized, the ViewModel will try to initialize it calling  a zero argument constructor. 

### Test your mapping

via the method `ViewModel.explain(view.class, model.class);`  you can get a report on how mapping will be done. Carefully examine this report. Missing mappings might either be because missing equality in names or duplicate mappings. (e.g. if you a control/property named 'street' in a home address and a billing address then you have to make sure that the mapping is unique using a `@Mapping` annotation.)

### Example

The unit test `nl.ealse.javafx.mapping.ViewModelTest` demonstrates how a Controller with super classes and embedded Controllers maps on a model structure of POJO's.

The class `nl.ealse.test.mapper.MartialStateMapper` demostrates a enum specificproperty mapper.