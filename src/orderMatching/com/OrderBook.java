package orderMatching.com;
import java.util.*;
import java.util.stream.Collectors;

public class OrderBook {
    private String instrument;
    private Map<Double, List<Order>> buyOrders;
    private Map<Double, List<Order>> sellOrders;
    private List<Trade> executedTrades = new ArrayList<>();

    public OrderBook(String instrument) {
        this.setInstrument(instrument);
        this.buyOrders = new TreeMap<>(Collections.reverseOrder());
        this.sellOrders = new TreeMap<>();
    }

    public List<Trade> getExecutedTrades() {
        return executedTrades;
    }

    public synchronized void addOrder(Order order) {
        if (order.getOrderType().equals("buy")) {
            double price = order.getPrice();
            List<Order> ordersAtPrice = buyOrders.getOrDefault(price, new ArrayList<>());
            ordersAtPrice.add(order);
            buyOrders.put(price, ordersAtPrice);

            // Add debugging statements for buy orders
            System.out.println("Buy order added for price: " + price);
           
            System.out.println("Buy Orders Size: " + ordersAtPrice.size());
        } else if (order.getOrderType().equals("sell")) {
            double price = order.getPrice();
            List<Order> ordersAtPrice = sellOrders.getOrDefault(price, new ArrayList<>());
            ordersAtPrice.add(order);
            sellOrders.put(price, ordersAtPrice);

            // Add debugging statements for sell orders
            System.out.println("Sell order added for price: " + price);
           
            System.out.println("Sell Orders Size: " + ordersAtPrice.size());
        } else {
            System.out.println("Unsupported order type: " + order.getOrderType());
        }
    }

    public synchronized List<Trade> matchOrders() {
        List<Trade> executedTrades = new ArrayList<>();

        for (Map.Entry<Double, List<Order>> buyEntry : buyOrders.entrySet()) {
            double buyPrice = buyEntry.getKey();
            List<Order> buyOrdersAtPrice = buyEntry.getValue();

            if (sellOrders.containsKey(buyPrice)) {
                List<Order> sellOrdersAtPrice = sellOrders.get(buyPrice);
                List<Trade> trades = matchOrdersAtPrice(buyOrdersAtPrice, sellOrdersAtPrice);
                executedTrades.addAll(trades);
            }
        }

        return executedTrades;
    }

    private List<Trade> matchOrdersAtPrice(List<Order> buyOrdersAtPrice, List<Order> sellOrdersAtPrice) {
        List<Trade> executedTrades = new ArrayList<>();
        Iterator<Order> buyIterator = buyOrdersAtPrice.iterator();
        Iterator<Order> sellIterator = sellOrdersAtPrice.iterator();

        while (buyIterator.hasNext() && sellIterator.hasNext()) {
            Order buyOrder = buyIterator.next();
            Order sellOrder = sellIterator.next();

            int buyQuantity = buyOrder.getQuantity();
            int sellQuantity = sellOrder.getQuantity();

            if (buyQuantity > 0 && sellQuantity > 0) {
                int tradeQuantity = Math.min(buyQuantity, sellQuantity);

                // Create a new Trade object with the required parameters
                Trade trade = new Trade(
                    buyOrder.getInstrument(),  // Instrument from buy order
                    tradeQuantity,             // Trade quantity
                    buyOrder.getPrice(),       // Price from buy order
                    System.currentTimeMillis(), // Timestamp
                    buyOrder.getOrderType(),   // Trade type from buy order
                    buyOrder,                  // Reference to buy order
                    sellOrder                  // Reference to sell order
                );

                // Add the trade to the list of executed trades
                executedTrades.add(trade);

                // Update the quantities of buy and sell orders
                buyOrder.setQuantity(buyQuantity - tradeQuantity);
                sellOrder.setQuantity(sellQuantity - tradeQuantity);

                // Remove orders with quantity 0
                if (buyOrder.getQuantity() == 0) {
                    buyIterator.remove();
                }
                if (sellOrder.getQuantity() == 0) {
                    sellIterator.remove();
                }
            }
        }

        return executedTrades;
    }



    public synchronized boolean cancelOrder(int orderId) {
        for (Map.Entry<Double, List<Order>> entry : buyOrders.entrySet()) {
            List<Order> ordersAtPrice = entry.getValue();
            for (Iterator<Order> iterator = ordersAtPrice.iterator(); iterator.hasNext();) {
                Order order = iterator.next();
                if (order.getID() == orderId) {
                    iterator.remove(); // Remove the canceled order
                    return true; // Return true to indicate successful cancellation
                }
            }
        }

        for (Map.Entry<Double, List<Order>> entry : sellOrders.entrySet()) {
            List<Order> ordersAtPrice = entry.getValue();
            for (Iterator<Order> iterator = ordersAtPrice.iterator(); iterator.hasNext();) {
                Order order = iterator.next();
                if (order.getID() == orderId) {
                    iterator.remove(); // Remove the canceled order
                    return true; // Return true to indicate successful cancellation
                }
            }
        }

        return false; // Return false to indicate that the order was not found and thus not canceled
    }

    
    public void printOrderBook() {
        System.out.println("Instrument: " + getInstrument()); // Add instrument info
        
        for (Map.Entry<Double, List<Order>> entry : buyOrders.entrySet()) {
            double price = entry.getKey();
            List<Order> orders = entry.getValue();
            System.out.println("   Price: " + price);
            for (Order order : orders) {
                System.out.println("      " + order.getOrderType() + " - Quantity: " + order.getQuantity());
            }
        }

        System.out.println("Sell Orders:");
        for (Map.Entry<Double, List<Order>> entry : sellOrders.entrySet()) {
            double price = entry.getKey();
            List<Order> orders = entry.getValue();
            System.out.println("   Price: " + price);
            for (Order order : orders) {
                System.out.println("      " + order.getOrderType() + " - Quantity: " + order.getQuantity());
            }
        }
    }

    public List<Order> getBuyOrders() {
        List<Order> buyOrdersList = buyOrders.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

       
        System.out.println("Buy Orders Size: " + buyOrdersList.size());

        for (Order order : buyOrdersList) {
            System.out.println(order);
        }

        return buyOrdersList;
    }

    public List<Order> getSellOrders() {
        return sellOrders.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
