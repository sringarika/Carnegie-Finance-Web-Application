Webapp
------

This is the real webapp. The final version of the Mutual Funds web application.

It is using MVC. The view JSPs can be found in `WebContent/WEB-INF/`. All of
them are using JSP EL. No Java snippets are involved. The Controller and the
Model is in the `src` directory.

In `src/controller`, the actions are implemented using separate classes. The
permission and authentication code are centralized in the Controller. The
abstract `Action` class has been modified for access level checking.

In `src/databean`, we are storing all the amount and shares balance as `double`
type as required. They are also stored as `double` in the database by
GenericDAO. However, for all calculations, they are converted to `BigDecimal`
in the Java code and then converted back to `double` before persistence. In this
way, we can precisely calculate with 2 or 3 digits after the decimal point
while still complying with the requirement to use `double` type.

In `src/viewbean`, we have a few view beans for different views. They are
instantiated using `GenericViewDAO` in `src/model`. We wrote the SQL code with
`JOIN`s to get the result.

In `src/formbean`, we have a lot of form beans using `FormBeanFactory`. However,
`TransitionDayForm` is not using `FormBeanFactory` because we are handling more
than one value for each parameter. Customized request parsing code is used
instead. Form validations are done there.
