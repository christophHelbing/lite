## Structural Refactoring
* Changing entities to kotlin data classes, make attributes immutable and removed nullability.
* Create interface for the service 
* Changing serivce implementation
* Removed repository from rest-controller -> no clean 3 layer architecture
* Create package for the domain customer
* Renaming files/packages for better overview

### Solves
* **"This building looks weird"** - `architecture`
    - Is everything where it is supposed to be?
    - What are considerations to change the projects structure?


## Tasks
- **"Where is my Invoice?"** - `database`
  - Do you really think it's a good idea to just remove an invoice?
  > I think it is no good idea
  - If yes, what's happening with the customer?
  - If no, why don't you prevent it?
  > I think archiving by setting a certain state would be better

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
   > Validation was added
- **"Give me all your money"** - `feature`
  - write an endpoint to mark an invoice paid
  - I also want to know when this happened
  - What if I don't pay the whole bill?
  > Fields for paid-timestamp, amount of payment are added to the table and domain model.
    The state of payment is shown via the invoice state, so it is possible to pay partly.
    
    
    
 