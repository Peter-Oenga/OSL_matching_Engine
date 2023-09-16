package orderMatching.com;

public class Trade {
	private int ID;
    public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}

	private String instrument;
    private int quantity;
    private double price;
    private long timestamp; // Use a timestamp to record when the trade occurred
    private String type;    // New field to store the trade type ("buy" or "sell")
    private Order buyOrder; // Reference to the buy order
    private Order sellOrder; // Reference to the sell order

    public Trade(String instrument, int quantity, double price, long timestamp, String type, Order buyOrder, Order sellOrder) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.type = type;
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
    }


    public String getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(Order buyOrder) {
        this.buyOrder = buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(Order sellOrder) {
        this.sellOrder = sellOrder;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "instrument='" + instrument + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", buyOrder=" + (buyOrder != null ? buyOrder.getID() : "null") +
                ", sellOrder=" + (sellOrder != null ? sellOrder.getID() : "null") +
                '}';
    }
}
