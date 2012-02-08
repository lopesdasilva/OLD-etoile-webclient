/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import db.DBConnect;
import db.SQLInstruct;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sha1.sha1;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager {

    @ManagedProperty(value="#{sha1}")
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

    public String CheckValidUser() {
        
        try {
            System.out.println(sha1.parseSHA1Password(password));
            DBConnect db = new DBConnect(SQLInstruct.dbAdress, SQLInstruct.dbUsername, SQLInstruct.dbPassword);
            db.loadDriver();
            String sqlStatement = SQLInstruct.login(username, sha1.parseSHA1Password(password));
            ResultSet rSet = db.queryDB(sqlStatement);


            if (rSet.next()) {
                user_id = rSet.getInt(1);


                System.out.println("User: " + username + " has logged On.");

                return "success";
            }

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("NoSuchAlgorithmException");
        } catch (InstantiationException ex) {
            System.out.println("InstantiationException");
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Wrong User or Password"));
        return "fail";

    }
}
