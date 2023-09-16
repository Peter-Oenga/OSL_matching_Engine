package orderMatching.com;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//import java.util.ArrayList;
import java.util.List;

//import java.util.List;

import org.junit.After;

public class OrderProcessorTest {
    private OrderProcessor orderProcessor;

    @Before
    public void setUp() {
        // Initialize the orderProcessor or any shared state needed for tests
        orderProcessor = new OrderProcessor();
       orderProcessor.addOrUpdateOrderBook("BTC");
    }

    @After
    public void tearDown() {
        // Clean up any resources or reset state after each test
        orderProcessor = null;
    }

   
    
    @Test
    public void testPlaceBuyOrder() {
        // Place a valid buy order for BTC
        String instrument = "BTC";
        String orderType = "buy";
        int quantity = 10;
        double price = 45000.0;

        orderProcessor.placeOrder(instrument, orderType, quantity, price);

        // Sleep briefly to allow order processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the order was placed correctly for BTC
        OrderBook btcOrderBook = orderProcessor.getOrderBook(instrument);
        assertNotNull(btcOrderBook);

        // Check the number of buy orders for BTC
        List<Order> buyOrders = btcOrderBook.getBuyOrders();
        assertEquals(1, buyOrders.size());

        // Check the quantity of the buy order
        Order placedOrder = buyOrders.get(0);
        assertEquals(quantity, placedOrder.getQuantity());

        // Check the price of the buy order
        assertEquals(price, placedOrder.getPrice(), 0.01);
    }

    @Test
    public void testPlaceSellOrder() {
        // Place a valid sell order for ETH
        String instrument = "ETH";
        String orderType = "sell";
        int quantity = 5;
        double price = 500.0;

        orderProcessor.placeOrder(instrument, orderType, quantity, price);

        // Sleep briefly to allow order processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the order was placed correctly for ETH
        OrderBook ethOrderBook = orderProcessor.getOrderBook(instrument);
        assertNotNull(ethOrderBook);

        // Check the number of sell orders for ETH
        List<Order> sellOrders = ethOrderBook.getSellOrders();
        assertEquals(1, sellOrders.size());

        // Check the quantity of the sell order
        Order placedOrder = sellOrders.get(0);
        assertEquals(quantity, placedOrder.getQuantity());

        // Check the price of the sell order
        assertEquals(price, placedOrder.getPrice(), 0.01);
    }



        @Test
        public void testPlaceInvalidOrder() {
            // Try to place an invalid order for an unsupported instrument
            orderProcessor.placeOrder("XYZ", "buy", 5, 100.0);

            // Verify that an unsupported instrument results in a null order book
            OrderBook invalidOrderBook = orderProcessor.getOrderBook("XYZ");
            assertNull(invalidOrderBook);
        }




    @SuppressWarnings("unused")
	private int convertToValidOrderId(long orderId) {
        // Perform the necessary conversion logic here
        // For example, you can take the lower 32 bits of the long value
        return (int) orderId;
    }
        @Test
        public void testCancelValidOrder() {
            // Place a valid buy order for BTC
            String instrument = "BTC";
            String orderType = "buy";
            int quantity = 10;
            double price = 45000.0;

            // Place the order and obtain the order ID
            long orderId = placeBuyOrder(instrument, orderType, quantity, price);

            // Retrieve the order book for BTC
            OrderBook btcOrderBook = orderProcessor.getOrderBook(instrument);

            // Print the order book before cancellation
            System.out.println("Order book before cancellation:");
            btcOrderBook.printOrderBook();

            // Attempt to cancel the order
            boolean canceled = orderProcessor.cancelOrder(instrument, orderId);

            // Print whether the order was canceled or not
            System.out.println("Order canceled: " + canceled);

            // Verify that the order was canceled successfully
            assertTrue("Order should be canceled successfully.", canceled);

            // Attempting to cancel the same order again should fail
            boolean canceledAgain = orderProcessor.cancelOrder(instrument, orderId);
            System.out.println("Order canceled again: " + canceledAgain);

            // Verify that the order book is empty for BTC
            assertNotNull(btcOrderBook);
            assertEquals(0, btcOrderBook.getBuyOrders().size());
            assertEquals(0, btcOrderBook.getSellOrders().size());
        }

        // Helper method to place a buy order and return the order ID
        private long placeBuyOrder(String instrument, String orderType, int quantity, double price) {
            orderProcessor.placeOrder(instrument, orderType, quantity, price);

            // Obtain the order from the order book (assuming it's the latest order)
            List<Order> buyOrders = orderProcessor.getOrderBook(instrument).getBuyOrders();
            Order latestOrder = buyOrders.get(buyOrders.size() - 1);

            return latestOrder.getID();
        }


    @Test
    public void testCancelNonExistentOrder() {
        // Attempt to cancel a non-existent order
        String instrument = "BTC";

        // Use a non-existent orderId (replace with an actual non-existent ID)
        long nonExistentOrderId = 999;

        // Attempting to cancel a non-existent order should fail
        boolean canceled = orderProcessor.cancelOrder(instrument, nonExistentOrderId);
        assertFalse(canceled);
    }
   


        @Test
        public void testInvalidInstrument() {
            // Test placing orders with an invalid instrument
            // Attempt to place an order for an unsupported instrument
            orderProcessor.placeOrder("InvalidInstrument", "buy", 10, 100.0);

            // Ensure there are no orders for the invalid instrument
            OrderBook orderBook = orderProcessor.getOrderBook("InvalidInstrument");
            assertNull(orderBook);
        }

        @Test
        public void testOrderBookRetrieval() {
            // Test retrieving the order book for specific instruments
            orderProcessor.addOrUpdateOrderBook("BTC");
            orderProcessor.addOrUpdateOrderBook("ETH");
            orderProcessor.addOrUpdateOrderBook("USDT");

            OrderBook msftOrderBook = orderProcessor.getOrderBook("BTC");
            OrderBook tslaOrderBook = orderProcessor.getOrderBook("ETH");
           // OrderBook usdtOrderBook = orderProcessor.getOrderBook("USDT");

            assertNotNull(msftOrderBook);
            assertNotNull(tslaOrderBook);
        }

        @Test
        public void testConcurrentOrderProcessing() {
            // Test concurrent order processing for BTC and ETH
            orderProcessor.addOrUpdateOrderBook("BTC");
            orderProcessor.addOrUpdateOrderBook("ETH");

            // Place multiple orders concurrently for BTC and ETH
            Runnable placeBuyOrdersBTC = () -> {
                for (int i = 0; i < 10; i++) {
                    orderProcessor.placeOrder("BTC", "buy", 1, 25000.0);
                }
            };

            Runnable placeSellOrdersBTC = () -> {
                for (int i = 0; i < 10; i++) {
                    orderProcessor.placeOrder("BTC", "sell", 1, 4760.0);
                }
            };

            Runnable placeBuyOrdersETH = () -> {
                for (int i = 0; i < 10; i++) {
                    orderProcessor.placeOrder("ETH", "buy", 1, 2000.0);
                }
            };

            Runnable placeSellOrdersETH = () -> {
                for (int i = 0; i < 10; i++) {
                    orderProcessor.placeOrder("ETH", "sell", 1, 2100.0);
                }
            };

            Thread thread1 = new Thread(placeBuyOrdersBTC);
            Thread thread2 = new Thread(placeSellOrdersBTC);
            Thread thread3 = new Thread(placeBuyOrdersETH);
            Thread thread4 = new Thread(placeSellOrdersETH);

            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            // Wait for all threads to finish
            try {
                thread1.join();
                thread2.join();
                thread3.join();
                thread4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Verify that orders are processed concurrently without errors for BTC and ETH
            OrderBook btcOrderBook = orderProcessor.getOrderBook("BTC");
            OrderBook ethOrderBook = orderProcessor.getOrderBook("ETH");

            assertNotNull(btcOrderBook);
            assertNotNull(ethOrderBook);
        }
    }
