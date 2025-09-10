# StockFlow Application use cases

# Lookup patterns information

Each root product will have a certain access pattern for counting its instances.

Like mattresses, instances with the root product ID will be grouped by width and length, but NOT colour and height since its not applicable.

Instances of a specific furniture unit will be grouped simply by colour, since the root product has its own measurements. 

# Product instances

- Get the count of instances of a specific root product grouped by certain fields depending on its lookup pattern. **DONE**
- Get a list of all instances of a product, which the frontend can order as it sees fit. **DONE**
- Get the list of product instances reserved for a specific order
- Save new instances by receiving a collection of instances. **DONE**
- When a new client order is saved, all instances that that order has claimed will be marked as reserved and provided the order ID. **DONE**

# Root products

- Query information about a root product **DONE**
- Add a new root product **DONE**
- Update root products
- Delete root products

# Categories

- Query all categories paginated **DONE**
- Create new categories **DONE**
- Update categories
- Delete categories

# Users

- Authentication with a username and password, which returns a JWT to the user **DONE**
- Registration of new users, done only by administrator users
- Updating user details.
- Delete user accounts.

# Orders (Client orders)

- Save a new client order and mark all instances used for it as reserved **DONE**
- Query orders **DONE BASIC QUERYING**
- Update order status and/or information
- Delete orders (if necessary)