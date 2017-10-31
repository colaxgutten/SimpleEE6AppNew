/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.presentation;

import boundary.AuctionUserFacade;
import entities.AuctionUser;
import entities.ProductCatalog;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "AuctionUserView")
@SessionScoped
public class AuctionUserView {
    
    //@XmlTransient for reference
    
    @EJB
    private AuctionUserFacade userFacade;
    private AuctionUser auctionUser;
    
    /**
     * Creates a new instance of UserView
     */
    public AuctionUserView() {
        auctionUser = new AuctionUser();
     }

    public AuctionUser getAuctionUser() {
        return auctionUser;
    }

    public void setUser(AuctionUser auctionUser) {
        this.auctionUser = auctionUser;
    }

    public AuctionUserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(AuctionUserFacade userFacade) {
        this.userFacade = userFacade;
    }
    
    public String register(){
        if (userFacade.ifUserExist(auctionUser)){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Username already exists","Register error"));
            return "index";
        }
        else {
            userFacade.create(auctionUser);
            return authenticate();
        }
    }
    
    public String authenticate(){
        if (userFacade == null)
            System.out.println("userFacade er null.. . .. . .. .. . .");
        else {
        if (userFacade.authenticate(auctionUser))
            return "theend";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong username and password combination.","Login error"));
        return "index";
    }

    public String getTest(){
        return "indexOld.xhtml";
    }

    void save() {
        userFacade.save(auctionUser);
    }

    void update() {
        userFacade.update(auctionUser);
    } 
}