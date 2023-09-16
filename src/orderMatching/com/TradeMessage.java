package orderMatching.com;

import java.time.LocalDateTime;

public class TradeMessage {
	 private int tradeID;
	    private String instrument;
	    private int quantity;
	    private double price;
	    private LocalDateTime timestamp;
		public TradeMessage(int tradeID, String instrument, int quantity, double price, LocalDateTime timestamp) {
			super();
			this.tradeID = tradeID;
			this.instrument = instrument;
			this.quantity = quantity;
			this.price = price;
			this.timestamp = timestamp;
		}
		public int getTradeID() {
			return tradeID;
		}
		public void setTradeID(int tradeID) {
			this.tradeID = tradeID;
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
		public LocalDateTime getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

}
