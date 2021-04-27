package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User{
    private String id;
    private String name;
    private String password;
    private boolean isBannend;
    private List<Product> auctionedItems;
    private List<Product> soldItems;
    private List<Auction> activeAuctions;
    private List<Auction> auctionsWon; // sorted
    private List<Auction> activeSellAuctions;
    private List<Auction> endedSellAuctions;

    public User(String name, String password){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.isBannend = false;
        this.auctionedItems =  new ArrayList<Product>();
        this.soldItems = new ArrayList<Product>();
        this.activeAuctions =  new ArrayList<Auction>();
        this.auctionsWon = new ArrayList<>();
        this.activeSellAuctions = new ArrayList<>();
        this.endedSellAuctions = new ArrayList<>();
    }
    public User(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
        this.isBannend = false;
        this.auctionedItems =  new ArrayList<Product>();
        this.soldItems = new ArrayList<Product>();
        this.activeAuctions =  new ArrayList<Auction>();
        this.auctionsWon = new ArrayList<>();
        this.activeSellAuctions = new ArrayList<>();
        this.endedSellAuctions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBannend() {
        return isBannend;
    }

    public void setBannend(boolean bannend) {
        isBannend = bannend;
    }

    public List<Product> getAuctionedItems() {
        return auctionedItems;
    }

    public void setAuctionedItems(List<Product> auctionedItems) {
        this.auctionedItems = auctionedItems;
    }

    public List<Product> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<Product> soldItems) {
        this.soldItems = soldItems;
    }

    public List<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public void setActiveAuctions(List<Auction> activeAuctions) {
        this.activeAuctions = activeAuctions;
    }

    public List<Auction> getAuctionsWon() {
        return auctionsWon;
    }

    public void setAuctionsWon(List<Auction> auctionsWon) {
        this.auctionsWon = auctionsWon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Auction> getActiveSellAuctions() {
        return activeSellAuctions;
    }

    public void setActiveSellAuctions(List<Auction> activeSellAuctions) {
        this.activeSellAuctions = activeSellAuctions;
    }

    public List<Auction> getEndedSellAuctions() {
        return endedSellAuctions;
    }

    public void setEndedSellAuctions(List<Auction> endedSellAuctions) {
        this.endedSellAuctions = endedSellAuctions;
    }
}
