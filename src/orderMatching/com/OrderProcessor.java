package orderMatching.com;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class OrderProcessor {
    private Map<String, OrderBook> orderBooks; // Map of instrument to order book
    private Queue<Order> incomingQueue; // Incoming orders queue

    public OrderProcessor() {
        this.orderBooks = new HashMap<>();
        this.incomingQueue = new LinkedList<>();
    }

    // Method to receive incoming orders and process them
    public void receiveOrder(Order order) {
        // Enqueue the incoming order
        incomingQueue.offer(order);

        // Attempt to store the order in the corresponding order book
        if (orderBooks.containsKey(order.getInstrument())) {
            OrderBook orderBook = orderBooks.get(order.getInstrument());
            orderBook.addOrder(order);
            // Send acknowledgment message to the output queue (not shown here)
            sendAcknowledgment(order);
        } else {
            // Handle the case where the instrument is not supported
            System.out.println("Instrument not supported: " + order.getInstrument());
        }
    }

    // Method to send acknowledgment message (to be implemented)
    private void sendAcknowledgment(Order order) {
        // Implement your logic to send acknowledgment here
        // This could involve interacting with a message queue or other communication mechanism.
        System.out.println("Acknowledgment sent for order: " + order.toString());
    }

    // Method to add or update an order book for an instrument
    public void addOrUpdateOrderBook(String instrument) {
        if (!orderBooks.containsKey(instrument)) {
            OrderBook orderBook = new OrderBook(instrument);
            orderBooks.put(instrument, orderBook);
        }
    }
    public void processOrders() {
        for (OrderBook orderBook : orderBooks.values()) {
            orderBook.matchOrders();
        }
    }
}
