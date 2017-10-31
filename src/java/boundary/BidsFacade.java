/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Bids;
import entities.Product;
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
public class BidsFacade extends AbstractFacade<Bids> {

    @PersistenceContext(unitName = "SimpleEE6AppNewPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BidsFacade() {
        super(Bids.class);
    }
    
     public List<Bids> findRecent() {
        TypedQuery<Bids> query = em.createQuery("SELECT a FROM Bids a ORDER BY a.startTime DESC",Bids.class);
        List<Bids> result = query.getResultList();
        return result;
    }

    public void publish(AuctionUserView auctionUserView) {
        List<Product> catalogProductsToBePublished = em.createQuery("SELECT p FROM Product p WHERE p.productCatalog.id = '"+ auctionUserView.getAuctionUser().getProductCatalog().getId()+"'",Product.class).getResultList();
        List<Bids> bidsAlreadyExistingFromTheCatalog = em.createQuery("SELECT b FROM Bids b WHERE b.product.productCatalog.id = '"+auctionUserView.getAuctionUser().getProductCatalog().getId()+"'",Bids.class).getResultList();
        long currentTime = System.currentTimeMillis();
        for (Product p : catalogProductsToBePublished){
            boolean exist=false;
            for (Bids b : bidsAlreadyExistingFromTheCatalog){
                if (b.getProduct().getId() == p.getId())
                    exist=true;
            }
            if (exist)
                continue;
            Bids b = new Bids();
            b.setAuctionUser(auctionUserView.getAuctionUser());
            b.setBid(p.getStartingBid());
            b.setProduct(p);
            b.setStartTime((int)currentTime/1000);
            //sets bidDuration to 2 days. . . Should be chosen in the web interface
            b.setBidDuration(172800);
            em.persist(b);
//            query = em.createQuery("IF EXISTS (SELECT b FROM Bids b WHERE b.product.id = '"+p.getId()+"') UPDATE Bids SET Bids.product.id = '"+p.getId()+"', Bids.auctionUser.id = '"+p.getAuctionUser().getId()+"' WHERE Bids.product.id = '"+p.getId()+"' ELSE INSERT INTO Bids VALUE (bid, startTime, auctionUser.id, product.id) VALUES ('"+p.getStartingBid()+"','"+currentTime+"','"+auctionUserView.getAuctionUser().getId()+"','"+p.getId()+"')",Product.class);
        }
    }

    public Bids getBidFromProduct(Product product) {
        Bids b = null;
        TypedQuery<Bids> query = em.createQuery("SELECT bid FROM Bids bid Where bid.product.id = '"+product.getId()+"'",Bids.class);
        try {
            b = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public void placeBid(Bids bid, AuctionUserView auctionUserView) {
        Bids b = em.find(Bids.class,bid.getId());
        b.setBid(bid.getBid()+5);
        b.setAuctionUser(auctionUserView.getAuctionUser());
        em.persist(b);
    }

    public List<Bids> findBidsWithProductName(String productName) {
        TypedQuery<Bids> query = em.createQuery("SELECT b FROM Bids b WHERE b.product.productName LIKE '%"+productName+"%'",Bids.class);
        List<Bids> list = query.getResultList();
        return list;
    }
    
}
