## Structural Refactoring
* Changing entities to kotlin data classes
* Make attributes in data classes immutable (val instead of var)
* Removed nullability of the most attributes in data classes
* Create interface for the service 
* Changing service implementation
* Removed repository from rest-controller -> no clean 3 layer architecture
* Create package for the domain customer
* Renaming files/packages for better overview

### Solves
* **"This building looks weird"** - `architecture`
    - Is everything where it is supposed to be?
    - What are considerations to change the projects structure?


## Tasks Implemented
- **"Where is my Invoice?"** - `database`
  - Do you really think it's a good idea to just remove an invoice?
  > I think it is no good idea to delete an invoice. It is better to archive them.
  - If yes, what's happening with the customer?
  - If no, why don't you prevent it?
  > I think archiving by setting a certain state would be better, therefor Iam using
  > the state CLOSED and a put Endpoint to set a certain invoice CLOSED.

- **"1+1=5"** - `feature`
  - Some values should not be set from outside!
  - It's better to calculate some values  e.g. a total price
  - Don't you want to safe that total price?
   > Creating a view-model which is only a subset of the domain attributes, so
     only relevant attributes can be set via the rest call.
     Calculated values should not be stored in the database, some changes in the calculation
     logic lead to a data migration (priceGross should no longer be stored in the database)
 
- **"You talk in riddles"** - `feature`
  - Should it be possible to create an invoice with negative price?
  - or was the due date perhaps already yesterday?
   > It should not be possible to set a negative price. I implemented some simple validation to the saveInvoice
   > service method. There are more possibilities for validation rules, I could implement.
   > In a more complex scenario, I would create a separate Validation-Layer/Service, which 
   > contains all validations and which can be used in a service method, if necessary.

- **"Give me all your money"** - `feature`
  - write an endpoint to mark an invoice paid
  - I also want to know when this happened
  - What if I don't pay the whole bill?
  > Fields for paid-timestamp, amount of payment are added to the table and domain model.
    The state of payment is shown via the invoice state, so it is possible to pay partly.

- **"these are not the droids you're looking for"** - `security`
  - it should be obvious, that it's a bad idea to let everyone see your invoices
  - implement some basic authorization and authentication to protect the API from unauthorized users 
  > Added simple spring security basic authentication. Using the InMemoryUserDetailsManager for storing some allowed
  > users. Securing the service methods via PreAuthorize. The writing routes can only be called by the admin and the read
  > routes by the user and admin. \
  > In a more complex scenario, we should think about using an IAM software like keycloak.

### Tasks not implemented
- **"I am curious"** - `feature`
  - I think it would be interesting to see who changed the data and when
  - Could this be a solution, that fits all tables at once
  - Would a rollback be nice as well?
- **"Fast Forward"** - `feature`
  - Some data really should never be changed anymore. Make the Invoices an append-only data structure
    where every change results in a new dataset which is linked to the previous version
  > For these two tasks I think we start to talk about Event-Sourcing. This is a pattern where all changes are stored
  > by an immutable sequence of events. These events can also contain auditing information, so we can see, who was
  > changing which field. The events represent a history for an invoice, so we are also able to roll back to a certain
  > state. \
  > I was implementing a small example for an event-driven-architecture, which you can find [here](https://github.com/christophHelbing/event-sourcing-example)
  > \
  > Another way to store some auditing information is enabling/using JPA Auditing. But Iam not 
  > very experienced in Spring Security, so I didn't implement it.
 
 