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
import sha1.sha1;

@ManagedBean(name = "userRegister")
@SessionScoped
public class userRegister {

     @ManagedProperty(value = "#{sha1}")
    private sha1 sha1;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public sha1 getSha1() {
        return sha1;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setSha1(sha1 sha1) {
        this.sha1 = sha1;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String createUser() {
        try {
            System.out.println("-----------CREATING USER!----------");
            System.out.println("Username:" + username);
            System.out.println("Password:" + sha1.parseSHA1Password(password));
            System.out.println("FirstName:" + firstName);
            System.out.println("LastName:" + lastName);
            System.out.println("Email:" + email);

            Student student = new Student("teste", "40bd001563085fc35165329ea1ff5c5ecbdbbeef", "Ruben", "Paixao", "r@r.r");
            System.out.println(student.username);
            ServiceManager manager = new ServiceManager();

            manager.userService().addStudent(student);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unknown Error"));

        return "fail";

    }
}
