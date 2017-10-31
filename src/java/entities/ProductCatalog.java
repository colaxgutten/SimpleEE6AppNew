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
 * @author Arezoo Sol
 */
@Entity
public class ProductCatalog implements Serializable {

    @OneToOne(mappedBy = "productCatalog")
    private AuctionUser auctionUser;

    public AuctionUser getAuctionUser() {
        return auctionUser;
    }

    public void setAuctionUser(AuctionUser auctionUser) {
        this.auctionUser = auctionUser;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  
    
    
    @OneToMany(mappedBy = "productCatalog")
    private List<Product> products;
    
    public List<Product> getProducts() {
        System.out.println(products.size());
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    private String profilename;

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
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
        if (!(object instanceof ProductCatalog)) {
            return false;
        }
        ProductCatalog other = (ProductCatalog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Catalog[ id=" + id + " ]";
    }
    
}
