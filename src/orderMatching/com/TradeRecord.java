package orderMatching.com;

	import java.util.Date;

	public class TradeRecord {
	    private String instrument;
	    private double price;
	    private int quantity;
	    private Date timestamp;

	    // Constructor
	    public TradeRecord(String instrument, double price, int quantity) {
	        this.instrument = instrument;
	        this.price = price;
	        this.quantity = quantity;
	        this.timestamp = new Date(); // Timestamp when the trade occurred
	    }

		public String getInstrument() {
			return instrument;
		}

		public void setInstrument(String instrument) {
			this.instrument = instrument;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public Date getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
		}

	 
	}


