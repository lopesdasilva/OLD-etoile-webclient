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
import java.util.List;
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
    private LinkedList<String> respostas = new LinkedList<String>();

    public LinkedList<String> getRespostas() {
        return respostas;
    }

    public void setRespostas(LinkedList<String> respostas) {
        this.respostas = respostas;
    }

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
        System.out.println("DEBUG: USER: " + username + " TRYING TO LOGIN");
        try {
            manager = new ServiceManager();

            if (manager.setAuthentication(username, sha1.parseSHA1Password(password))) {
                current_user = manager.getCurrent_student();

                manager.userService().updateCourses(current_user.getId());

                this.menu = new MenuBean(current_user.getCourses().getFirst().getDisciplines());

                System.out.println("DEBUG: USER: " + username + " SUCCESS");
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
        System.out.println("DEBUG: USER: " + username + " FAIL");
        return "fail";

    }

    public String logOff() {
        //TODO: Propper logout
        try {
            System.out.println("DEBUG: Redirecting to Logoff");
            manager.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "logoff";

    }

    public String redirectAccount() {
        System.out.println("DEBUG: Redirecting to Account");
        return "account";

    }

    public String redirectProfile() {
        System.out.println("DEBUG: Redirecting to Profile");
        return "profile";
    }

    public String redirectDiscussionForum() {
        System.out.println("DEBUG: Redirecting to Forum");
        return "forum";
    }

    public String redirectAnnouncements() {
        System.out.println("DEBUG: Redirecting to Announcements");
        return "announcements";
    }

    public void redirectDiscussionForum(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());

    }

    public void redirectAnnouncements(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());

    }

    public String redirectModule() {
        System.out.println("DEBUG: Redirecting to Module");
        return "module";

    }

    public void redirectModule(ActionEvent event) {
        try {
            Object obj = event.getSource();
            MenuItem aux_info = (MenuItem) obj;
            Submenu aux_discipline = (Submenu) aux_info.getParent();

            selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
            selectedModule = manager.userService().getModule(aux_info.getValue());
            manager.userService().updateTests(selectedModule);
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());
        System.out.println("DEBUG: SELECTED MODULE: " + selectedModule.name + " ID: " + selectedModule.getId());

    }

    public void answerTest(ActionEvent actionEvent) {
        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;


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
        System.out.println("DEBUG: SELECTED TEST: " + selectedTest.name + " ID: " + selectedTest.getId());
        System.out.println("DEBUG: SELECTED TEST AUTHOR: " + selectedTest.author);


        
       
        
    }

    public void saveTest(ActionEvent actionEvent) {
//            selectedDiscipline.id;
//            selectedModule.id;
//            selectedTest.id;
        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;

        int qNumber = Integer.parseInt(cb.getLabel());



        for (Question q : selectedTest.getQuestions()) {

            if (q.getNumber() == qNumber) {
                try {
                    switch (q.getQuestionType()) {
                        case MULTIPLE_CHOICE:
                            System.out.println("DEBUG: Question: " + q.getText());
                            System.out.println("DEBUG: Question: " + q.getText());
                            System.out.println("DEBUG: Selected Answers:");
                            for (String s : q.getUserAnswers()) {
                                System.out.println("DEBUG: Answer: " + s);
                            }
                            break;
                        case ONE_CHOICE:
                                manager.userService().updateOneChoiceAnswer(q.getAnswerId(), q.getUserAnswer());
                            System.out.println("DEBUG:  Question: " + q.getText());
                            System.out.println("Question: " + q.getText());
                            System.out.println("Answer: " + q.getUserAnswer());
                            break;
                        case OPEN:
                            manager.userService().updateOpenAnswer(q.getAnswerId(), q.getUserAnswer());
                            System.out.println("DEBUG:  Question: " + q.getText());
                            System.out.println("Question: " + q.getText());
                            System.out.println("Answer: " + q.getUserAnswer());
                            break;
                    }


                } catch (SQLException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail", "Answer not saved."));
                }
            }
        }
//            


//        manager.userService().updateOpenAnswer(, userAnswer);


//        manager.userService().updateOpenQuestion()
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Saved Answer"));

    }

    public void submitURL(ActionEvent actionEvent) {



        Object obj = actionEvent.getSource();
        CommandButton cb = (CommandButton) obj;

        int qNumber = Integer.parseInt(cb.getLabel());
        System.out.println("DEBUG: Submit URL");
        System.out.println("DEBUG: Username: " + username);
        System.out.println("DEBUG: Question Number: " + qNumber);
        System.out.println("DEBUG: URL Name: " + urlName);
        System.out.println("DEBUG: URL Address: " + urlAdress);
        Question selectedQuestion = null;
        for (Question q : selectedTest.getQuestions()) {

            if (q.getNumber() == qNumber) {
                selectedQuestion = q;
                break;
            }

        }
        try {
            System.out.println(selectedQuestion.getQuestionType() + "");
            manager.userService().addURL(urlName, urlAdress, selectedQuestion.getQuestionType(), selectedQuestion.getId());

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fail", "New URL submission failed"));


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
