package au.com.nicta.openshapa;

import au.com.nicta.openshapa.controllers.CreateNewCellC;
import au.com.nicta.openshapa.db.Database;
import au.com.nicta.openshapa.db.LogicErrorException;
import au.com.nicta.openshapa.db.MacshapaDatabase;
import au.com.nicta.openshapa.db.SystemErrorException;
import au.com.nicta.openshapa.util.Constants;
import au.com.nicta.openshapa.views.ListVariables;
import au.com.nicta.openshapa.views.OpenSHAPAView;
import au.com.nicta.openshapa.views.QTVideoController;
import au.com.nicta.openshapa.views.ScriptOutput;
import au.com.nicta.openshapa.views.discrete.layouts.SheetLayoutFactory.SheetLayoutType;
import com.sun.script.jruby.JRubyScriptEngineManager;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SessionStorage;
import org.jdesktop.application.SingleFrameApplication;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The main class of the application.
 */
public final class OpenSHAPA extends SingleFrameApplication
implements KeyEventDispatcher {

    /**
     * Dispatches the keystroke to the correct action.
     *
     * @param evt The event that triggered this action.
     *
     * @return true if the KeyboardFocusManager should take no further action
     * with regard to the KeyEvent; false  otherwise
     */
    public boolean dispatchKeyEvent(final KeyEvent evt) {
        if (evt.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }

        boolean result = true;
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ASTERISK:
                qtVideoController.setCellOffsetAction();
                break;
            case KeyEvent.VK_NUMPAD7:
                qtVideoController.rewindAction();
                break;
            case KeyEvent.VK_NUMPAD8:
                qtVideoController.playAction();
                break;
            case KeyEvent.VK_NUMPAD9:
                qtVideoController.forwardAction();
                break;
/*            case KeyEvent.VK_MINUS:
                qtVideoController.goBackAction();
                break;
 */
            case KeyEvent.VK_NUMPAD4:
                qtVideoController.shuttleBackAction();
                break;
            case KeyEvent.VK_NUMPAD5:
                qtVideoController.pauseAction();
                break;
            case KeyEvent.VK_NUMPAD6:
                qtVideoController.shuttleForwardAction();
                break;
            case KeyEvent.VK_PLUS:
                qtVideoController.findAction();
                break;
            case KeyEvent.VK_NUMPAD1:
                qtVideoController.jogBackAction();
                break;
            case KeyEvent.VK_NUMPAD2:
                qtVideoController.stopAction();
                break;
            case KeyEvent.VK_NUMPAD3:
                qtVideoController.jogForwardAction();
                break;
            case KeyEvent.VK_NUMPAD0:
                qtVideoController.createNewCellAction();
                break;
            case KeyEvent.VK_ENTER:
                //this.createNewCell();
                new CreateNewCellC();
                break;
            default:
                // Do nothing with the key.
                result = false;
                break;
        }

        return result;
    }

    /**
     * Action for running a script.
     *
     * @param rubyFile The file of the ruby script to run.
     */
    public void runScript(final File rubyFile) {
        try {
            JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
            if (console == null) {
                console = new ScriptOutput(mainFrame,
                                                    false,
                                                    consoleOutputStream);
            }
            OpenSHAPA.getApplication().show(console);

            // Place a reference to the database within the scripting engine.
            rubyEngine.put("db", db);

            FileReader reader = new FileReader(rubyFile);
            rubyEngine.eval(reader);
        } catch (ScriptException e) {
            consoleWriter.println("***** SCRIPT ERRROR *****");
            consoleWriter.println("@Line " + e.getLineNumber() + ":'"
                                 + e.getMessage() + "'");
            consoleWriter.println("*************************");
            consoleWriter.flush();

            logger.error("Unable to execute script: ", e);
        } catch (FileNotFoundException e) {
            logger.error("Unable to execute script: ", e);
        }
    }

    /**
     * Action for showing the quicktime video controller.
     */
    public void showQTVideoController() {
        JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
        qtVideoController = new QTVideoController(mainFrame, false);
        OpenSHAPA.getApplication().show(qtVideoController);
    }

    /**
     * Action for showing the variable list.
     */
    public void showVariableList() {
        JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
        listVarView = new ListVariables(mainFrame, false, db);
        try {
            db.registerColumnListListener(listVarView);
        } catch (SystemErrorException e) {
            logger.error("Unable register column list listener: ", e);
        }
        OpenSHAPA.getApplication().show(listVarView);
    }

    /**
     * Action for showing the spreadsheet.
     */
    public void showSpreadsheet() {
        openshapaView.showSpreadsheet();         
    }

    /**
     * Show a warning dialog to the user.
     *
     * @param e The LogicErrorException to present to the user.
     */
    public void showWarningDialog(LogicErrorException e) {
        JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
        ResourceMap rMap = Application.getInstance(OpenSHAPA.class)
                                      .getContext()
                                      .getResourceMap(OpenSHAPA.class);

        JOptionPane.showMessageDialog(mainFrame,
                                      e.getMessage(),
                                      rMap.getString("WarningDialog.title"),
                                      JOptionPane.WARNING_MESSAGE);        
    }

    /**
     * Show a fatal error dialog to the user.
     *
     * @param e The SystemErrorException that caused this problem.
     */
    public void showErrorDialog() {
        JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
        ResourceMap rMap = Application.getInstance(OpenSHAPA.class)
                                      .getContext()
                                      .getResourceMap(OpenSHAPA.class);

        JOptionPane.showMessageDialog(mainFrame,
                                      rMap.getString("ErrorDialog.message"),
                                      rMap.getString("ErrorDialog.title"),
                                      JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Set the layout type for the spreadsheet.
     * @param type SheetLayoutType to set.
     */
    public void setSheetLayout(final SheetLayoutType type) {
        //spreadsheetView.setLayoutType(type);
    }

    /**
     * Creates a class and adds it to the supplied list of unit tests if it ends
     * with 'Test.class' and is concrete (i.e. not abstract).
     *
     * @param unitTests The list of unit tests that you wish to test.
     * @param className The name of the class that you wish to determine if it
     * is a test or not. A class is considered a Junit test if it ends with
     * 'Test.class' and is concrete (no abstract tests are created and invoked).
     * @throws java.lang.ClassNotFoundException If unable to build the class
     * of the unit test to invoke.
     */
    private static void addTest(Vector<Class> unitTests, final String className)
    throws ClassNotFoundException {
        String cName = className;

        if (cName.endsWith("Test.class")) {
            // Build the class for the found test.
            cName = cName.substring(0, cName.length() - ".class".length());
            cName = cName.replace('/', '.');
            Class test = Class.forName(cName);

            // If the class is not abstract - add it to the list of
            // tests to perform.
            if (!Modifier.isAbstract(test.getModifiers())) {
                unitTests.add(test);
            }
        }
        
    }

    /**
     * All regression tests (Junits) and present results to the user.
     */
    public void runRegressionTests() throws SystemErrorException {
        JFrame mainFrame = OpenSHAPA.getApplication().getMainFrame();
        if (console == null) {
            console = new ScriptOutput(mainFrame,
                                                false,
                                                consoleOutputStream);
        }
        OpenSHAPA.getApplication().show(console);

        // Build a list of unit tests to invoke.
        consoleWriter.println("Running OpenSHAPA unit tests:");
        consoleWriter.flush();
        Vector<Class> unitTests = new Vector<Class>();

        // Build the list of unitTests to perform.
        try {            
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL resource = loader.getResource("au/com/nicta/openshapa");
            if (resource == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }

            // The classloader references a jar - open the jar file up and
            // iterate through all the entries and add the entries that are
            // concrete unit tests and add them to our list of tests to perform.
            if (resource.getFile().contains(".jar!")) {
                String file = resource.getFile();
                file = file.substring(0, file.indexOf("!"));
                URI uri = new URI(file);
                File f = new File(uri);
                JarFile jar = new JarFile(f);

                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    addTest(unitTests, entries.nextElement().getName());
                }


            // The classloader references a bunch of .class files on disk,
            // recusively inspect contents of each of the resources. If it is
            // a directory at it to our workStack, otherwise check to see if it
            // is a concrete unit tests and add it to our list of tests to
            // perform.
            } else {
                Stack<File> workStack = new Stack<File>();
                workStack.push(new File(resource.getFile()));

                Stack<String> packages = new Stack<String>();
                packages.push("au.com.nicta.openshapa.");
                
                while (!workStack.empty()) {
                    File dir = workStack.pop();
                    String pkgName = packages.pop();

                    // For each of the children of the directory - look for
                    // tests or more directories to recurse inside.
                    String[] files = dir.list();
                    for (int i = 0; i < files.length; i++) {
                        File file = new File(dir.getAbsolutePath() + "/"
                                             + files[i]);
                        if (file == null) {
                            throw new ClassNotFoundException("Null file");
                        }

                        // If the file is a directory - add it to our work list.
                        if (file.isDirectory()) {
                            workStack.push(file);
                            packages.push(pkgName + file.getName() + ".");

                        // If the file ends with Test.class - it is a unit test,
                        // add it to our list of tests.
                        } else {
                            addTest(unitTests, pkgName.concat(files[i]));
                        }
                    }
                }
            }

        // Whoops - something went bad. Chuck a spaz.
        } catch (ClassNotFoundException e) {
            logger.error("Unable to build unit test", e);            
        } catch (IOException ie) {
            logger.error("Unable to load jar file", ie);
        } catch (URISyntaxException se) {
            logger.error("Unable to build path to jar file", se);
        }


        // With the list of tests built - for each of them, excute the test and
        // report to the user a bunch of notification information.
        float totalTime = 0.0f;
        int totalTests = 0;
        int totalFailures = 0;

        // Build the testing framework and invoke all the regression tests.
        JUnitCore core = new JUnitCore();
        for (int i = 0; i < unitTests.size(); i++) {
            Result results = core.run(unitTests.get(i));

            // Display results to user.            
            consoleWriter.println("\n******************************");
            consoleWriter.println("Running Test: "
                                  + unitTests.get(i).getName());

            float seconds = results.getRunCount() / 1000.0f;
            consoleWriter.println("Test Run time: " + seconds + "s");
            totalTime += seconds;
            
            consoleWriter.println("Tests Performed: "
                                  + results.getRunCount());
            totalTests += results.getRunCount();

            if (!results.wasSuccessful()) {
                consoleWriter.println("Tests failed: "
                                      + results.getFailureCount());
                totalFailures += results.getFailureCount();

                List<Failure> fails = results.getFailures();
                for (int j = 0; j < fails.size(); j++) {
                    consoleWriter.println("Failure: "
                                          + fails.get(j).getTestHeader());
                    consoleWriter.println(fails.get(j).getTrace());
                }
            }
            consoleWriter.flush();
        }

        consoleWriter.println("\n\n\n******************************");
        consoleWriter.println("         Test Summary");
        consoleWriter.println("******************************");
        consoleWriter.println("Total Time: " + totalTime + "s");
        consoleWriter.println("Total Tests: " + totalTests);
        consoleWriter.println("Test Failures: " + totalFailures);
        consoleWriter.flush();
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        try {
            // Initalise DB
            db = new MacshapaDatabase();

            // Initalise last created values
            lastCreatedCellID = 0;
            lastCreatedColID = 0;

            // Build the ruby scripting engine - we need to avoid using the
            // javax.script.ScriptEngineManager, so that OpenSHAPA can work in
            // java 1.5. Instead we use the JRubyScriptEngineManager BugzID: 236
            JRubyScriptEngineManager m = new JRubyScriptEngineManager();

            // Whoops - JRubyScriptEngineManager may have failed, if that does
            // not construct engines for jruby correctly, switch to
            // javax.script.ScriptEngineManager
            if (m.getEngineFactories().size() == 0) {
                ScriptEngineManager m2 = new ScriptEngineManager();
                rubyEngine = m2.getEngineByName("jruby");
            } else {
                rubyEngine = m.getEngineByName("jruby");
            }

            // Build output streams for the scripting engine.
            consoleOutputStream = new PipedInputStream();
            PipedOutputStream sIn = new PipedOutputStream(consoleOutputStream);
            consoleWriter = new PrintWriter(sIn);
            rubyEngine.getContext().setWriter(consoleWriter);

            console = null;
            //spreadsheetView = null;

            // TODO- BugzID:79 This needs to move above showSpreadsheet,
            // when setTicks is fully implemented.
            db.setTicks(Constants.TICKS_PER_SECOND);
        } catch (SystemErrorException e) {
            logger.error("Unable to create MacSHAPADatabase", e);
        } catch (IOException e) {
            logger.error("Unable to create scripting output streams", e);
        }

        openshapaView = new OpenSHAPAView(this);
        show(openshapaView);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     *
     * @return The instance of the OpenSHAPA application.
     */
    public static OpenSHAPA getApplication() {
        return Application.getInstance(OpenSHAPA.class);
    }

    /**
     * A convenient static getter for the application session storage.
     *
     * @return The SessionStorage for the OpenSHAPA application.
     */
    public static SessionStorage getSessionStorage() {
        return OpenSHAPA.getApplication().getContext().getSessionStorage();
    }

    /**
     * Gets the single instance database associated with the currently running
     * OpenSHAPA.
     *
     * @return The single database in use with this instance of OpenSHAPA
     */
    public static Database getDatabase() {
        return OpenSHAPA.getApplication().db;
    }

    /**
     * Sets the single instance of the database assocaited with the currently
     * running OpenSHAPA to the defined parameter.
     *
     * @param newDB The new database to use for this instance of OpenSHAPA.
     */
    public static void setDatabase(Database newDB) {
        OpenSHAPA.getApplication().db = newDB;
    }

    /**
     * @return The id of the last created cell.
     */
    public static long getLastCreatedCellId() {
        return OpenSHAPA.getApplication().lastCreatedCellID;
    }

    /**
     * Sets the id of the last created cell to the specified parameter.
     *
     * @param newId The Id of the newly created cell.
     */
    public static void setLastCreatedCellId(final long newId) {
        OpenSHAPA.getApplication().lastCreatedCellID = newId;
    }

    /**
     * @return The id of the last created column.
     */
    public static long getLastCreatedColId() {
        return OpenSHAPA.getApplication().lastCreatedColID;
    }

    /**
     * Sets the id of the last created column to the specified parameter.
     *
     * @param newId The Id of the newly created column.
     */
    public static void setLastCreatedColId(final long newId) {
        OpenSHAPA.getApplication().lastCreatedColID = newId;
    }

    /** All the supported platforms that OpenSHAPA runs on. */
    public enum Platform {MAC, WINDOWS, UNKNOWN};

    /**
     * @return The platform that OpenSHAPA is running on.
     */
    public static Platform getPlatform() {
        String os = System.getProperty("os.name");
        if (os.contains("Mac")) {
            return Platform.MAC;
        }

        if (os.contains("Win")) {
            return Platform.WINDOWS;
        }

        return Platform.UNKNOWN;
    }

    /**
     * Main method launching the application.
     *
     * @param args The command line arguments passed to OpenSHAPA.
     */
    public static void main(String[] args) {
        // Configure logger - and start logging things.
        PropertyConfigurator.configure("log4j.properties");
        logger.info("Starting OpenSHAPA.");

        // If we are running on a MAC set some additional properties:
        if (OpenSHAPA.getPlatform() == Platform.MAC) {
            try {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "OpenSHAPA");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException cnfe) {
                logger.error("Unable to start OpenSHAPA", cnfe);
            } catch (InstantiationException ie) {
                logger.error("Unable to start OpenSHAPA", ie);
            } catch (IllegalAccessException iae) {
                logger.error("Unable to start OpenSHAPA", iae);
            } catch (UnsupportedLookAndFeelException ulafe) {
                logger.error("Unable to start OpenSHAPA", ulafe);
            }
        }

        launch(OpenSHAPA.class, args);
    }

    /** The logger for OpenSHAPA. */
    private static Logger logger = Logger.getLogger(OpenSHAPA.class);

    /** The current database we are working on. */
    private Database db;

    /** Ruby scripting engine to use for this instance of openshapa. */
    private ScriptEngine rubyEngine;

    /** output stream for messages coming from the scripting engine. */
    private PipedInputStream consoleOutputStream;

    /** input stream for displaying messages from the scripting engine. */
    private PrintWriter consoleWriter;

    /** The current spreadsheet view. */
    //private Spreadsheet spreadsheetView;
    private OpenSHAPAView openshapaView;

    /** The id of the last datacell that was created. */
    private long lastCreatedCellID;

    /** The id of the last datacell that was created. */
    private long lastCreatedColID;

    /** The view to use when listing all variables in the database. */
    private ListVariables listVarView;

    /** The view to use for the quick time video controller. */
    private QTVideoController qtVideoController;

    /** The view to use when displaying the output of a user invoked script. */
    private ScriptOutput console;
}
