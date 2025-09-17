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

# On order instances

- This is to account for the fact some product instances for a client order may need to be ordered from the factory; it will be very similar to the instances table, but only for products on order.
- When a client order is created, if it had any items to be ordered, add them here.
- Retrieve a list of all ordered instances.
- When they arrive, have an endpoint to mark them as received and possibly move them to the main product instances table.


# Root products

- Query information about a root product **DONE**
- Add a new root product **DONE**
- Update root products **DONE**
- Delete root products **DONE**

# Categories

- Query all categories paginated **DONE**
- Create new categories **DONE**
- Update categories **DONE**
- Delete categories

# Users

- Authentication with a username and password, which returns a JWT to the user **DONE**
- Registration of new users, done only by administrator users **DONE**
- Updating user details.
- Delete user accounts.

# Orders (Client orders)

- Save a new client order and mark all instances used for it as reserved **DONE**
- Provide the option on order creation to take product instances "on order" as part of the sale.
- Query orders **DONE BASIC QUERYING**
- Update order status and/or information **DONE**
- Delete orders (if necessary) **DONE**

# Configuration

- Have some sort of setup phase when the application is first "installed" to create the first admin user and choose the company name, amongst other details.