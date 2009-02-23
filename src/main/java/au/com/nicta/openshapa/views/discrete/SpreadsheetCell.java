package au.com.nicta.openshapa.views.discrete;

import au.com.nicta.openshapa.OpenSHAPA;
import au.com.nicta.openshapa.db.Cell;
import au.com.nicta.openshapa.db.DataCell;
import au.com.nicta.openshapa.db.Database;
import au.com.nicta.openshapa.db.ExternalDataCellListener;
import au.com.nicta.openshapa.db.IntDataValue;
import au.com.nicta.openshapa.db.Matrix;
import au.com.nicta.openshapa.db.ReferenceCell;
import au.com.nicta.openshapa.db.SystemErrorException;
import au.com.nicta.openshapa.db.TimeStamp;
import au.com.nicta.openshapa.db.TimeStampDataValue;
import au.com.nicta.openshapa.util.UIConfiguration;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import org.apache.log4j.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author  felix
 */
public class SpreadsheetCell extends SpreadsheetPanel
implements ExternalDataCellListener, Selectable {

    /** A panel for holding the header to the cell. */
    private SpreadsheetPanel topPanel;

    /** A panel for holding the value of the cell. */
    private MatrixViewLabel dataPanel;

    /** The Ordinal display component. */
    private DataValueView ord;

    /** The Onset display component. */
    private OnsetView onset;

    /** The Offset display component. */
    private OffsetView offset;

    /** The Database the cell belongs to. */
    private Database db;

    /** The data cell that this view represents. */
    private DataCell dc;

    /** The cellID for retrieving the cell from the database. */
    private long cellID;

    /** selected state of cell. */
    private boolean selected = false;

    /** Stores the current user dimensions. */
    private Dimension userDimensions = new Dimension(0, 0);

    /** Default height multiplier of spreadsheet cell. */
    public static final int HEIGHT_MULT = 50;

    /** Boolean to flag if size is changed. Not Used. */
    private boolean temporalSizeChanged = false;

    /** Show/Hide the Ordinal value of the cell. */
    private boolean showOrd = true;

    /** Show/Hide the Onset value of the cell. */
    private boolean showOnset = true;

    /** Show/Hide the Offset value of the cell. */
    private boolean showOffset = true;

    /** Show/Hide the Data value of the cell. */
    private boolean showData = true;

    /** The parent selection that could include this cell. */
    private Selector selection;

    /** Logger for this class. */
    private static Logger logger = Logger.getLogger(SpreadsheetCell.class);

    /**
     * Creates new form SpreadsheetCell.
     * @param db Database the cell is in
     * @param cell Cell to display
     * @param selector Selector to register the cell with.
     * @throws SystemErrorException if trouble with db calls
     */
    public SpreadsheetCell(final Database cellDB,
                           final Cell cell,
                           final Selector selector)
    throws SystemErrorException {
        db = cellDB;
        cellID = cell.getID();
        selection = selector;
        setName(this.getClass().getSimpleName());

        ResourceMap rMap = Application.getInstance(OpenSHAPA.class)
                                      .getContext()
                                      .getResourceMap(SpreadsheetCell.class);

        // Register this view with the database so that we can get updates when
        // the cell within the database changes.
        if (cell instanceof DataCell) {
            dc = (DataCell)cell;
        } else {
            dc = (DataCell)db.getCell(((ReferenceCell)cell).getTargetID());
        }
        db.registerDataCellListener(dc.getID(), this);

        // Build components used for the spreadsheet cell.
        topPanel = new SpreadsheetPanel();
        ord = new IntDataValueView(selection,
                                   dc,
                                   new IntDataValue(cellDB),
                                   false);
        ord.setFocusable(false);
        ord.setToolTipText(rMap.getString("ord.tooltip"));
        setOrdinal(dc.getOrd());

        onset = new OnsetView(selection,
                           dc,
                           new TimeStampDataValue(cellDB),
                           true);
        onset.setToolTipText(rMap.getString("onset.tooltip"));
        onset.setValue(dc.getOnset());        

        offset = new OffsetView(selection,
                                dc,
                                new TimeStampDataValue(cellDB),
                                true);
        offset.setToolTipText(rMap.getString("offset.tooltip"));
        offset.setValue(dc.getOffset());  

        dataPanel = new MatrixViewLabel(selection, dc, null);
        dataPanel.setMatrix(dc.getVal());
        
        // Set the appearance of the spreadsheet cell.
        setBackground(java.awt.SystemColor.window);
        setBorder(javax.swing.BorderFactory
                       .createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.BorderLayout());

        // Set the apperance of the top panel and add child elements (ord, onset
        // and offset).
        topPanel.setBackground(java.awt.SystemColor.window);
        topPanel.setLayout(new java.awt.GridLayout(1, 3, 5, 0));
        add(topPanel, java.awt.BorderLayout.NORTH);
        topPanel.add(ord);
        topPanel.add(onset);
        topPanel.add(offset);

        // Set the apperance of the data panel - add elements for displaying the
        // actual data of the panel.
        dataPanel.setBackground(java.awt.SystemColor.window);
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
        add(dataPanel, java.awt.BorderLayout.WEST);
        
        this.updateDimensions();
    }

    public long getCellID() {
        return cellID;
    }

    /**
     * Set the ordinal value.
     *
     * @param ord The new ordinal value to use with this cell.
     * @deprecated The underlying IntDataValue should be altered, not the view.
     */
    private void setOrdinal(int ord) {
        ((IntDataValue)this.ord.getValue()).setItsValue(ord);
        this.ord.updateStrings();
        this.validate();
    }

    /**
     * @return Return the Ordinal value of the datacell as an IntDataValue.
     */
    public IntDataValue getOrdinal() {
        return ((IntDataValue)this.ord.getValue());
    }

    /**
     * Set the size of the SpreadsheetCell. Keeps a record in UserDimensions.
     * @param width New width of the SpreadsheetCell.
     * @param height New height of the SpreadsheetCell.
     */
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        dataPanel.setSize(width, dataPanel.getHeight());
        userDimensions = new Dimension(width, height);
    }

    /**
     * Set the width of the SpreadsheetCell.
     * @param width New width of the SpreadsheetCell.
     */
    public void setWidth(int width) {
        this.setSize(width, this.getHeight());
    }

    /**
     * Set the height of the SpreadsheetCell.
     * @param height New height of the SpreadsheetCell.
     */
    public void setHeight(int height) {
        this.setSize(this.getWidth(), height);
    }

    /**
     * @return The Minimum height of the SpreadsheetCell.
     */
    public int getMinimumHeight() {
        FontMetrics fm =
                this.getFontMetrics(UIConfiguration.spreadsheetTimeStampFont);
        FontMetrics fm1 =
                this.getFontMetrics(UIConfiguration.spreadsheetDataFont);
        return(fm.getHeight() + fm1.getHeight());
    }

    /**
     * @return The Preferred size of the SpreadsheetCell.
     */
    @Override
    public Dimension getPreferredSize() {
        if ((this.userDimensions.width > 0)
                && (this.userDimensions.height > 0)) {
            return (this.userDimensions);
        }

        return (super.getPreferredSize());
    }

    /**
     * @return Minimum size of the SpreadsheetCell.
     */
    @Override
    public Dimension getMinimumSize() {
        if ((this.userDimensions.width > 0)
                && (this.userDimensions.height > 0)) {
        return (this.userDimensions);
        }

        return (super.getMinimumSize());
    }

    /**
     * @return Maximum Size of the SpreadsheetCell.
     */
    @Override
    public Dimension getMaximumSize()
    {
        if ((this.userDimensions.width > 0)
                && (this.userDimensions.height > 0)) {
            return (this.userDimensions);
        }

        return (super.getMaximumSize());
    }

    /**
     * Calculate the dimensions of the SpreadsheetCell. Based on the size and
     * font face, calculate the size required for the cell.
     */
    public void updateDimensions() {
        Rectangle r = this.getBounds();
        FontMetrics fm =
                this.getFontMetrics(UIConfiguration.spreadsheetTimeStampFont);

        int totalWidth = 4;
        int totalHeight = 4;

        totalWidth += this.ord.getMinimumSize().width;
        totalWidth += this.onset.getMinimumSize().width;
        totalWidth += this.offset.getMinimumSize().width;

        if (showOrd || showOnset || showOffset) {
            totalHeight += fm.getHeight();
        }
        this.ord.setVisible(showOrd);
        this.onset.setVisible(showOnset);
        this.offset.setVisible(showOffset);

        if ((this.userDimensions.width > 0)
                && (this.userDimensions.height > 0)) {
            totalWidth = this.userDimensions.width;
            totalHeight = this.userDimensions.height;
        }

        //this.dataPanel.setWrapWidth(totalWidth - 4);
        Dimension d = this.dataPanel.getMaximumSize();
        if (showData) {
            totalHeight += d.getHeight();
        }
        this.dataPanel.setVisible(showData);
    }

    /**
     * Paint the SpreadsheetCell.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        this.updateDimensions();
        super.paintComponent(g);
    }

    /**
     * Set this cell as selected, a selected cell has a different appearance to
     * an unselcted one (typically colour).
     *
     * @param sel The selection state of the cell, when true the cell is
     * selected false otherwise.
     */
    public void setSelected(boolean sel) {
        selected = sel;

        // Set the selection within the database.
        try {
            Cell cell = db.getCell(this.cellID);
            DataCell dc = null;
            if (cell instanceof DataCell) {
                dc = (DataCell)db.getCell(cell.getID());
            } else {
                dc = (DataCell)db.getCell(((ReferenceCell)cell).getTargetID());
            }
            dc.setSelected(selected);
            cell.getDB().replaceCell(dc);
        } catch (SystemErrorException e) {
           logger.error("Failed clicking on SpreadsheetCell.", e);
        }

        // Update the visual representation of the SpreadsheetCell.
        if (selected) {
            topPanel.setBackground(UIConfiguration.spreadsheetSelectedColor);
            dataPanel.setBackground(UIConfiguration.spreadsheetSelectedColor);
            setBackground(UIConfiguration.spreadsheetSelectedColor);
        } else {
            topPanel.setBackground(UIConfiguration.spreadsheetBackgroundColor);
            dataPanel.setBackground(UIConfiguration.spreadsheetBackgroundColor);
            setBackground(UIConfiguration.spreadsheetBackgroundColor);
        }

        repaint();
    }

    /**
     * @return True if the cell is selected, false otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Called if the DataCell of interest is changed.
     * see ExternalDataCellListener.
     */
    public void DCellChanged(Database db,
                             long colID,
                             long cellID,
                             boolean ordChanged,
                             int oldOrd,
                             int newOrd,
                             boolean onsetChanged,
                             TimeStamp oldOnset,
                             TimeStamp newOnset,
                             boolean offsetChanged,
                             TimeStamp oldOffset,
                             TimeStamp newOffset,
                             boolean valChanged,
                             Matrix oldVal,
                             Matrix newVal,
                             boolean selectedChanged,
                             boolean oldSelected,
                             boolean newSelected,
                             boolean commentChanged,
                             String oldComment,
                             String newComment) {

        if (ordChanged) {
            this.setOrdinal(newOrd);
        }

        if (onsetChanged) {
            onset.setValue(newOnset);
        }

        if (offsetChanged) {
            onset.setValue(newOffset);
        }

        if (valChanged) {
            dataPanel.setMatrix(newVal);
        }

        if (selectedChanged) {
            this.selected = newSelected;
        }

        this.revalidate();
    }

    /**
     * Called if the DataCell of interest is deleted.
     */
    public void DCellDeleted(Database db,
                             long colID,
                             long cellID) {
        // TODO: Figure out how to work with cells that are deleted.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        selection.addToSelection(me, this);
        me.consume();
    }

    /**
     * @return True if this matrix view is the current focus owner, false
     * otherwise.
     */
    @Override
    public boolean isFocusOwner() {
        return (this.onset.isFocusOwner() || this.offset.isFocusOwner()
                || this.dataPanel.isFocusOwner());
    }

    /**
     * Request to focus this matrix view label, focus will be set to the first
     * element in the formal argument list.
     */
    @Override
    public void requestFocus() {
        this.dataPanel.requestFocus();
    }
}
