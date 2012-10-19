/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javaapi.ServiceManager;
import etoile.javaapi.forum.Forum;
import etoile.javaapi.forum.Topic;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Administrator
 */
public class forumManager {

    private final ServiceManager manager;
    private Forum forum;
    private Topic selectedTopic;
    private String newTopicTitle;
    private String newTopicText;
    private String newReplyText;

    public Topic getSelectedTopic() {
        return selectedTopic;
    }

    public void setSelectedTopic(Topic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }

    public String getNewTopicTitle() {
        return newTopicTitle;
    }

    public void setNewTopicTitle(String newTopicTitle) {
        this.newTopicTitle = newTopicTitle;
    }

    public String getNewTopicText() {
        return newTopicText;
    }

    public void setNewTopicText(String newTopicText) {
        this.newTopicText = newTopicText;
    }

    public String getNewReplyText() {
        return newReplyText;
    }

    public void setNewReplyText(String newReplyText) {
        this.newReplyText = newReplyText;
    }

    /**
     *
     * @param manager
     * @param userManager
     */
    public forumManager(ServiceManager manager, userManager userManager) {
        this.manager = manager;
        System.out.println("Forum Manager class instancied");


        try {
            System.out.println("DEBUG: Loading " + userManager.getSelectedDiscipline().name + " forum");
            manager.userService().updateForum(userManager.getSelectedDiscipline());
            this.forum = userManager.getSelectedDiscipline().getForum();
            System.out.println("DEBUG: TOPICS SIZE: " + forum.getTopics().size());
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

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Topic Selected", ((Topic) event.getObject()).getTitle());
        try {
            manager.userService().updateTopicAnswers(((Topic) event.getObject()));
        } catch (SQLException ex) {
            Logger.getLogger(forumManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();


        try {
            ec.redirect("topic.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(forumManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Topic) event.getObject()).getTitle());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void addNewTopic(ActionEvent actionEvent) {
        System.out.println("DEBUG: Adding a new topic");
        System.out.println("DEBUG: Topic title:" + newTopicTitle);
        System.out.println("DEBUG: Topic text:" + newTopicText);
        try {
            manager.userService().addForumTopic(forum, newTopicTitle,newTopicText);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Topic Added"));
        } catch (SQLException ex) {
            Logger.getLogger(forumManager.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Topic not Added"));

        }
        newTopicText="";
        newTopicTitle="";
    }

    public void addNewReply(ActionEvent actionEvent) {
        System.out.println("DEBUG: Adding a new topic");
        System.out.println("DEBUG: Topic selected title:" + selectedTopic.getTitle());
        System.out.println("DEBUG: New reply text:" + newReplyText);
        
        try {
            manager.userService().addTopicAnswer(selectedTopic, newReplyText);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Reply Added"));
        
        } catch (SQLException ex) {
            Logger.getLogger(forumManager.class.getName()).log(Level.SEVERE, null, ex);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Reply not Added"));

        }
        newReplyText="";

    }
}
