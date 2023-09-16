package orderMatching.com;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessor {
    private Map<String, OrderBook> orderBooks; // Map of instrument to order book
    private Queue<Order> incomingQueue; // Incoming orders queue
    private ExecutorService executor; // Thread pool for parallel order processing

    // List to store executed trades
    private List<Trade> executedTrades;

    public OrderProcessor() {
        this.orderBooks = new HashMap<>();
        this.incomingQueue = new LinkedList<>();
        this.executor = Executors.newFixedThreadPool(4); // Adjust the thread pool size as needed
        this.executedTrades = new ArrayList<>(); // Initialize the list of executed trades
    }

    // Method to receive incoming orders and process them
    public synchronized void receiveOrder(Order order) {
        // Enqueue the incoming order
        incomingQueue.offer(order);

        // Process the order in parallel using a thread pool
        executor.execute(() -> processOrder(order));
    }

    private void sendAcknowledgment(Order order) {
        // Generate a timestamp for the acknowledgment
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);

        // Construct the acknowledgment message
        String acknowledgmentMessage = String.format(
                "Acknowledgment for order ID %d%n" +
                        "Instrument: %s%n" +
                        "Order Type: %s%n" +
                        "Quantity: %d%n" +
                        "Price: %.2f%n" +
                        "Timestamp: %s%n",
                order.getID(),
                order.getInstrument(),
                order.getOrderType(),
                order.getQuantity(),
                order.getPrice(),
                formattedTimestamp
        );

        // Implement your logic to send the acknowledgment here.
        // This could involve interacting with a message queue or other communication mechanism.
        System.out.println("Sending Acknowledgment Message:");
        System.out.println(acknowledgmentMessage);
    }

    // Send trade message to an output queue or external systems
    private void sendTradeMessage(Trade trade) {
        // Implement your logic to send trade messages here.
        // This could involve interacting with a message queue or other communication mechanism.
        System.out.println("Sending Trade Message:");
        System.out.println("Trade executed - " + trade.toString());
    }

    // Process a single order
 // Process a single order
    private void processOrder(Order order) {
        // Ensure that an order book exists for the instrument
        addOrUpdateOrderBook(order.getInstrument());

        // Store the order in the corresponding order book
        OrderBook orderBook = orderBooks.get(order.getInstrument());
        orderBook.addOrder(order);

        // Check for matching orders in the order book
        List<Trade> executedTrades = orderBook.matchOrders();

        // Send acknowledgment message for the incoming order
        sendAcknowledgment(order);

        // Send trade messages for executed trades
        for (Trade trade : executedTrades) {
            sendTradeMessage(trade);

            // Record the executed trade
            recordExecuted(trade);
        }
    }

    // Method to add or update an order book for an instrument
    public void addOrUpdateOrderBook(String instrument) {
        synchronized (orderBooks) {
            if (!orderBooks.containsKey(instrument)) {
                OrderBook orderBook = new OrderBook(instrument);
                orderBooks.put(instrument, orderBook);
            }
        }
    }

    // Method to place a new buy or sell order
    public synchronized void placeOrder(String instrument, String orderType, int quantity, double price) {
        // Generate a unique ID for the order using the Order class's getNextOrderId method
       int orderID = Order.getNextOrderId();

        // Create a new order with the specified instrument, order ID, quantity, price, and order type
        Order order = new Order(orderID, instrument, quantity, price, orderType);

        // Receive and process the order
        receiveOrder(order);
    }


    // Method to retrieve the current order book for a specific instrument
    public synchronized OrderBook getOrderBook(String instrument) {
        if (orderBooks.containsKey(instrument)) {
            return orderBooks.get(instrument);
        } else {
            // Handle the case where the instrument is not supported
            System.out.println("Instrument not supported: " + instrument);
            return null;
        }
    }

    // Method to retrieve executed trades for a specific instrument
    public synchronized List<Trade> getExecutedTrades(String instrument) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.getExecutedTrades();
        } else {
            // Handle the case where the instrument is not supported
            System.out.println("Instrument not supported: " + instrument);
            return new ArrayList<>(); // Return an empty list in case of unsupported instrument
        }
    }

    // Synchronized method to cancel an order by orderId (int version)
    public synchronized boolean cancelOrder(String instrument, int orderId) {
        OrderBook orderBook = orderBooks.get(instrument);
        if (orderBook != null) {
            return orderBook.cancelOrder(orderId);
        } else {
            // Handle the case where the instrument is not supported
            System.out.println("Instrument not supported: " + instrument);
            return false; // Return false to indicate the cancellation failed
        }
    }

    // Synchronized method to cancel an order by orderId (long version)
    public synchronized boolean cancelOrder(String instrument, long orderId) {
        int intOrderId = convertToValidOrderId(orderId);
        return cancelOrder(instrument, intOrderId);
    }

    // Helper method to convert long to int for order cancellation
    private int convertToValidOrderId(long orderId) {
        // Perform the necessary conversion logic here
        // For example, you can take the lower 32 bits of the long value
        return (int) orderId;
    }

    // Method to record an executed trade
    public synchronized void recordExecuted(Trade trade) {
        // Implement the logic to record the executed trade here.
        // This could involve storing it in a data structure or sending it to an external system.
        // For example, you can add the trade to a list of executed trades.
        executedTrades.add(trade);
    }

    // Method to retrieve the list of executed trades
    public synchronized List<Trade> getExecutedTrades() {
        return executedTrades;
    }

    // Method to process all orders in the order books
    public synchronized void processOrders() {
        for (OrderBook orderBook : orderBooks.values()) {
            orderBook.matchOrders();
        }
    }

    // Shutdown the order processor
    public synchronized void shutdown() {
        // Shutdown the thread pool when the processor is no longer needed
        executor.shutdown();
    }
}
