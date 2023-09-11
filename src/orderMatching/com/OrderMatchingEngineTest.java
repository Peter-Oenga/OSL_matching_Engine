package orderMatching.com;

public class OrderMatchingEngineTest {
    public static void main(String[] args) {
        // Create instances of OrderProcessor and OrderBook
        OrderProcessor orderProcessor = new OrderProcessor();
        OrderBook orderBook = new OrderBook("BTC/ETH");

        // Add the order book to the order processor using the instrument
        orderProcessor.addOrUpdateOrderBook("BTC/ETH");

        // Create sample buy and sell orders for testing
        Order buyOrder1 = new Order("BTC/ETH", 100, 10.0, "buy");
        Order sellOrder1 = new Order("BTC/ETH", 100, 10.0, "sell");
        Order sellOrder2 = new Order("BTC/ETH", 50, 11.0, "sell");

        // Send orders to the order processor
        orderProcessor.receiveOrder(buyOrder1);
        orderProcessor.receiveOrder(sellOrder1);
        orderProcessor.receiveOrder(sellOrder2);

        // Trigger the order matching process
        orderProcessor.processOrders();

        // Verify the results (e.g., check trade records and order book updates)
        // Implement your verification logic here...

        // Print the updated order book for inspection
        orderBook.printOrderBook();
    }
}
