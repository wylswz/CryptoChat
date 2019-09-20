# Controllers
This contains all the logics for activities/fragments

## How to add an activity?
- Create a layout in `res/layout` folder
- Create a new Controller extending Activity
- Set content view in the controller's onCreate() method
- Set up event listener and ...

## How to add fragment to navigation framework
- Create a new destination in `res/navigation/nav.xml` along with layout/controller
- Set up a new buttom in `res/menu/button_nav.xml` with id referring to the nav destination created in last step
- Implement logics in controller created