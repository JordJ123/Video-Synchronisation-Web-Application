package server.base.database.exception;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Represents a database exception.
 */
public class DatabaseException extends Exception {

    //CONSTANTS
    public static final SQLError DUPLICATE_COLUMN
        = new SQLError(1060, "42S21", "Duplicate column name");
    public static final SQLError DUPLICATE_VALUE
        = new SQLError(1062, "23000", "Duplicate value for a unique column");
    private static final SQLError INCORRECT_LOGIN
        = new SQLError(1045, "28000", "Username and/or password is incorrect");
    private static final SQLError OFFLINE
        = new SQLError(0, "08S01", "Database server is offline");
    public static final SQLError UNKNOWN_DATABASE
        = new SQLError(1049, "42000", "Database does not exist");
    public static final SQLError UNKNOWN_TABLE
        = new SQLError(1146, "42S02", "Table does not exist");
    public static final SQLError UNKNOWN_COLUMN
        = new SQLError(1054, "42S22", "Column does not exist");
    private static final ArrayList<SQLError> ERROR_MAP = new ArrayList<>();

    /**
     * Creates a database exception.
     * @param sqlException Equivalent sqlException
     * @throws DatabaseException Throws if there is no equivalent message
     */
    public DatabaseException(SQLException sqlException)
        throws DatabaseException {
        super(getErrorMessage(sqlException));
        if (getMessage().equals("")) {
            throw new DatabaseException(new SQLError(sqlException));
        }
    }

    /**
     * Creates a database exception with just an sql state code.
     * @param sqlError SQL state code
     */
    private DatabaseException(SQLError sqlError) {
        super(sqlError.toString());
    }

    /**
     * Sets the map of states and their corresponding messages;
     */
    static {
        ERROR_MAP.add(DUPLICATE_COLUMN);
        ERROR_MAP.add(DUPLICATE_VALUE);
        ERROR_MAP.add(INCORRECT_LOGIN);
        ERROR_MAP.add(OFFLINE);
        ERROR_MAP.add(UNKNOWN_DATABASE);
        ERROR_MAP.add(UNKNOWN_TABLE);
        ERROR_MAP.add(UNKNOWN_COLUMN);
    }

    /**
     * Gets the corresponding error message for the sql error.
     * @param sqlException Equivalent sqlException
     * @return Corresponding error message for the sql error
     */
    private static String getErrorMessage(SQLException sqlException) {
        SQLError sqlError = new SQLError(sqlException);
        int sqlErrorIndex = ERROR_MAP.indexOf(sqlError);
        if (sqlErrorIndex != -1) {
            return ERROR_MAP.get(sqlErrorIndex).getErrorMessage();
        } else {
            return "";
        }
    }

}
