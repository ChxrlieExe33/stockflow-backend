# StockFlow Application use cases

# Lookup patterns information

Each root product will have a certain access pattern for counting its instances.

Like mattresses, instances with the root product ID will be grouped by width and length, but NOT colour and height since its not applicable.

Instances of a specific furniture unit will be grouped simply by colour, since the root product has its own measurements. 

# Product instances

- Get the count of instances of a specific root product grouped by certain fields depending on its lookup pattern.
- Get a list of all instances of a product, which the frontend can order as it sees fit.
- Persist new instances by receiving a collection of instances.
- When a new client order is saved, all instances that that order has claimed will be marked as reserved and provided the order ID.

# Root products

- Query information about a root product
- Add a new root product
- Update root products
- Delete root products

# Orders (Client orders)

- Save a new client order and mark all instances used for it as reserved.
- Query orders
- Update order status
- Delete orders (if necessary)