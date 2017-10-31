/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Bids;
import entities.Product;
import entities.ProductCatalog;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import my.presentation.AuctionUserView;

/**
 *
 * @author Daniel
 */
@Stateless
public class ProductCatalogFacade extends AbstractFacade<ProductCatalog> {

    @PersistenceContext(unitName = "SimpleEE6AppNewPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductCatalogFacade() {
        super(ProductCatalog.class);
    }
    
    public void Publish(AuctionUserView auctionUserView) {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaUpdate<Product> update = builder.createCriteriaUpdate(Product.class);
//        Root<Product> e = update.from(Product.class);
//        e.join(Product_.productCatalog);
//        update.set(Product_.published, true);
//        update.where(builder.equal(e.get((Product_.productCatalog)).get(ProductCatalog_.id).as(Long.class),(catalog.getId())));
//        this.em.createQuery(update).executeUpdate();
        //Updates the products (published=true)
        TypedQuery<Product> query = em.createQuery("UPDATE Product SET published = "+true+" where productCatalog.id = '" + auctionUserView.getAuctionUser().getProductCatalog().getId()+"'",Product.class);
        query.executeUpdate();
        //Creates Bids if they do not alreay exist for the Products to be published
        List<Product> catalogProductsToBePublished = em.createQuery("SELECT p FROM Product p WHERE p.productCatalog.id = '"+ auctionUserView.getAuctionUser().getProductCatalog().getId()+"'",Product.class).getResultList();
        List<Bids> bidsAlreadyExistingFromTheCatalog = em.createQuery("SELECT b FROM Bids b WHERE b.auctionUser.id = '"+auctionUserView.getAuctionUser().getId()+"'",Bids.class).getResultList();
        long currentTime = System.currentTimeMillis();
        for (Product p : catalogProductsToBePublished){
            boolean exist=false;
            for (Bids b : bidsAlreadyExistingFromTheCatalog){
                if (b.getProduct().getId() == p.getId())
                    exist=true;
            }
//            query = em.createQuery("IF EXISTS (SELECT b FROM Bids b WHERE b.product.id = '"+p.getId()+"') UPDATE Bids SET Bids.product.id = '"+p.getId()+"', Bids.auctionUser.id = '"+p.getAuctionUser().getId()+"' WHERE Bids.product.id = '"+p.getId()+"' ELSE INSERT INTO Bids VALUE (bid, startTime, auctionUser.id, product.id) VALUES ('"+p.getStartingBid()+"','"+currentTime+"','"+auctionUserView.getAuctionUser().getId()+"','"+p.getId()+"')",Product.class);
        }   
    }
}
