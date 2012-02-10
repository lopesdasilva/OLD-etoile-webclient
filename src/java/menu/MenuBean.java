/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import etoile.javaapi.Discipline;
import etoile.javaapi.Module;
import java.io.Serializable;
import java.util.LinkedList;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

public class MenuBean implements Serializable {

    private MenuModel model;

    public MenuBean(LinkedList<Discipline> disciplines) {
        model = new DefaultMenuModel();
        //First submenu  
        Submenu submenu = new Submenu();
        MenuItem item;





        for (Discipline d : disciplines) {
            submenu = new Submenu();

            submenu.setLabel(d.getName());

            item = new MenuItem();
            item.setValue("Announcements");
            item.setUrl("#");
            submenu.getChildren().add(item);
            MenuItem item2 = new MenuItem();
            item2.setValue("Contents");
            item2.setUrl("#");
            submenu.getChildren().add(item2);
            MenuItem item3 = new MenuItem();
            item3.setValue("Discussion Forum");
            item3.setUrl("#");
            submenu.getChildren().add(item3);

            for (Module m : d.getModules()) {
                item = new MenuItem();
                item.setValue(m.getName());
                item.setUrl("#");
                submenu.getChildren().add(item);
            }
            model.addSubmenu(submenu);
        }
    }

    public MenuModel getModel() {
        return model;
    }
}
