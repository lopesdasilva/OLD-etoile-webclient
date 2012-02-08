/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import db.DBConnect;
import db.SQLInstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager {
    
    private String username;
    private String password;

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
        DBConnect db = new DBConnect(SQLInstruct.dbAdress, SQLInstruct.dbUsername, SQLInstruct.dbPassword);
        if(username.equals("teste")){
            System.out.println("Username: "+username);
            return "success";
        }
        return "fail";
    }
}
