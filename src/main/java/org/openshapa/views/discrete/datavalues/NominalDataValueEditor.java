package org.openshapa.views.discrete.datavalues;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import org.openshapa.db.DataCell;
import org.openshapa.db.Matrix;
import java.awt.event.KeyEvent;
import javax.swing.text.JTextComponent;
import org.apache.log4j.Logger;
import org.openshapa.db.NominalDataValue;
import org.openshapa.db.PredDataValue;
import org.openshapa.db.SystemErrorException;

/**
 * This class is the character editor of a NominalDataValue.
 */
public final class NominalDataValueEditor extends DataValueEditor {

    /**
     * String holding the reserved characters - these are characters that are
     * users are unable to enter into a nominal field.
     */
    private static final String NOMINAL_RESERVED_CHARS = ")(<>|,;\t\r\n\"";

    /**
     * The reserved replacement is a character that replaces reserved
     * characters pasted into nominal views.
     */
    private static final Character RESERVED_REPLACEMENT = '_';

    /** The logger for this class. */
    private static Logger logger = Logger
                                   .getLogger(NominalDataValueEditor.class);

    /**
     * Constructor.
     *
     * @param ta The parent JTextComponent the editor is in.
     * @param cell The parent data cell this editor resides within.
     * @param matrix Matrix holding the datavalue this editor will represent.
     * @param matrixIndex The index of the datavalue within the matrix.
     */
    public NominalDataValueEditor(final JTextComponent ta,
                                  final DataCell cell,
                                  final Matrix matrix,
                                  final int matrixIndex) {
        super(ta, cell, matrix, matrixIndex);
    }

    /**
     * Constructor.
     *
     * @param ta The parent JTextComponent the editor is in.
     * @param cell The parent data cell this editor resides within.
     * @param p The predicate holding the datavalue this editor will represent.
     * @param pi The index of the datavalue within the predicate.
     * @param matrix Matrix holding the datavalue this editor will represent.
     * @param matrixIndex The index of the datavalue within the matrix.
     */
    public NominalDataValueEditor(final JTextComponent ta,
                                  final DataCell cell,
                                  final PredDataValue p,
                                  final int pi,
                                  final Matrix matrix,
                                  final int matrixIndex) {
        super(ta, cell, p, pi, matrix, matrixIndex);
    }

    /**
     * The action to invoke when a key is typed.
     *
     * @param e The KeyEvent that triggered this action.
     */
    @Override
    public void keyTyped(final KeyEvent e) {
        super.keyTyped(e);

        // Just a regular vanilla keystroke - insert it into nominal field.
        NominalDataValue ndv = (NominalDataValue) getModel();
        if (!e.isConsumed() && !e.isMetaDown() && !e.isControlDown()
            && !isReserved(e.getKeyChar())) {
            this.removeSelectedText();
            StringBuffer currentValue = new StringBuffer(getText());
            currentValue.insert(getCaretPosition(), e.getKeyChar());

            // Advance caret over the top of the new char.
            int pos = this.getCaretPosition() + 1;
            this.setText(currentValue.toString());
            this.setCaretPosition(pos);
            e.consume();

        // All other keystrokes are consumed.
        } else {
            e.consume();
        }

        // Push the character changes into the database.
        try {
            ndv.setItsValue(this.getText());
            updateDatabase();
        } catch (SystemErrorException se) {
            logger.error("Unable to edit text string", se);
        }
    }

    /**
     * @param aChar Character to test
     * @return true if the character is a reserved character.
     */
    public boolean isReserved(final char aChar) {
        return (NOMINAL_RESERVED_CHARS.indexOf(aChar) >= 0);
    }

    /**
     * Sanitize the text in the clipboard.
     * @return true if it is okay to call the JTextComponent's paste command.
     */
    @Override
    public boolean prePasteCheck() {
        // Get the contents of the clipboard.
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasText = (contents != null)
                 && contents.isDataFlavorSupported(DataFlavor.stringFlavor);

        // No valid text in clipboard. Bail.
        if (!hasText) {
            return false;
        }

        // Valid text in clipboard
        try {
            // Get the text
            String text = (String) contents
                                  .getTransferData(DataFlavor.stringFlavor);

            // Replace reserved characters with a suitable replacement.
            for (Character reservedCh : NOMINAL_RESERVED_CHARS.toCharArray()) {
                text = text.replace(reservedCh, RESERVED_REPLACEMENT);
            }

            // Put the modified text back into the clipboard
            Transferable transferableText = new StringSelection(text);
            clipboard.setContents(transferableText, null);
        } catch (Exception ex) {
            logger.error("Unable to get clipboard contents", ex);
            return false;
        }
        return true;
    }
}