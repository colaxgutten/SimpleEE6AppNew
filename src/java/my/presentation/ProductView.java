/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.presentation;

import boundary.ProductFacade;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "ProductView")
@SessionScoped
public class ProductView {
    @EJB
    private ProductFacade productFacade;
    private Product product;
    
    @ManagedProperty(value="#{AuctionUserView}")
    private AuctionUserView auctionUserView;

    public AuctionUserView getAuctionUserView() {
        return auctionUserView;
    }

    public void setAuctionUserView(AuctionUserView auctionUserView) {
        this.auctionUserView = auctionUserView;
    }
    
    public Product getProduct(){
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductFacade getProductFacade() {
        return productFacade;
    }

    public void setProductFacade(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }
    
    public void test(AjaxBehaviorEvent abe){
        product.setId((Long)((UIOutput)abe.getSource()).getValue());
    }
    
    public void addToCatalog(){
        product.setProductCatalog(auctionUserView.getAuctionUser().getProductCatalog());
        productFacade.save(product);
        auctionUserView.update();
    }

    /**
     * Creates a new instance of ProductView
     */
    public ProductView() {
        this.product = new Product();
    }
    
    public void createProduct(){
        product.setId(null);
        product.setAuctionUser(auctionUserView.getAuctionUser());
        productFacade.create(product);
        auctionUserView.update();
    }
    
    
    public List<Product> getAllProductsAsList(){
        return productFacade.findAll();
    }
    
    public String getAllProducts(){
        String s = "";
        for (Product p: productFacade.findAll()){
            s+=p.toString();
        }
        return s;
    }
    
    public String getAllProductsAsTable(){
                String s = "<table><tr><td>Product name</td><td>Feedback</td><td>Contact information</td><td>Sellers rating</td></tr>";
                for (Product p : productFacade.findAll())
                    s+=p.getProductAsTableRow();
                s+="</table>";
                return s;
    }
    
    public List<Product> getSearchForProducts(){
        List<Product> results = productFacade.findProductThatContains(product.getProductName());
        return results;
    }
    
    private String asTable(List<Product> list){
        if (list==null)
            return"null";
        String s="<table><tr><td>ID</td><td>Name</td><td>Feedback</td></tr>";
        for (Product p : list){
            s+="<tr><td>"+p.getId()+"</td><td>"+p.getProductName()+"</td><td>"+p.getFeedBack()+"</td></tr>";
        }
        s+="</table>";
        return s;
    }
    
    public int getNumberOfProducts(){
        return productFacade.findAll().size();
    }
    
    public String postProduct(){
        return "theend";
    }
    
}
