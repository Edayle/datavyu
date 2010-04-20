package org.fest.swing.fixture;

import java.awt.MouseInfo;
import static org.fest.reflect.core.Reflection.field;

import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.fest.swing.core.MouseButton;
import org.fest.swing.core.Robot;
import org.openshapa.controllers.component.TrackController;
import org.openshapa.util.UIUtils;
import org.openshapa.views.component.TrackPainter;

/**
 * Fixture for OpenSHAPA TrackController.
 */
public class TrackFixture extends ComponentFixture {
    /** The underlying mixercontroller. */
    private TrackController trackC;

    /**
     * Constructor.
     * @param robot mainframe robot
     * @param target TracksEditorController
     */
    public TrackFixture(final Robot robot, final TrackController target) {
        super(robot, (JComponent) field("trackPainter")
                .ofType(TrackPainter.class).in(target).get());
        trackC = target;
    }

    /**
     * @return Duration of track as a long.
     */
    public long getDurationTimeAsLong() {
        return trackC.getDuration();
    }

    /**
     * @return Duration of track as a timestamp.
     */
    public String getDurationTimeAsTimeStamp() {
        return UIUtils.millisecondsToTimestamp(getDurationTimeAsLong());
    }

    /**
     * @return Offset of track as a long.
     */
    public long getOffsetTimeAsLong() {
        return trackC.getOffset();
    }

    /**
     * @return Offset of track as a timestamp.
     */
    public String getOffsetTimeAsTimeStamp() {
        return UIUtils.millisecondsToTimestamp(getOffsetTimeAsLong());
    }

    /**
     * @return Offset of track as a long.
     */
    public long getBookmarkTimeAsLong() {
        return trackC.getBookmark();
    }

    /**
     * @return Offset of track as a timestamp.
     */
    public String getBookmarkTimeAsTimeStamp() {
        return UIUtils.millisecondsToTimestamp(getBookmarkTimeAsLong());
    }

    /**
     * @return true if track is selected.
     */
    public boolean isSelected() {
        return trackC.isSelected();
    }

    /**
     * @return true if track is selected.
     */
    public boolean isLocked() {
        return trackC.isLocked();
    }

    /**
     * Press the lock/unlock button.
     */
    public void pressLockButton() {
        new JButtonFixture(robot, "lockUnlockButton").click();
    }

    /**
     * @return track label
     */
    public String getTrackName() {
        return new JLabelFixture(robot, "trackLabel").text();
    }

    public JPanelFixture getTrackPanel() {
        return new JPanelFixture(robot, (JPanel) trackC.getView());
    }

    /**
     * Drag number of pixels left (negative) or right (positive)
     * @param pixels
     */
    public void drag(final int pixels) {
        dragWithoutReleasing(pixels);

        // Release mouse
        releaseLeftMouse();
    }

    /**
     * Drag number of pixels left (negative) or right (positive) without
     * releasing the mouse.
     * @param pixels
     */
    public void dragWithoutReleasing(final int pixels) {
        // Hold down left mouse button
        // Start position should leave enough room to move pixels
        Point to = null;
        if (!robot.isDragging()) {
            //Point topLeft = ((TrackPainter) target).getLocationOnScreen();
            Point carriagePoint = ((TrackPainter) target).getCarriagePolygon()
                    .getBounds().getLocation();
            Point topLeft = new Point(
                    ((TrackPainter) target).getLocationOnScreen().x
                    + carriagePoint.x,
                    ((TrackPainter) target).getLocationOnScreen().y
                    + carriagePoint.y);
            Point startClick;
            if (pixels >= 0) {
                startClick = new Point(topLeft.x + 5,
                        (topLeft.y + ((TrackPainter) target).getHeight() / 2)
                        + 5);
            } else {
                startClick =
                        new Point(topLeft.x + ((TrackPainter) target).getWidth()
                        - carriagePoint.x - 5,
                        (topLeft.y
                        + ((TrackPainter) target).getHeight() / 2) - 5);
            }
            robot.pressMouse(startClick, MouseButton.LEFT_BUTTON);

            // Move mouse to new position
            to = new Point(startClick.x + pixels, startClick.y);
        }
        to = new Point(MouseInfo.getPointerInfo().getLocation().x + pixels,
                MouseInfo.getPointerInfo().getLocation().y);
        robot.moveMouse(to);
    }

    /**
     * Releases the left mouse button that was used for dragging.
     */
    public void releaseLeftMouse() {
        robot.releaseMouse(MouseButton.LEFT_BUTTON);
    }

    /**
     * @return int width of track in pixels
     */
    public int getWidthInPixels() {
        return trackC.getView().getWidth();
    }

    /**
     * Clicks on the track to select or deselect.
     */
    public void click() {
        robot.click(target);
    }

    /**
     * Right click and show pop up menu.
     * @return JPopMenuFixture for the pop up menu
     */
    public JPopupMenuFixture showPopUpMenu() {
        robot.click(target, MouseButton.RIGHT_BUTTON);

        return new JPopupMenuFixture(robot, field("menu")
                .ofType(JPopupMenu.class).in(trackC).get());
    }

}