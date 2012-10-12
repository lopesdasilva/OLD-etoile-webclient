/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javaapi.ServiceManager;
import etoile.javaapi.forum.Forum;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class forumManager {
    private final ServiceManager manager;
    private Forum forum;
    
    /**
     *
     * @param manager
     * @param userManager
     */
    public forumManager (ServiceManager manager, userManager userManager){
        this.manager=manager; 
         System.out.println("Forum Manager class instancied");
           
        
        try {
            System.out.println("DEBUG: Loading "+userManager.getSelectedDiscipline().name+" forum");
            manager.userService().updateForum(userManager.getSelectedDiscipline());
            this.forum = userManager.getSelectedDiscipline().getForum();
            System.out.println("DEBUG: TOPICS SIZE: "+forum.getTopics().size());
        } catch (SQLException ex) {
            Logger.getLogger(forumManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
    
    
}
