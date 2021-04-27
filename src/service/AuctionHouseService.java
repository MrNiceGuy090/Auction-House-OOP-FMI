package service;
import model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionHouseService {
    private static AuctionHouseService instance = null;

    private ArrayList<Auction> activeAuctions = new ArrayList<Auction>();
    private ArrayList<Auction> endedAuctions = new ArrayList<Auction>();
    private WriterService writerService = WriterService.getInstance();

    private AuctionHouseService() {
        activeAuctions = new ArrayList<Auction>();
        endedAuctions = new ArrayList<Auction>();
    }

    public static AuctionHouseService getInstance()
    {
        if (instance == null)
            instance = new AuctionHouseService();

        return instance;
    }


    public List<Auction> getAuctions(String filter, String sortBy){
        List<Auction> auctions = new ArrayList<>(activeAuctions);

        if ("car".equals(filter))
            auctions.removeIf(auction -> !(auction.getProduct() instanceof Car));
        if ("motorycle".equals(filter))
            auctions.removeIf(auction -> !(auction.getProduct() instanceof Motorcycle));
        if ("truck".equals(filter))
            auctions.removeIf(auction -> !(auction.getProduct() instanceof Truck));
        if ("bicycle".equals(filter))
            auctions.removeIf(auction -> !(auction.getProduct() instanceof Bicycle));
        if ("has engine".equals(filter))
            auctions.removeIf(auction -> auction.getProduct() instanceof Bicycle);


        if (sortBy != null) {
            if(sortBy.equals("price")) {
                auctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
            }
            else if(sortBy.equals("year")){
                    auctions.sort(Comparator.comparingInt(o -> o.getProduct().getBuiltDate()));
            }
        }
        writerService.writeToFile("getAuctions", new Date(System.currentTimeMillis()));
        return auctions;
    }


    public List<List<Object>> getAuctionWinners(){
        List<Object> pair = new ArrayList<Object>();
        List<List<Object>> pairList = new ArrayList<List<Object>>();

        for (Auction auction : endedAuctions){
            pair = new ArrayList<Object>();
            pair.add(auction);
            pair.add(auction.getHighestBid().getBidder());

            pairList.add(pair);
        }
        writerService.writeToFile("getAuctionWinners", new Date(System.currentTimeMillis()));
        return pairList;
    }

    public Auction startAuction(Product product, User owner, double startingPrice){

        Auction auction = new Auction( product, owner, startingPrice);
        activeAuctions.add(0, auction);

        if(!owner.getAuctionedItems().contains(product)){
            List<Product> ownerProducts = new ArrayList<>(owner.getAuctionedItems());
            ownerProducts.add(product);
            owner.setAuctionedItems( ownerProducts );

            List<Auction> ownerAuctions = new ArrayList<>(owner.getActiveSellAuctions());
            ownerAuctions.add(auction);
            owner.setActiveSellAuctions(ownerAuctions);
        }
        else{
            System.out.println("Product is already in auction");
        }
        writerService.writeToFile("startAuction", new Date(System.currentTimeMillis()));
        return auction;
    }

    public Auction startAuction(String auctionId, Product product, User owner, double startingPrice){

        Auction auction = new Auction( auctionId, product, owner, startingPrice);
        activeAuctions.add(0, auction);

        if(!owner.getAuctionedItems().contains(product)){
            List<Product> ownerProducts = new ArrayList<>(owner.getAuctionedItems());
            ownerProducts.add(product);
            owner.setAuctionedItems( ownerProducts );

            List<Auction> ownerAuctions = new ArrayList<>(owner.getActiveSellAuctions());
            ownerAuctions.add(auction);
            owner.setActiveSellAuctions(ownerAuctions);
        }
        else{
            System.out.println("Product is already in auction");
        }
        writerService.writeToFile("startAuction", new Date(System.currentTimeMillis()));
        return auction;
    }

    public Bid getHighestBid(Auction auction){
        writerService.writeToFile("getHighestBid", new Date(System.currentTimeMillis()));
        return auction.getHighestBid();
    }

    public void closeAuction(Auction auction, User owner){
        if (owner.equals(auction.getOwner()) ){
            // update activeAuctions and enededAuctions lists
            this.activeAuctions.remove(auction);
            this.endedAuctions.add(auction);

            // update owner's active and sold products
            List<Product> ownerAuctionItems = new ArrayList<>(owner.getAuctionedItems());
            ownerAuctionItems.remove(auction.getProduct());
            owner.setAuctionedItems(ownerAuctionItems);

            List<Product> ownerSoldItems = new ArrayList<>(owner.getSoldItems());
            ownerSoldItems.add(auction.getProduct());
            owner.setSoldItems(ownerSoldItems);

            // update owner's active and ended auctions
            List<Auction> ownerActiveAuctions = new ArrayList<>(owner.getActiveSellAuctions());
            ownerActiveAuctions.remove(auction);
            owner.setActiveSellAuctions(ownerActiveAuctions);

            List<Auction> ownerEndedAuctions = new ArrayList<>(owner.getEndedSellAuctions());
            ownerEndedAuctions.add(auction);
            owner.setEndedSellAuctions(ownerEndedAuctions);


            // update Users active and won auctions
            List<Auction> userActiveActions = new ArrayList<>(auction.getHighestBid().getBidder().getActiveAuctions());
            userActiveActions.remove(auction);
            auction.getHighestBid().getBidder().setActiveAuctions(userActiveActions);

            List<Auction> userWonAuctions = new ArrayList<>(auction.getHighestBid().getBidder().getAuctionsWon());
            userWonAuctions.add(auction);
            userWonAuctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
            auction.getHighestBid().getBidder().setAuctionsWon(userWonAuctions);
        }
        writerService.writeToFile("closeAuction", new Date(System.currentTimeMillis()));
    }

    public void banUser(User user){
        user.setBannend(true);
        writerService.writeToFile("banUser", new Date(System.currentTimeMillis()));
    }

    public List<List<Object>> getMostExpensiveProductsSold(){
        List<Auction> sortedAuctions = new ArrayList<>(endedAuctions);
        sortedAuctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()) );
        List<List<Object>> pairList = new ArrayList<List<Object>>();

        for (Auction auction : sortedAuctions){
            List<Object> pair = new ArrayList<>();
            pair.add(auction.getProduct());
            pair.add(auction.getHighestBid().getPrice());
            pairList.add(pair);
        }
        writerService.writeToFile("getMostExpensiveProductsSold", new Date(System.currentTimeMillis()));
        return pairList;
    }

    public List<Auction> getUserActiveAuctions(User user){
        List<Auction> auctions = new ArrayList<>(user.getActiveAuctions());
        auctions.sort(Comparator.comparingDouble(o -> o.getHighestBid().getPrice()));
        writerService.writeToFile("getUsersActiveAuction", new Date(System.currentTimeMillis()));
        return auctions;
    }

    public List<Auction> getUserActiveSellAuctions(User user){
        List<Auction> auctions = new ArrayList<>(user.getActiveSellAuctions());
        writerService.writeToFile("getUsersActiveSellAuctions", new Date(System.currentTimeMillis()));
        return auctions;
    }

    public void readAuctions(List<Product> products, List<Bid> bids, List<User> owners){
        ReaderService reader = ReaderService.getInstance();
        String text = reader.readFile("src\\files\\Auctions.csv");
        String[] lines = text.split("\n");
        boolean skip = true;
        for(String line : lines){
            if( skip){
                skip = false;
                continue;
            }
            String[] data = line.split(",");
            Product prod = ProductService.getInstance().getProducts().stream().filter(product -> product.getId().equals(data[1])).collect(Collectors.toList()).get(0);
            Bid highestBid = BidService.getInstance().getBids().stream().filter(bid -> bid.getId().equals(data[2])).collect(Collectors.toList()).get(0);
            User owner = UserService.getInstance().getUsers().stream().filter(usr -> usr.getId().equals(data[3])).collect(Collectors.toList()).get(0);
            Auction auction = this.startAuction(data[0], prod, owner, Integer.parseInt(data[4]));
            BidService.getInstance().placeBid(auction, highestBid);
            this.closeAuction(auction, auction.getOwner());
        }
    }

}
