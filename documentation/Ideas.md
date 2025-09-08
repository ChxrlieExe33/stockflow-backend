# Ideas for implementations

## Creating orders

The main challenge with creating orders is how the product instances are found and added to the order.

One way I'm thinking is on the order creation page, we have:

- A real-time search bar, for finding the root product you are looking for, where you search for a product by name, and you can select one.
- Next to that or below it, would be filters, for size and colour (regardless of what that item is grouped by), and a button to search for instances with those filters, or it could have a subscription to changes and trigger the search after a time.
- Once the button above has been clicked, it will display all the instances that match below, and the user can click an “add” button next to each one to add it to the order

What this would need is the following:

- An endpoint to find root products by name (do a `.toLower()` on the name before querying and inserting to avoid wrong capitalisation causing confusion).
- An endpoint to search for instances of a specific product by product ID (obtained from the first endpoint) with some query params for size and colour.

The frontend logic would be like the following:

1. The user searches for a root product in the search bar and selects it.
2. When selecting it a signal like `currentSelectedProduct` gets populated with the details of the root product.
3. When the user fills out the filters and clicks the find button, it does the request with the query params and receives a list of product instances that match. Also, when they click the button, the query params are cleared.
4. The user clicks the “add” button next to each one, it adds the product instance ID to a signal array.
5. User clicks submit order, and it submits the lot.

