/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import etoile.javaapi.Discipline;
import etoile.javaapi.Module;
import java.io.Serializable;
import java.util.LinkedList;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

public class MenuBean implements Serializable {

    private MenuModel model;

    public MenuBean(LinkedList<Discipline> disciplines) {
        model = new DefaultMenuModel();
        //First submenu 
        MenuItem item;



        for (Discipline d : disciplines) {
            Submenu submenu = new Submenu();

            submenu.setLabel(d.getName());

            item = new MenuItem();
            item.setValue("News & Resources");
            //item.setId(d.getId()+"");
            item.setAjax(false);            
            item.setActionListener(createActionListener("#{userManager.redirectAnnouncements}"));
            item.setActionExpression(createAction_old("#{userManager.redirectAnnouncements}", String.class));
            submenu.getChildren().add(item);

//            MenuItem item2 = new MenuItem();
//            item2.setValue("Contents");
//            item2.setUrl("#");
//            submenu.getChildren().add(item2);
            MenuItem item3 = new MenuItem();
            item3.setValue("Discussion Forum");
            item3.setAjax(false);
            item3.setActionListener(createActionListener("#{userManager.redirectDiscussionForum}"));
            item3.setActionExpression(createAction_old("#{userManager.redirectDiscussionForum}", String.class));
            submenu.getChildren().add(item3);

            for (Module m : d.getModules()) {
                item = new MenuItem();
                item.setValue(m.getName());
                item.setAjax(false);
                item.setActionListener(createActionListener("#{userManager.redirectModule}"));
                item.setActionExpression(createAction_old("#{userManager.redirectModule}", String.class));
                
                submenu.getChildren().add(item);
            }
            model.addSubmenu(submenu);
        }
    }

    public MenuModel getModel() {
        return model;
    }

    private MethodExpression createAction_old(String actionExpression, Class<String> aClass) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), actionExpression, null, new Class[0]);
    }

    private MethodBinding createActionListener(String actionListenerExpression) {
        Application app = FacesContext.getCurrentInstance().getApplication();

        return app.createMethodBinding(actionListenerExpression, new Class[]{ActionEvent.class});

    }
}
