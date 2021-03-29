package model;

import java.util.ArrayList;
import java.util.UUID;

public class User{
    private String id;
    private String name;
    private String password;
    private boolean isBannend;
    private ArrayList<Product> auctionedItems;
    private ArrayList<Product> soldItems;
    private ArrayList<Auction> activeAuctions;
    private ArrayList<Auction> auctionsWon; // sorted
    private ArrayList<Auction> activeSellAuctions;
    private ArrayList<Auction> endedSellAuctions;

    public User(String name, String password){
        this.id = UUID.randomUUID().toString();;
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

    public ArrayList<Product> getAuctionedItems() {
        return auctionedItems;
    }

    public void setAuctionedItems(ArrayList<Product> auctionedItems) {
        this.auctionedItems = auctionedItems;
    }

    public ArrayList<Product> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(ArrayList<Product> soldItems) {
        this.soldItems = soldItems;
    }

    public ArrayList<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public void setActiveAuctions(ArrayList<Auction> activeAuctions) {
        this.activeAuctions = activeAuctions;
    }

    public ArrayList<Auction> getAuctionsWon() {
        return auctionsWon;
    }

    public void setAuctionsWon(ArrayList<Auction> auctionsWon) {
        this.auctionsWon = auctionsWon;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Auction> getActiveSellAuctions() {
        return activeSellAuctions;
    }

    public void setActiveSellAuctions(ArrayList<Auction> activeSellAuctions) {
        this.activeSellAuctions = activeSellAuctions;
    }

    public ArrayList<Auction> getEndedSellAuctions() {
        return endedSellAuctions;
    }

    public void setEndedSellAuctions(ArrayList<Auction> endedSellAuctions) {
        this.endedSellAuctions = endedSellAuctions;
    }
}
