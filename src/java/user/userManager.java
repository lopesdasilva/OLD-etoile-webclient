/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;


import etoile.javaapi.ServiceManager;
import etoile.javaapi.Student;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import menu.MenuBean;
import sha1.sha1;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager {

    @ManagedProperty(value = "#{sha1}")
    private sha1 sha1;
    private String username;
    private String password;
    private Student current_user;
    private MenuBean menu;
    ServiceManager manager;
    public MenuBean getMenu() {
        return menu;
    }

    public Student getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(Student current_user) {
        this.current_user = current_user;
    }


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
             manager= new ServiceManager();
            
            if (manager.setAuthentication(username, sha1.parseSHA1Password(password))) {
                current_user = manager.getCurrent_student();
                
                //TODO:REMOVE
                System.out.println("***************Name:"+current_user.firstname);
                
                manager.userService().updateCourses(current_user.getId());
                
                
                //TODO:REMOVE
                System.out.println("***************Name:"+current_user.courses.getFirst().getName());
                
               
                
                //TODO replace arg with List
                
                menu = new MenuBean(current_user.getCourses().getFirst().getDisciplines());
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
    
    public String logOff(){
        //TODO: Propper logout
        try {
            manager.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
        
    } 
    
    public String redirectAccount(){
        return "success";
        
    }
    public String redirectProfile(){
        return "success";
    }
    
}
