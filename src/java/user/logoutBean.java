/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lopesdasilva
 */
@ManagedBean(name = "logoutBean")
@ViewScoped
public class logoutBean {

    public void idleListener() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        killHttpSession(ctx);
    }

    public void activeListener() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        killHttpSession(ctx);
        doRedirectToLoggedOutPage(ctx);
    }
    
    public void activeListener2() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        killHttpSession(ctx);
        doRedirectToLoggedOutPage2(ctx);
    }
    
     private void doRedirectToLoggedOutPage2(FacesContext ctx) {
        try {
            
            ctx.getExternalContext().redirect("");
        } catch (IOException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     private void doRedirectToLoggedOutPage(FacesContext ctx) {
        try {
            ctx.getExternalContext().redirect("index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void killHttpSession(FacesContext ctx) {
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        session.invalidate();

    }
}
