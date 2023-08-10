package server.base.database.exception;

import java.sql.SQLException;
import java.util.Objects;

public class SQLError {

    //CONSTANTS
    private static final String TO_STRING = "Error Code = %s, SQL State = %s";

    //Attributes
    private int errorCode;
    private String sqlState;
    private String errorMessage = "";

    /**
     * Creates a sql error for a message to match a corresponding sqlException.
     * @param errorCode Message related error code
     * @param sqlState Message related sql state
     * @param errorMessage Error message
     */
    public SQLError(int errorCode, String sqlState, String errorMessage) {
        setErrorCode(errorCode);
        setSqlState(sqlState);
        setErrorMessage(errorMessage);
    }

    /**
     * Creates a sql error to represent a sql exception.
     * @param sqlException SQL exception
     */
    public SQLError(SQLException sqlException) {
        setErrorCode(sqlException.getErrorCode());
        setSqlState(sqlException.getSQLState());
    }

    /**
     * Sets the error code.
     * @param errorCode Error code
     */
    private void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Sets the sql state.
     * @param sqlState SQL state
     */
    private void setSqlState(String sqlState) {
        this.sqlState = sqlState;
    }

    /**
     * Sets the error message.
     * @param errorMessage Error message
     */
    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error code.
     * @return Error code
     */
    private int getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the sql state.
     * @return SQL state
     */
    private String getSqlState() {
        return sqlState;
    }

    /**
     * Gets the error message.
     * @return Error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Checks if the sql error message matches a sql error sql exception.
     * @param obj SQL error representing a sql exception
     * @return True if the sql error message matches the sql error exception
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SQLError) {
            return (getErrorCode() == ((SQLError) obj).getErrorCode())
                && (getSqlState().equals(sqlState));
        }
        return false;
    }

    /**
     * Gets the unique hash code for the sql error.
     * @return Hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(errorCode, sqlState);
    }

    /**
     * Converts the sql error into a string.
     * @return SQL error in a string format
     */
    @Override
    public String toString() {
        return String.format(TO_STRING, getErrorCode(), getSqlState());
    }

}
