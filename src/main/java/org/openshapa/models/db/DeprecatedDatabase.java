package org.openshapa.models.db;

import com.usermetrix.jclient.Logger;
import com.usermetrix.jclient.UserMetrix;
import java.util.ArrayList;
import java.util.List;
import org.openshapa.models.db.legacy.DataColumn;
import org.openshapa.models.db.legacy.MacshapaDatabase;
import org.openshapa.models.db.legacy.SystemErrorException;
import org.openshapa.util.Constants;

/**
 * Converts legacy database calls into newer datastore calls.
 */
public class DeprecatedDatabase implements Datastore {

    /** The logger for this class. */
    private static Logger LOGGER = UserMetrix.getLogger(DeprecatedDatabase.class);

    /** The legacy database that this datastore represents. */
    private MacshapaDatabase legacyDB;

    /**
     * Default constructor.
     */
    public DeprecatedDatabase() {
        try {
            legacyDB = new MacshapaDatabase(Constants.TICKS_PER_SECOND);
            // BugzID:449 - Set default database name.
            legacyDB.setName("Database1");
        } catch (SystemErrorException e) {
            LOGGER.error("Unable to create new database", e);
        }
    }

    /**
     * @return The legacy database that this datastore represents.
     *
     * @deprecated Should use methods defined in datastore interface rather than
     * the db.legacy package.
     */
    @Deprecated public MacshapaDatabase getDatabase() {
        return legacyDB;
    }

    /**
     * Sets the legacy database that this datastore represents.
     *
     * @param newDB The new legacy databsae that this datastore represents.
     *
     * @deprecated Should use methods defined in datastore interface rather than
     * the db.legacy package.
     */
    @Deprecated public void setDatabase(MacshapaDatabase newDB) {
        legacyDB = newDB;
    }

    @Override public List<Variable> getAllVariables() {
        List<Variable> result = new ArrayList<Variable>();
        try {
            for (DataColumn dc : legacyDB.getDataColumns()) {
                if (dc == null) {
                    System.out.println("Datacolumn was null");
                    continue;
                }
                result.add(new DeprecatedVariable(dc));
            }
        } catch (SystemErrorException ex) {
            LOGGER.error("System prevented database access", ex);
        }

        return result;
    }
}
