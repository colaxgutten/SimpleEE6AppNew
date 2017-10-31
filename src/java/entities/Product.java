/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Daniel
 */
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String productName;
    private String feedBack; 
    private int startingBid;

    
  //  private Bids currentBid;
    private String contactInformation;
    private double sellersRating;
    private boolean published;

    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="productCatalog_ID")
    private ProductCatalog productCatalog;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="auctionUser_ID")
    private AuctionUser auctionUser;
    
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
    
    public int getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(int startingBid) {
        this.startingBid = startingBid;
    }
    public AuctionUser getAuctionUser() {
        return auctionUser;
    }

    public void setAuctionUser(AuctionUser auctionUser) {
        this.auctionUser = auctionUser;
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }
    
    public Product(String productName){
        this.productName=productName;
    }
    //A test product is made here for testing purposes
    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

//    public Bids getCurrentBid() {
//        return currentBid;
//    }
//
//    public void setCurrentBid(Bids currentBid) {
//        this.currentBid = currentBid;
//    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public double getSellersRating() {
        return sellersRating;
    }

    public void setSellersRating(int sellersRating) {
        this.sellersRating = sellersRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProductAsTableRow(){
        String s = "<tr><td>"+productName+"</td><td>"+feedBack+"</td><td>"+contactInformation+"</td><td>"+sellersRating+"</td></tr>";
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id: " + this.id;
    }
    
}
