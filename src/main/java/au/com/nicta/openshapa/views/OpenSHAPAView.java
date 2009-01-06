package au.com.nicta.openshapa.views;

import au.com.nicta.openshapa.OpenSHAPA;
import au.com.nicta.openshapa.db.SystemErrorException;
import java.awt.FileDialog;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.File;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.apache.log4j.Logger;

/**
 * This application is a simple text editor. This class displays the main frame
 * of the application and provides much of the logic. This class is called by
 * the main application class, DocumentEditorApp. For an overview of the
 * application see the comments for the DocumentEditorApp class.
 */
public final class OpenSHAPAView extends FrameView
implements KeyEventDispatcher {

    /**
     * Constructor.
     *
     * @param app The SingleFrameApplication that invoked this main FrameView.
     */
    public OpenSHAPAView(SingleFrameApplication app) {
        super(app);
        KeyboardFocusManager key = KeyboardFocusManager
                                   .getCurrentKeyboardFocusManager();
        key.addKeyEventDispatcher(this);

        // generated GUI builder code
        initComponents();
    }

    /**
     * Dispatches the keystroke to the correct action.
     *
     * @param evt The event that triggered this action.
     *
     * @return true if the KeyboardFocusManager should take no further action
     * with regard to the KeyEvent; false  otherwise
     */
    public boolean dispatchKeyEvent(java.awt.event.KeyEvent evt) {
        // Pass the keyevent onto the keyswitchboard so that it can route it
        // to the correct action.
        return OpenSHAPA.getApplication().dispatchKeyEvent(evt);
    }

    /**
     * Action for creating a new database.
     */
    @Action
    public void showNewDatabaseForm() {
        OpenSHAPA.getApplication().showNewDatabaseForm();
    }

    /**
     * Action for creating a new variable.
     */
    @Action
    public void showNewVariableForm() {
        OpenSHAPA.getApplication().showNewVariableForm();
    }

    /**
     * Action for showing the variable list.
     */
    @Action
    public void showVariableList() {
        OpenSHAPA.getApplication().showVariableList();
    }

    /**
     * Action for showing the quicktime video controller.
     */
    @Action
    public void showQTVideoController() {
        OpenSHAPA.getApplication().showQTVideoController();
    }

    /**
     * Action for showing the spreadsheet.
     */
    @Action
    public void showSpreadsheet() {
        OpenSHAPA.getApplication().showSpreadsheet();
    }

    /**
     * Action for running tests.
     */
    @Action
    public void runTests() {
        try {
            OpenSHAPA.getApplication().runRegressionTests();
        } catch (SystemErrorException e) {
            logger.error("Unable to run regression tests", e);
        }
    }

    /**
     * Action for invoking a script.
     */
    @Action
    public void runScript() {
        FileDialog c = new FileDialog(OpenSHAPA.getApplication().getMainFrame(),
                                      "Select ruby script file:",
                                      FileDialog.LOAD);
        c.setVisible(true);

        if (c.getFile() != null && c.getDirectory() != null) {
            File rubyFile = new File(c.getDirectory() + c.getFile());
            OpenSHAPA.getApplication().runScript(rubyFile);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem openMenuItem = new javax.swing.JMenuItem();
        javax.swing.JSeparator fileMenuSeparator = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        newCellMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        qtControllerItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        helpMenu1 = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem1 = new javax.swing.JMenuItem();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 500, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(au.com.nicta.openshapa.OpenSHAPA.class).getContext().getResourceMap(OpenSHAPAView.class);
        resourceMap.injectComponents(mainPanel);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(au.com.nicta.openshapa.OpenSHAPA.class).getContext().getActionMap(OpenSHAPAView.class, this);
        openMenuItem.setAction(actionMap.get("showNewDatabaseForm")); // NOI18N
        openMenuItem.setText(resourceMap.getString("newMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenu.add(openMenuItem);

        fileMenuSeparator.setName("fileMenuSeparator"); // NOI18N
        fileMenu.add(fileMenuSeparator);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu3.setAction(actionMap.get("showQTVideoController")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        jMenuItem2.setAction(actionMap.get("showSpreadsheet")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenu3.add(jMenuItem2);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu3.add(jSeparator1);

        jMenuItem1.setAction(actionMap.get("showNewVariableForm")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenu3.add(jMenuItem1);

        jMenuItem3.setAction(actionMap.get("showVariableList")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenu3.add(jMenuItem3);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jMenu3.add(jSeparator2);

        newCellMenuItem.setName("newCellMenuItem"); // NOI18N
        newCellMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCellMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(newCellMenuItem);

        menuBar.add(jMenu3);

        jMenu2.setName("jMenu2"); // NOI18N

        qtControllerItem.setAction(actionMap.get("showQTVideoController")); // NOI18N
        qtControllerItem.setName("qtControllerItem"); // NOI18N
        jMenu2.add(qtControllerItem);

        menuBar.add(jMenu2);

        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem4.setAction(actionMap.get("runScript")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenu1.add(jMenuItem4);

        menuBar.add(jMenu1);

        helpMenu1.setName("helpMenu1"); // NOI18N

        contentsMenuItem.setAction(actionMap.get("runTests")); // NOI18N
        contentsMenuItem.setName("contentsMenuItem"); // NOI18N
        helpMenu1.add(contentsMenuItem);

        aboutMenuItem1.setName("aboutMenuItem1"); // NOI18N
        helpMenu1.add(aboutMenuItem1);

        menuBar.add(helpMenu1);
        resourceMap.injectComponents(menuBar);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The action to inoke when the user selects new cell from the menu.
     *
     * @param evt The event that fired this action.
     */
    private void newCellMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCellMenuItemActionPerformed
        OpenSHAPA.getApplication().createNewCell(0);
}//GEN-LAST:event_newCellMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem1;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenu helpMenu1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newCellMenuItem;
    private javax.swing.JMenuItem qtControllerItem;
    // End of variables declaration//GEN-END:variables

    /** Logger for this class. */
    private static Logger logger = Logger.getLogger(OpenSHAPAView.class);
}
