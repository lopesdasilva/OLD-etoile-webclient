/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javaapi.*;
import etoile.javaapi.question.Question;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import menu.MenuBean;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.event.TabChangeEvent;
import sha1.sha1;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager implements Serializable {

    @ManagedProperty(value = "#{sha1}")
    private sha1 sha1;
    private String username;
    private String password;
    private Student current_user;
    private MenuBean menu;
    ServiceManager manager;
    private Discipline selectedDiscipline;
    private Module selectedModule;
    public Test selectedTest;
    private String userAnswer = "";
    private String text;
    private String urlName;
    private String urlAdress;

    public String getUrlAdress() {
        return urlAdress;
    }

    public void setUrlAdress(String urlAdress) {
        this.urlAdress = urlAdress;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Test getSelectedTest() {
        return selectedTest;
    }

    public void setSelectedTest(Test selectedTest) {
        this.selectedTest = selectedTest;
    }

    public Module getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(Module selectedModule) {
        this.selectedModule = selectedModule;
    }

    public Discipline getSelectedDiscipline() {
        return selectedDiscipline;
    }

    public void setSelectedDiscipline(Discipline selectedDiscipline) {
        this.selectedDiscipline = selectedDiscipline;
    }

    public MenuBean getMenu() {
        //TODO:Evitar a linha de código seguinte.
        menu = new MenuBean(current_user.getCourses().getFirst().getDisciplines());
//        for( MenuItem m: menu.getModel().getMenuItems()){
//        System.out.println(m.toString());
//        }
        return this.menu;
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
            manager = new ServiceManager();

            if (manager.setAuthentication(username, sha1.parseSHA1Password(password))) {
                current_user = manager.getCurrent_student();

                //TODO:REMOVE
                System.out.println("***************Name:" + current_user.firstname);

                manager.userService().updateCourses(current_user.getId());


                //TODO:REMOVE
                System.out.println("***************Name:" + current_user.courses.getFirst().getName());



                //TODO replace arg with List

                this.menu = new MenuBean(current_user.getCourses().getFirst().getDisciplines());
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

    public String logOff() {
        //TODO: Propper logout
        try {
            System.out.println("Logoff");
            manager.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "logoff";

    }

    public String redirectAccount() {
        System.out.println("Account");
        return "account";

    }

    public String redirectProfile() {
        System.out.println("Profile");
        return "profile";
    }

    public String redirectDiscussionForum() {
        System.out.println("Forum");
        return "forum";
    }

    public String redirectAnnouncements() {
        System.out.println("Announcements");
        return "announcements";
    }

    public void redirectDiscussionForum(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        System.out.println("PRESSSSSSED " + aux_info.getValue() + "FROM:" + aux_discipline.getLabel());
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
    }

    public void redirectAnnouncements(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        System.out.println("PRESSSSSSED " + aux_info.getValue() + "FROM:" + aux_discipline.getLabel());
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("Selected Discipline: " + selectedDiscipline.getName() + " id" + selectedDiscipline.getId());
    }

    public String redirectModule() {
        System.out.println("Module");
        return "module";

    }

    public void redirectModule(ActionEvent event) {
        try {
            Object obj = event.getSource();
            MenuItem aux_info = (MenuItem) obj;
            Submenu aux_discipline = (Submenu) aux_info.getParent();
            System.out.println("PRESSSSSSED " + aux_info.getValue() + "FROM:" + aux_discipline.getLabel());
            selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
            selectedModule = manager.userService().getModule(aux_info.getValue());
            manager.userService().updateTests(selectedModule);
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void answerTest(ActionEvent actionEvent) {
        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;

        System.out.println("DEBUG: TEST ID: " + cb.getLabel());
        for (Test t : selectedModule.getTests()) {
            if (t.getId() == Integer.parseInt(cb.getLabel())) {
                try {
                    this.selectedTest = t;
                    selectedTest.setQuestions(new LinkedList<Question>());
                    manager.userService().updateQuestions(selectedTest);
                } catch (SQLException ex) {
                    Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println(selectedTest.author);
        System.out.println(selectedTest.name);

    }

    public void saveTest(ActionEvent actionEvent) {
//            selectedDiscipline.id;
//            selectedModule.id;
//            selectedTest.id;
        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;
        System.out.println("DEBUG: Question ID: " + cb.getLabel());
        int qID = Integer.parseInt(cb.getLabel());
        for (Question q : selectedTest.getQuestions()) {
            if (q.isOpenQuestion()) {
                if (q.getId() == qID) {
                    try {
                        manager.userService().updateOpenAnswer(q.getAnswerId(), q.getUserAnswer());

                        System.out.println("text: " + text);
                        System.out.println("Question: " + q.getText());
                        System.out.println("Answer: " + q.getUserAnswer());
                    } //
                    catch (SQLException ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail", "Answer not saved."));
                    }
                }
            }
//            
        }

//        manager.userService().updateOpenAnswer(, userAnswer);


//        manager.userService().updateOpenQuestion()
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved Answer"));

    }

    public void submitURL(ActionEvent actionEvent) {



        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;

        int qID = Integer.parseInt(cb.getLabel());
        System.out.println("DEBUG: Submit URL");
        System.out.println("DEBUG: Username: " + username);
        System.out.println("DEBUG: Question ID: " + qID);
        System.out.println("DEBUG: URL Name: " + urlName);
        System.out.println("DEBUG: URL Address: " + urlAdress);

        for (Question q : selectedTest.getQuestions()) {
            if (q.isOpenQuestion()) {
                if (q.getId() == qID) {
                    try {
                        manager.userService().addURL(urlName, urlAdress,q.getQuestionType(),q.getId());
                        urlName = "";
                        urlAdress = "";
                    } catch (SQLException ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail", "New URL submission failed"));

                    }
                }
            }

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "New URL submitted"));

    }
    //NOT WORKING

    public void onTabChange(TabChangeEvent event) {
        System.out.println("ID: " + event.getTab().getId());
        System.out.println("Title: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getId()));

    }
}
