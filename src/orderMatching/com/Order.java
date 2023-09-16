package orderMatching.com;

import java.util.Objects;

public class Order {
    private static int nextOrderId = 1; // Static counter to generate unique order IDs
    private int ID; // Unique order ID
    private String instrument;
    private int quantity;
    private double price;
    private String orderType;

    public Order(int ID, String instrument, int quantity, double price, String orderType) {
        this.ID = ID;
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    public int getID() {
        return ID;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    // Static method to get the next available order ID
    @SuppressWarnings("unused")
	static int getNextOrderId() {
        return nextOrderId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return ID == order.ID &&
                quantity == order.quantity &&
                Double.compare(order.price, price) == 0 &&
                Objects.equals(instrument, order.instrument) &&
                Objects.equals(orderType, order.orderType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, instrument, quantity, price, orderType);
    }

    @Override
    public String toString() {
        return "Order [ID: " + ID + ", Instrument: " + instrument + ", Quantity: " + quantity +
               ", Price: " + price + ", OrderType: " + orderType + "]";
    }
}
