/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.presentation;

import boundary.BidsFacade;
import entities.Bids;
import entities.Product;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "BidsView")
@RequestScoped
public class BidsView {

    @EJB
    private BidsFacade bidsFacade;
    private Bids bids;
    
    @ManagedProperty(value="#{AuctionUserView}")
    private AuctionUserView auctionUserView;
    
    @ManagedProperty(value="#{ProductView}")
    private ProductView productView;

    public ProductView getProductView() {
        return productView;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    public BidsFacade getBidsFacade() {
        return bidsFacade;
    }

    public void setBidsFacade(BidsFacade bidsFacade) {
        this.bidsFacade = bidsFacade;
    }

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }
    
    public void publish(ActionEvent event){
        this.bidsFacade.publish(auctionUserView);
    }
    
    public String getTimeLeft(Bids bid){
        if (bid==null)
            return "";
        int now = (int)System.currentTimeMillis()/1000;
        int timeLeft = bid.getStartTime()+bid.getBidDuration()-now;
        return toTimeString(timeLeft);
    }

    private String toTimeString(int time){
        String s ="";
        if (time>=3600){
            s+=(time/3600)+" hours, ";
        }
        if (time>=60){
            int minutes = (time%3600)/60;
            s+=minutes +" minutes and ";
        }
        if (time>=0)
        s+=(time%60)+" seconds";
        else
            s+="Ended";
        return s;
    }
    
    public AuctionUserView getAuctionUserView() {
        return auctionUserView;
    }

    public void setAuctionUserView(AuctionUserView auctionUserView) {
        this.auctionUserView = auctionUserView;
    }
    
    public List<Bids> getMostRecentBids(){
        List<Bids> recentBids = bidsFacade.findRecent();
        if (recentBids.size()>10)
            return recentBids.subList(0, 10);
        return recentBids;
    }
    
    public void bid(Bids bid){
        bidsFacade.placeBid(bid,auctionUserView);
    }
    
    public void bidProduct(Product product){
        bid(bidsFacade.getBidFromProduct(product));
    }
    
    public List<Bids> getSearchForBidsOnProductNames(){
        return bidsFacade.findBidsWithProductName(productView.getProduct().getProductName());
    }
    
    private String asTable(List<Bids> list){
        if (list==null)
            return "null";
        String s ="<table><tr><td>Current Bid</td><td>Time left</td></tr>";
        for (Bids b : list){
            s+="<tr><td>"+b.getBid()+"</td><td>"+b.getBidDuration()+"</td></tr>";
        }
        s+="</table>";
        return s;
    }
    /**
     * Creates a new instance of BidsView
     */
    public BidsView() {
        bids = new Bids();
    }
    
}
