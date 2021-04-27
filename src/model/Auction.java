package model;
import java.util.UUID;

public class Auction {
    private String id;
    private Product product;
    private Bid highestBid;
    private User owner;

    public Auction(Product product, User owner, double startingPrice){
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.highestBid = new Bid(owner, startingPrice);
        this.owner = owner;
    }

    public Auction(String id, Product product, User owner, double startingPrice){
        this.id = id;
        this.product = product;
        this.highestBid = new Bid(owner, startingPrice);
        this.owner = owner;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
