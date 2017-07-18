package org.datavyu.plugins.vlcfx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.datavyu.models.id.Identifier;
import org.datavyu.plugins.CustomActions;
import org.datavyu.plugins.CustomActionsAdapter;
import org.datavyu.plugins.ViewerStateListener;
import org.datavyu.plugins.BaseDataViewer;
import org.datavyu.views.component.DefaultTrackPainter;
import org.datavyu.views.component.TrackPainter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


public class VLCFXDataViewer extends BaseDataViewer {

    private static final float FALLBACK_FRAME_RATE = 24.0f;
    private static final String VIDEO =
            "file:///Users/jesse/Desktop/country_life_butter.mp4";
    /**
     * Data viewer ID.
     */
    private Identifier id;
    /**
     * Dialog for showing our visualizations.
     */
    private JDialog vlcDialog;
    /**
     * Data viewer offset.
     */
    private long offset;
    /**
     * Data to visualize.
     */
    private File data;
    /**
     * Boolean to keep track of whether or not we are playing
     */
    private boolean playing;
    /**
     * Data viewer state listeners.
     */
    private List<ViewerStateListener> stateListeners;
    /**
     * Action button for demo purposes.
     */
    private JButton sampleButton;
    /**
     * Supported custom actions.
     */
    private CustomActions actions = new CustomActionsAdapter() {
        @Override
        public AbstractButton getActionButton1() {
            return sampleButton;
        }
    };
    /**
     * Surface on which we will display video
     */
    private Canvas videoSurface;
    /**
     * FPS of the video, calculated on launch
     */
    private float fps;
    /**
     * Length of the video, calculated on launch
     */
    private long length;

    /**
     * The last jog position, making sure we are only calling jog once
     * VLC has issues when trying to go to the same spot multiple times
     */
    private long last_position;
    private Thread vlcThread;
    private JDialog dialog = new JDialog();
    private VLCApplication vlcFxApp;
    private boolean assumedFPS = false;

    public VLCFXDataViewer(final Frame parent, final boolean modal) {
        super(parent, modal);
//        stateListeners = new ArrayList<ViewerStateListener>();
        dialog.setVisible(false);
    }

    public static void runAndWait(final Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    action.run();
                } finally {
                    doneLatch.countDown();
                }
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            // ignore exception
        }
    }

    protected void setPlayerVolume(float volume) {
        vlcFxApp.setVolume(volume);
    }

    private void launchEdtTaskNow(Runnable edtTask) {
        if (SwingUtilities.isEventDispatchThread()) {
            edtTask.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(edtTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void launchEdtTaskLater(Runnable edtTask) {
        if (SwingUtilities.isEventDispatchThread()) {
            edtTask.run();
        } else {
            try {
                SwingUtilities.invokeLater(edtTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JDialog getParentJDialog() {
        return dialog;
    }

    @Override
    protected void setPlayerSourceFile(File videoFile) {

    }

    @Override
    protected Dimension getOriginalVideoSize() {
        return null;
    }

    @Override
    protected float getPlayerFramesPerSecond() {
        return getFramesPerSecond();
    }

    @Override
    public float getFramesPerSecond() {
        return vlcFxApp.getFrameRate();
    }

    public void setFramesPerSecond(float fpsIn) {
        fps = fpsIn;
        assumedFPS = false;
    }

    @Override
    public float getDetectedFrameRate() {
        return 30;
    }

    @Override
    public Identifier getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(final Identifier id) {
        this.id = id;
    }

    @Override
    public long getStartTime() {
        return offset;
    }

    @Override
    public void setStartTime(final long offset) {
        this.offset = offset;
    }

    @Override
    public TrackPainter getTrackPainter() {
        return new DefaultTrackPainter();
    }

    @Override
    public void setDataViewerVisible(final boolean isVisible) {
        vlcFxApp.setVisible(isVisible);
    }

    @Override
    public void setVisible(final boolean isVisible) {
        vlcFxApp.setVisible(isVisible);
    }

    @Override
    public File getSourceFile() {
        return data;
    }

    @Override
    public void setSourceFile(final File sourceFile) {
        data = sourceFile;


        // Needed to init JavaFX stuff
        new JFXPanel();
        vlcFxApp = new VLCApplication(sourceFile);

        runAndWait(new Runnable() {
            @Override
            public void run() {
                vlcFxApp.start(new Stage());
            }
        });


        // Wait for javafx to initialize
        while (!vlcFxApp.isInit()) {
        }

        // Hide our fake dialog box
        dialog.setVisible(false);

        // TODO Add in function to guess framerate
    }

    @Override
    public long getDuration() {
//        System.out.println("DURATION: " + vlcFxApp.getDuration());
        return vlcFxApp.getDuration();
    }

    @Override
    public long getCurrentTime() {
        return vlcFxApp.getCurrentTime();
    }

    @Override
    public void seek(final long position) {
//        System.out.println("SEEKING TO " + position);

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
        vlcFxApp.seek(position);
//            }
//        });

    }

    @Override
    public boolean isPlaying() {
        return vlcFxApp.isPlaying();
    }

    @Override
    public void stop() {
        playing = false;

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
        vlcFxApp.pause();
//            }
//        });
    }

    @Override
    public void setPlaybackSpeed(final float rate) {
        vlcFxApp.setRate(rate);
    }

    @Override
    public void play() {
        playing = true;

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
        vlcFxApp.play();
//            }
//        });
    }

    @Override
    protected void cleanUp() {
        clearSourceFile();

    }

    @Override
    public void clearSourceFile() {
        stop();
        vlcFxApp.setVisible(false);
        vlcFxApp.closeAndDestroy();
    }

    public boolean isAssumedFramesPerSecond() {
        return vlcFxApp.isAssumedFps();
    }

    @Override
    public void storeSettings(final OutputStream os) {
        try {
            Properties settings = new Properties();
            settings.setProperty("offset", Long.toString(getStartTime()));
            settings.setProperty("volume", Float.toString(getVolume()));
            settings.setProperty("visible", Boolean.toString(vlcFxApp.isVisible()));
            settings.setProperty("height", Integer.toString(vlcFxApp.getHeight()));
            settings.setProperty("fps", Float.toString(vlcFxApp.getFrameRate()));

            settings.store(os, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
