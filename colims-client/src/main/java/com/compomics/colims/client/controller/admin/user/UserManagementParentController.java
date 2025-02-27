package com.compomics.colims.client.controller.admin.user;

import com.compomics.colims.client.controller.Controllable;
import com.compomics.colims.client.controller.MainController;
import com.compomics.colims.client.util.GuiUtils;
import com.compomics.colims.client.view.admin.UserManagementDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Niels Hulstaert
 */
@Component("userManagementController")
@Lazy
public class UserManagementParentController implements Controllable {

    //model
    //view
    private UserManagementDialog userManagementDialog;
    //parent controller
    @Autowired
    private MainController mainController;
    //child controllers
    @Autowired
    @Lazy
    private UserManagementController userManagementController;
    @Autowired
    @Lazy
    private GroupManagementController groupManagementController;
    @Autowired
    @Lazy
    private RoleManagementController roleManagementController;
    @Autowired
    @Lazy
    private PermissionManagementController permissionManagementController;

    /**
     * Get the view of this controller.
     *
     * @return the UserManagementDialog
     */
    public UserManagementDialog getUserManagementDialog() {
        return userManagementDialog;
    }

    @Override
    @PostConstruct
    public void init() {
        //init view
        userManagementDialog = new UserManagementDialog(mainController.getMainFrame(), true);

        //add action listeners
        userManagementDialog.getCloseButton().addActionListener(e -> {
            userManagementController.onCancel();
            groupManagementController.onCancel();
            roleManagementController.onCancel();
            permissionManagementController.onCancel();

            userManagementDialog.dispose();
        });
    }

    @Override
    public void showView() {
        userManagementController.showView();
        groupManagementController.showView();
        roleManagementController.showView();
        permissionManagementController.showView();

        //reset tab selection
        userManagementDialog.getUserManagementTabbedPane().setSelectedIndex(0);

        GuiUtils.centerDialogOnComponent(mainController.getMainFrame(), userManagementDialog);
        userManagementDialog.setVisible(true);
    }
}
