package orderMatching.com;

	public class Order {
	    private String instrument;
	    private int quantity;
	    private double price;
	    private String orderType; // "buy" or "sell"

	    // Constructor
	    public Order(String instrument, int quantity, double price, String orderType) {
	        this.instrument = instrument;
	        this.quantity = quantity;
	        this.price = price;
	        this.orderType = orderType;
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

	}

   


