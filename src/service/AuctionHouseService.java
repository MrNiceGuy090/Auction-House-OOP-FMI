package service;
import model.*;

import java.util.ArrayList;
import java.util.Comparator;

public class AuctionHouseService {
    private ArrayList<Auction> activeAuctions = new ArrayList<Auction>();
    private ArrayList<Auction> endedAuctions = new ArrayList<Auction>();
    private ArrayList<User> users = new ArrayList<User>();

    public ArrayList<Auction> getAuctions(String filter, String sortBy){
        ArrayList<Auction> auctions = new ArrayList<>(activeAuctions);

        if (filter != null) {
                if (filter.equals("car"))
                    auctions.removeIf(auction -> !(auction.getProduct() instanceof Car));
                if (filter.equals("motorcycle"))
                    auctions.removeIf(auction -> !(auction.getProduct() instanceof Motorcycle));
                if (filter.equals("truck"))
                    auctions.removeIf(auction -> !(auction.getProduct() instanceof Truck));
                if (filter.equals("bicycle"))
                    auctions.removeIf(auction -> !(auction.getProduct() instanceof Bicycle));
                if (filter.equals("has engine"))
                    auctions.removeIf(auction -> auction.getProduct() instanceof Bicycle);

        }
        if (sortBy != null) {
            if(sortBy.equals("price")) {
                auctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
            }
            else if(sortBy.equals("year")){
                    auctions.sort(Comparator.comparingInt(o -> o.getProduct().getBuiltDate()));
            }
        }
        return auctions;
    }

    public boolean placeBid(Auction auction, User User, int price){
        if(auction.getHighestBid().getPrice() < price ){
            // remove auction from current highestBid holder
            ArrayList<Auction> oldBidderAuctions = auction.getHighestBid().getBidder().getActiveAuctions();
            oldBidderAuctions.remove(auction);
            auction.getHighestBid().getBidder().setActiveAuctions(oldBidderAuctions);

            // place bid
            auction.setHighestBid( new Bid(User, price)  );
            // update User's active auctions
            ArrayList<Auction> UserActiveAuctions = User.getActiveAuctions();
            if(!UserActiveAuctions.contains(auction)) {
                UserActiveAuctions.add(auction);
                User.setActiveAuctions(UserActiveAuctions);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<ArrayList<Object>> getAuctionWinners(){
        ArrayList<Object> pair = new ArrayList<Object>();
        ArrayList<ArrayList<Object>> pairList = new ArrayList<ArrayList<Object>>();

        for (Auction auction : endedAuctions){
            pair = new ArrayList<Object>();
            pair.add(auction);
            pair.add(auction.getHighestBid().getBidder());

            pairList.add(pair);
        }
        return pairList;
    }

    public void startAuction(Product product, User owner, double startingPrice){

        Auction auction = new Auction( product, owner, startingPrice);
        activeAuctions.add(0, auction);

        if(!owner.getAuctionedItems().contains(product)){
            ArrayList<Product> ownerProducts = owner.getAuctionedItems();
            ownerProducts.add(product);
            owner.setAuctionedItems( ownerProducts );

            ArrayList<Auction> ownerAuctions = owner.getActiveSellAuctions();
            ownerAuctions.add(auction);
            owner.setActiveAuctions(ownerAuctions);
        }
        else{
            System.out.println("Product is already in auction");
        }
    }

    public Bid getHighestBid(Auction auction){
        return auction.getHighestBid();
    }

    public void closeAuction(Auction auction, User owner){
        if (owner.equals(auction.getOwner()) ){
            // update activeAuctions and enededAuctions lists
            this.activeAuctions.remove(auction);
            this.endedAuctions.add(auction);

            // update owner's active and sold products
            ArrayList<Product> ownerAuctionItems = owner.getAuctionedItems();
            ownerAuctionItems.remove(auction.getProduct());
            owner.setAuctionedItems(ownerAuctionItems);

            ArrayList<Product> ownerSoldItems = owner.getSoldItems();
            ownerSoldItems.add(auction.getProduct());
            owner.setSoldItems(ownerSoldItems);

            // update owner's active and ended auctions
            ArrayList<Auction> ownerActiveAuctions = owner.getActiveSellAuctions();
            ownerActiveAuctions.remove(auction);
            owner.setActiveSellAuctions(ownerActiveAuctions);

            ArrayList<Auction> ownerEndedAuctions = owner.getEndedSellAuctions();
            ownerEndedAuctions.add(auction);
            owner.setEndedSellAuctions(ownerEndedAuctions);


            // update Users active and won auctions
            ArrayList<Auction> userActiveActions = auction.getHighestBid().getBidder().getActiveAuctions();
            userActiveActions.remove(auction);
            auction.getHighestBid().getBidder().setActiveAuctions(userActiveActions);

            ArrayList<Auction> userWonAuctions = auction.getHighestBid().getBidder().getAuctionsWon();
            userWonAuctions.add(auction);
            userWonAuctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
            auction.getHighestBid().getBidder().setAuctionsWon(userWonAuctions);
        }
    }

    public void banUser(User user){
        user.setBannend(true);
    }

    public ArrayList<ArrayList<Object>> getMostExpensiveProductsSold(){
        ArrayList<Auction> sortedAuctions = endedAuctions;
        sortedAuctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()) );
        ArrayList<ArrayList<Object>> pairList = new ArrayList<ArrayList<Object>>();

        for (Auction auction : sortedAuctions){
            ArrayList<Object> pair = new ArrayList<>();
            pair.add(auction.getProduct());
            pair.add(auction.getHighestBid().getPrice());
            pairList.add(pair);
        }
        return pairList;
    }

    public ArrayList<Auction> getUserAuctionsByBid(User user){
        ArrayList<Auction> auctions = user.getActiveAuctions();
        auctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
        return auctions;
    }

    public boolean addUser(String name, String password){
        for(User u: users){
            if(u.getName().equals(name)) {
                return false;
            }
        }
        User user = new User(name, password);
        users.add(user);
        users.sort(Comparator.comparing(User::getName));
        return true;
    }

    public User logInUser(String name, String password){
        for(User u: users){
            if(u.getName().equals(name) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

}
