package au.com.nicta.openshapa.controllers;

import au.com.nicta.openshapa.OpenSHAPA;
import au.com.nicta.openshapa.db.DataCell;
import au.com.nicta.openshapa.db.Database;
import au.com.nicta.openshapa.db.SystemErrorException;
import au.com.nicta.openshapa.db.TimeStamp;
import au.com.nicta.openshapa.util.Constants;
import org.apache.log4j.Logger;

/**
 * Controller for setting the stop time (offset) of a new cell.
 *
 * @author cfreeman (refactored into seperate controller class.)
 * @author switcher (logic of controller - pulled from spreadsheet panel.)
 */
public final class SetNewCellStopTimeC {

    /**
     * Sets the stop time of the last cell that was created.
     *
     * @param milliseconds The number of milliseconds since the origin of the
     * spreadsheet to set the stop time for.
     */
    public SetNewCellStopTimeC(final long milliseconds) {
        try {
            Database model = OpenSHAPA.getDatabase();

            DataCell cell = (DataCell) model.getCell(OpenSHAPA
                                                       .getLastCreatedCellId());
            cell.setOffset(new TimeStamp(Constants.TICKS_PER_SECOND,
                                         milliseconds));
            model.replaceCell(cell);
        } catch (SystemErrorException e) {
            logger.error("Unable to set new cell stop time.", e);
        }
    }

    /** The logger for this class. */
    private static Logger logger = Logger.getLogger(SetNewCellStopTimeC.class);
}
