package org.openshapa.views.discrete;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import javax.swing.text.JTextComponent;

/**
 * EditorComponent - Abstract class for editing a segment of text within a
 * JTextComponent. Subclasses of this abstract class are combined and used by an
 * EditorTracker to manage editing of the JTextComponent.
 */
public abstract class EditorComponent {

    /** JTextComponent containing this EditorComponent. */
    private JTextComponent parentComp;

    /** Character position in the JTextComponent where this editor begins. */
    private int startPos;

    /** Local copy of this editor's text. */
    private String editorText;

    /** Is the editorComponent editable?  Used by EditorTracker. */
    private boolean editable;

    /** Does the editorComponent allow Return characters to be input? */
    private boolean acceptsReturnKey;

    /** A list of characters that can not be removed from this view. */
    private String preservedChars;

    /** Are we deleting characters, or replacing them with a substitute? */
    private boolean isDeletingChar;

    /** The character to use as a substitute if we are doing replacement. */
    private char replaceChar;

    /**
     * Action to invoke when a key is pressed.
     *
     * @param e The KeyEvent that triggered this action.
     */
    public abstract void keyPressed(final KeyEvent e);

    /**
     * Action to invoke when a key is typed.
     *
     * @param e The KeyEvent that triggered this action.
     */
    public abstract void keyTyped(final KeyEvent e);

    /**
     * Action to invoke when a key is released.
     *
     * @param e The KeyEvent that triggered this action.
     */
    public abstract void keyReleased(final KeyEvent e);

    /**
     * Action to invoke when focus is gained.
     *
     * @param fe The FocusEvent that triggered this action.
     */
    public abstract void focusGained(final FocusEvent fe);

    /**
     * Action to invoke when focus is lost.
     *
     * @param fe The FocusEvent that triggered this action.
     */
    public abstract void focusLost(final FocusEvent fe);

    /**
     * Default Constructor.
     */
    public EditorComponent() {
        startPos = 0;
        editorText = "";
        editable = false;
        parentComp = null;
        preservedChars = "";
        isDeletingChar = true;
    }

    /**
     * Constructor.
     *
     * @param tc JTextComponent this editor works with.
     */
    public EditorComponent(final JTextComponent tc) {
        this();
        parentComp = tc;
    }

    /**
     * Constructor.
     *
     * @param tc JTextComponent this editor works with.
     * @param text text to initialise the editor to.
     */
    public EditorComponent(final JTextComponent tc, final String text) {
        this(tc);
        editorText = text;
    }

    /**
     * @param canEdit set true if the editorcomponent is editable.
     */
    public final void setEditable(final boolean canEdit) {
        editable = canEdit;
    }

    /**
     * @return is the editorcomponent "editable".
     */
    public final boolean isEditable() {
        return editable;
    }

    /**
     * @param canAccept set true if the editorcomponent uses return character.
     */
    public final void setAcceptReturnKey(final boolean canAccept) {
        acceptsReturnKey = canAccept;
    }

    /**
     * @return is the return character used by this editor.
     */
    public final boolean isReturnKeyAccepted() {
        return acceptsReturnKey;
    }

    /**
     * @param pos the start location in the JTextComponent for this editor.
     */
    public final void setStartPos(final int pos) {
        startPos = pos;
    }

    /**
     * @return the current text of this editor.
     */
    public final String getText() {
        return editorText;
    }

    /**
     * Set the text without updating the associated JTextComponent.
     *
     * @param text new text to set.
     */
    public final void resetText(final String text) {
        editorText = text;
    }

    /**
     * Set the text of the editorcomponent and update the text segment of the
     * JTextComponent.
     *
     * @param text new text to set.
     */
    public final void setText(final String text) {
        int prevlength = editorText.length();
        editorText = text;
        int localPos = getCaretPosition();
        replaceRange(text, startPos, startPos + prevlength);
        setCaretPosition(localPos);
    }

    /**
     * Utility function for replacing a segment of a string with another.
     *
     * @param text new segment of text to set.
     * @param start start position of the new text.
     * @param end length of the segment being replaced.
     */
    private void replaceRange(final String text,
                              final int start,
                              final int end) {
        String fullText = parentComp.getText();
        parentComp.setText(fullText.substring(0, start) + text
                                                    + fullText.substring(end));
    }

    /**
     * @return the caret location within the text segment as a local value.
     */
    public int getCaretPosition() {
        int pos = Math.max(0, parentComp.getCaretPosition() - startPos);
        pos = Math.min(pos, editorText.length());
        return pos;
    }

    /**
     * @return the selection start within the segment as a local value.
     */
    public final int getSelectionStart() {
        int pos = Math.max(0, parentComp.getSelectionStart() - startPos);
        pos = Math.min(pos, editorText.length());
        return pos;
    }

    /**
     * @return the selection end within the segment as a local value.
     */
    public final int getSelectionEnd() {
        int pos = Math.max(0, parentComp.getSelectionEnd() - startPos);
        pos = Math.min(pos, editorText.length());
        return pos;
    }

    /**
     * Set the caret position of the parentComponent given a local value to
     * set within the editor.
     *
     * @param localPos Position of caret relative to the start of this editor.
     */
    public void setCaretPosition(final int localPos) {
        int pos = Math.max(0, localPos);
        pos = Math.min(pos, editorText.length());
        parentComp.setCaretPosition(startPos + pos);
    }

    /**
     * Select all of this segments text in the JTextComponent.
     */
    public void selectAll() {
        parentComp.select(startPos, startPos + editorText.length());
    }

    /**
     * Given a startClick position and endClick position, select the text in
     * the JTextComponent.
     *
     * @param startClick character position of the start of the click.
     * @param endClick character position of the end of the click.
     */
    public void select(final int startClick, final int endClick) {
        int start = Math.max(startPos, startClick);
        start = Math.min(startPos + editorText.length(), start);
        int end = Math.max(startPos, endClick);
        end = Math.min(startPos + editorText.length(), end);
        parentComp.setCaretPosition(start);
        parentComp.moveCaretPosition(end);
    }

    /**
     * Sanitize the text in the clipboard. Subclasses that check for
     * reserved chars override this method.
     *
     * @return true if it is okay to call the JTextComponent's paste command.
     */
    public boolean prePasteCheck() {
        // default version assumes it is okay
        return true;
    }

    /**
     * Rather than delete characters.
     *
     * @param c The character to use when deleting (rather than deleting - the
     * supplied character is used to replace).
     */
    public final void setDeleteChar(final char c) {
        isDeletingChar = false;
        replaceChar = c;
    }

    /**
     * Adds characters to the list that must be preserved by the editor
     * (characters that can not be deleted).
     *
     * @param pChars The characters to be preserved.
     */
    public final void addPreservedChars(final String pChars) {
        preservedChars = preservedChars.concat(pChars);
    }

    /**
     * @param aChar Character to test
     *
     * @return true if the character is a reserved character.
     */
    public final boolean isPreserved(final char aChar) {
        return (preservedChars.indexOf(aChar) >= 0);
    }

    /**
     * Removes characters from ahead of the caret if they are not in the
     * preservedChars parameter. If the character is to be preserved, this
     * method will simple shift the caret forward one spot.
     */
    public final void removeAheadOfCaret() {
        // Underlying text field has selection no caret, remove everything that
        // is selected.
        if ((getSelectionEnd() - getSelectionStart()) > 0) {
            removeSelectedText();

        // Underlying Text field has no selection, just a caret. Go ahead and
        // manipulate it as such.
        } else if (getText() != null && getText().length() > 0
                   && getCaretPosition() < getText().length()) {
            // Check ahead of caret to see if it is a preserved character. If
            // the character is preserved - simply move the caret ahead one spot
            // and leave the preserved character untouched.
            if (isPreserved(getText().charAt(getCaretPosition()))) {
                setCaretPosition(getCaretPosition() + 1);
            }

            // Delete next character.
            StringBuffer currentValue = new StringBuffer(getText());
            currentValue.deleteCharAt(getCaretPosition());

            if (!isDeletingChar) {
                currentValue.insert(getCaretPosition(), replaceChar);
            }

            int cPosition = getCaretPosition();
            this.setText(currentValue.toString());
            setCaretPosition(cPosition);
        }
    }

    /**
     * Removes characters from behind the caret if they are not in the
     * preservedChars parameter. If the character is to be preserved, this
     * method will simply shift the caret back one spot.
     */
    public final void removeBehindCaret() {
        // Underlying text field has selection and no carret, simply remove
        // everything that is selected.
        if ((getSelectionEnd() - getSelectionStart()) > 0) {
            removeSelectedText();

        // Underlying text field has no selection, just a caret. Go ahead and
        // manipulate it as such.
        } else if (getText() != null && getText().length() > 0) {
            // Check behind the caret to see if it is a preserved character. If
            // the character is preserved - simply move the caret back one spot
            // and leave the preserved character untouched.
            int carPosMinusOne = Math.max(0, getCaretPosition() - 1);
            if (isPreserved(getText().charAt(carPosMinusOne))) {
                setCaretPosition(carPosMinusOne);
                carPosMinusOne = Math.max(0, getCaretPosition() - 1);
            }

            // Delete previous character.
            StringBuffer currentValue = new StringBuffer(getText());
            currentValue.deleteCharAt(carPosMinusOne);
            if (!isDeletingChar) {
                currentValue.insert(carPosMinusOne, replaceChar);
            }

            int cPosition = carPosMinusOne;
            this.setText(currentValue.toString());
            setCaretPosition(cPosition);
        }
    }

    /**
     * This method will remove any characters that have been selected in the
     * underlying text field and that don't exist in the preservedChars
     * parameter. If no characters have been selected, the underlying text field
     * is unchanged.
     */
    public final void removeSelectedText() {
        // Get the current value of the visual representation of this DataValue.
        StringBuffer cValue = new StringBuffer(getText());

        // Obtain the start and finish of the selected text.
        int start = this.getSelectionStart();
        int end = this.getSelectionEnd();
        int pos = start;

        for (int i = start; i < end; i++) {

            // Current character is not reserved - either delete or replace it.
            if (!isPreserved(cValue.charAt(pos))) {
                cValue.deleteCharAt(pos);

                // Replace the character rather than remove it, we then need to
                // skip to the next position to delete a character.
                if (!isDeletingChar) {
                    cValue.insert(pos, replaceChar);
                    pos++;
                }

            // Current character is reserved, skip over current position.
            } else {
                pos++;
            }
        }

        // Set the text for this data value to the new string.
        this.setText(cValue.toString());
        this.setCaretPosition(start);
    }
}
