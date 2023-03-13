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
