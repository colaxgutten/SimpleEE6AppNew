/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Daniel
 */
@Entity
public class AuctionUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private String username;
    private String password;
    private String profilename;
    private double sellersRating;
    private int numberOfRatings;
    @OneToOne
    private ProductCatalog productCatalog;
    
    @OneToMany(mappedBy = "auctionUser")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getSellersRating() {
        return sellersRating;
    }

    public void setSellersRating(double sellersRating) {
        this.sellersRating = sellersRating;
    }
    
    @OneToMany(mappedBy="auctionUser")
    private List<Bids> bid;

    public List<Bids> getBid() {
        return bid;
    }

    public void setBid(List<Bids> bid) {
        this.bid = bid;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof AuctionUser)) {
            return false;
        }
        AuctionUser other = (AuctionUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }

    public void Copy(AuctionUser u) {
        this.bid=u.getBid();
        this.id=u.getId();
        this.numberOfRatings=u.getNumberOfRatings();
        this.password=u.getPassword();
        this.productCatalog=u.getProductCatalog();
        this.profilename=u.getProfilename();
        this.sellersRating=u.getSellersRating();
        this.username=u.getUsername();
        this.products=u.getProducts();
    }
    
}
