package service;

import model.Auction;
import model.Bid;
import model.User;

import java.io.Writer;
import java.lang.invoke.WrongMethodTypeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BidService {
    private static BidService instance = null;
    List<Bid> bids;
    private WriterService writerService = WriterService.getInstance();

    private BidService(){
        bids = new ArrayList<Bid>();
    }

    public static BidService getInstance()
    {
        if (instance == null)
            instance = new BidService();

        return instance;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public boolean placeBid(Auction auction, Bid bid){
        writerService.writeToFile("placeBid", new Date(System.currentTimeMillis()));
        if(auction.getHighestBid().getPrice() < bid.getPrice() ){
            // remove auction from current highestBid holder
            List<Auction> oldBidderAuctions = new ArrayList<>(auction.getHighestBid().getBidder().getActiveAuctions());
            oldBidderAuctions.remove(auction);
            auction.getHighestBid().getBidder().setActiveAuctions(oldBidderAuctions);

            // place bid
            auction.setHighestBid( bid );
            // update User's active auctions
            List<Auction> UserActiveAuctions = new ArrayList<>(bid.getBidder().getActiveAuctions());
            if(!UserActiveAuctions.contains(auction)) {
                UserActiveAuctions.add(auction);
                bid.getBidder().setActiveAuctions(UserActiveAuctions);
            }
            return true;
        }
        else{
            return false;
        }
    }

    public boolean placeBid(Auction auction, User User, int price){
        writerService.writeToFile("placeBid", new Date(System.currentTimeMillis()));
        if(auction.getHighestBid().getPrice() < price ){
            // remove auction from current highestBid holder
            List<Auction> oldBidderAuctions = new ArrayList<>(auction.getHighestBid().getBidder().getActiveAuctions());
            oldBidderAuctions.remove(auction);
            auction.getHighestBid().getBidder().setActiveAuctions(oldBidderAuctions);

            // place bid
            auction.setHighestBid( new Bid(User, price)  );
            // update User's active auctions
            List<Auction> UserActiveAuctions = new ArrayList<>(User.getActiveAuctions());
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

    public void readBids(){
        ReaderService reader = ReaderService.getInstance();
        String text = reader.readFile("src\\files\\Bids.csv");
        String[] lines = text.split("\n");
        boolean skip = true;
        for(String line : lines){
            if( skip){
                skip = false;
                continue;
            }
            String[] data = line.split(",");
            User user = UserService.getInstance().getUsers().stream().filter(usr -> usr.getId().equals(data[1])).collect(Collectors.toList()).get(0);
            Bid bid = new Bid(data[0], user, Integer.parseInt(data[2]));
            bids.add(bid);
        }
    }

}
