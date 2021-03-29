package model;

import java.util.UUID;

public class Bid implements Comparable<Bid>{
    private User bidder;
    private double price;
    private String id;

    public Bid(User bidder, double price){
        this.id = UUID.randomUUID().toString();
        this.bidder = bidder;
        this.price = price;
    }

    @Override
    public int compareTo(Bid otherBid) {
        return Double.compare(getPrice(), otherBid.getPrice());
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
