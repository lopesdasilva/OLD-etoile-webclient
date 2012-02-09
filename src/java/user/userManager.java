/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javaapi.ServiceManager;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sha1.sha1;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager {

    @ManagedProperty(value = "#{sha1}")
    private sha1 sha1;
    private String username;
    private String password;
    int user_id;

    public void setSha1(sha1 sha1) {
        this.sha1 = sha1;
    }

    public sha1 getSha1() {
        return sha1;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String checkValidUser() {
        try {
            ServiceManager manager = new ServiceManager();
            boolean a=manager.setAuthentication(username, sha1.parseSHA1Password(password));
            System.out.println("--------------------------------------"+manager.setAuthentication(username, sha1.parseSHA1Password(password)));
            
            if (manager.setAuthentication(username, sha1.parseSHA1Password(password))) {
                return "success";
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Wrong User or Password"));

        return "fail";

    }
}
