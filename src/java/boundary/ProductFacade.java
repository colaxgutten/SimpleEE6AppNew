/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

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
public class ProductFacade extends AbstractFacade<Product> {

    @PersistenceContext(unitName = "SimpleEE6AppNewPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    public List<Product> findProductThatContains(String string){
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.productName LIKE '%" + string + "%' AND p.published = "+true,Product.class);
        List<Product> result = query.getResultList();
        return result;
    }

    public void save(Product product) {
        TypedQuery<Product> query = em.createQuery("Select p from Product p where p.id = '"+product.getId()+"'",Product.class);
        Product dbProduct = query.getSingleResult();
        dbProduct.setProductCatalog(product.getProductCatalog());
        System.out.println(product);
        em.persist(dbProduct);
        
    }
    
}
