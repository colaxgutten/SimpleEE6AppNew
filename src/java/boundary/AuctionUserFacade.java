/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.AuctionUser;
import entities.Product;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel
 */
@Stateless
public class AuctionUserFacade extends AbstractFacade<AuctionUser> {

    @PersistenceContext(unitName = "SimpleEE6AppNewPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuctionUserFacade() {
        super(AuctionUser.class);
    }
    
    public boolean ifUserExist(AuctionUser auctionUser){
        if (auctionUser == null)
            return false;
        TypedQuery<AuctionUser> query = em.createQuery("Select u from AuctionUser u where u.username = '" + auctionUser.getUsername() + "'",AuctionUser.class);
        AuctionUser u = null;
        try {
        u = query.getSingleResult();
        } catch(Exception e){
            
        }
        return u!= null && u.getUsername().equals(auctionUser.getUsername());
    }
    //TODO
    public boolean authenticate(AuctionUser auctionUser){
//        Query query = em.createQuery("SELECT x FROM User x WHERE x.username = '" + user.getUsername() + "'");
//        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(User.class);
//        CriteriaQuery select = cq.select(cq.from(User.class));
        boolean validLogin = false;
        TypedQuery<AuctionUser> query = em.createQuery("Select u from AuctionUser u where u.username = '" + auctionUser.getUsername() + "'",AuctionUser.class);
        AuctionUser u = null;
        System.out.println(auctionUser.getUsername());
        System.out.println(auctionUser.getPassword());
        try {
        u = query.getSingleResult();
        } catch(Exception e){
            System.out.println(e);
        }
        if (u!= null){
            validLogin =  u.getUsername().equals(auctionUser.getUsername()) && u.getPassword().equals(auctionUser.getPassword());
        }
        if (validLogin){
            auctionUser.Copy(u);
        }
        return validLogin;
    }

    public void save(AuctionUser auctionUser) {
//        TypedQuery<AuctionUser> query = em.createQuery("Select u from AuctionUser u where u.username = '" + auctionUser.getUsername() + "'",AuctionUser.class);
          AuctionUser a = em.find(AuctionUser.class, auctionUser.getId());
          a.Copy(auctionUser);
          em.persist(a);
//        AuctionUser u = null;
//        try {
//            u = query.getSingleResult();
//        } catch(Exception e){
//            System.out.println(e);
//        }
//        if (u!=null){
//            TypedQuery<AuctionUser> tq = em.createQuery("UPDATE AuctionUser SET productcatalog_id = '"+auctionUser.getProductCatalog().getId()+"' WHERE id = '"+ u.getId()+"'",AuctionUser.class);
//            tq.executeUpdate();
//        }
    }
    
    public void update(AuctionUser auctionUser){
        em.flush();
        TypedQuery<Product> query = em.createQuery("Select u from Product u where u.auctionUser.id = '" + auctionUser.getId() + "'",Product.class);
        List<Product> products = query.getResultList();
        TypedQuery<Product> query2 = em.createQuery("Select u from Product u where u.productCatalog.id = '" +auctionUser.getProductCatalog().getId()+"'",Product.class);
        List<Product> catalogProducts = query2.getResultList();
        AuctionUser dbUser = em.find(AuctionUser.class, auctionUser.getId());
        dbUser.setProducts(products);
        dbUser.getProductCatalog().setProducts(catalogProducts);
        em.persist(dbUser);
        auctionUser.Copy(dbUser);
    }
    
}
