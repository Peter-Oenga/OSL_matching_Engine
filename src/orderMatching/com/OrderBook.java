package orderMatching.com;

import java.util.*;

public class OrderBook {
    private String instrument;
    private Map<Double, List<Order>> buyOrders;
    private Map<Double, List<Order>> sellOrders;

    public OrderBook(String instrument) {
        this.instrument = instrument;
        this.buyOrders = new TreeMap<>(Collections.reverseOrder());
        this.sellOrders = new TreeMap<>();
    }

    // Methods to add, remove, and retrieve orders from buyOrders and sellOrders
    // ...

    public void addOrder(Order order) {
        // Check the order type (buy or sell)
        if (order.getOrderType().equals("buy")) {
            // Handle buy orders
            double price = order.getPrice();

            // Check if there are existing orders at this price
            List<Order> ordersAtPrice = buyOrders.getOrDefault(price, new ArrayList<>());

            // Add the new buy order to the list of orders at this price
            ordersAtPrice.add(order);

            // Update the buyOrders map with the modified list
            buyOrders.put(price, ordersAtPrice);
        } else if (order.getOrderType().equals("sell")) {
            // Handle sell orders
            double price = order.getPrice();

            // Check if there are existing orders at this price
            List<Order> ordersAtPrice = sellOrders.getOrDefault(price, new ArrayList<>());

            // Add the new sell order to the list of orders at this price
            ordersAtPrice.add(order);

            // Update the sellOrders map with the modified list
            sellOrders.put(price, ordersAtPrice);
        } else {
            // Handle unsupported order types or errors
            System.out.println("Unsupported order type: " + order.getOrderType());
        }
    }

    public void matchOrders() {
        // Iterate through buy and sell orders, looking for matches
        for (Map.Entry<Double, List<Order>> buyEntry : buyOrders.entrySet()) {
            double buyPrice = buyEntry.getKey();
            List<Order> buyOrdersAtPrice = buyEntry.getValue();

            if (sellOrders.containsKey(buyPrice)) {
                List<Order> sellOrdersAtPrice = sellOrders.get(buyPrice);

                // Match orders with the same price
                matchOrdersAtPrice(buyOrdersAtPrice, sellOrdersAtPrice);
            }
        }
    }

    // New method to match orders at a specific price
    private void matchOrdersAtPrice(List<Order> buyOrdersAtPrice, List<Order> sellOrdersAtPrice) {
        Iterator<Order> buyIterator = buyOrdersAtPrice.iterator();
        while (buyIterator.hasNext()) {
            Order buyOrder = buyIterator.next();
            Iterator<Order> sellIterator = sellOrdersAtPrice.iterator();
            while (sellIterator.hasNext()) {
                Order sellOrder = sellIterator.next();

                if (buyOrder.getQuantity() > 0 && sellOrder.getQuantity() > 0) {
                    // Implement your matching logic here

                    // Calculate the trade quantity (can be less than or equal to min(buy, sell))
                    int tradeQuantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());

                    // Execute the trade by updating quantities
                    buyOrder.setQuantity(buyOrder.getQuantity() - tradeQuantity);
                    sellOrder.setQuantity(sellOrder.getQuantity() - tradeQuantity);

                    // Create trade records and send trade messages (not shown here)

                    // Remove orders with zero quantity (fully executed)
                    if (buyOrder.getQuantity() == 0) {
                        buyIterator.remove(); // Use the iterator to remove the order
                    }
                    if (sellOrder.getQuantity() == 0) {
                        sellIterator.remove(); // Use the iterator to remove the order
                    }
                }
            }
        }

        // Update the order book with residual quantities if needed
    }

    
    	public void printOrderBook() {
    	    System.out.println("Buy Orders:");
    	    for (Map.Entry<Double, List<Order>> entry : buyOrders.entrySet()) {
    	        double price = entry.getKey();
    	        List<Order> orders = entry.getValue();
    	        System.out.println("Price: " + price);
    	        for (Order order : orders) {
    	            System.out.println("   " + order.getOrderType() + " - Quantity: " + order.getQuantity());
    	        }
    	    }

    	    System.out.println("Sell Orders:");
    	    for (Map.Entry<Double, List<Order>> entry : sellOrders.entrySet()) {
    	        double price = entry.getKey();
    	        List<Order> orders = entry.getValue();
    	        System.out.println("Price: " + price);
    	        for (Order order : orders) {
    	            System.out.println("   " + order.getOrderType() + " - Quantity: " + order.getQuantity());
    	        }
    	    }
    	}


}
