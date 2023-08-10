package server.base.database;

import server.base.database.attribute.Attribute;
import server.base.database.attribute.SchemaAttribute;
import server.base.database.exception.DatabaseException;
import java.sql.*;

import static server.base.database.exception.DatabaseException.UNKNOWN_DATABASE;

/**
 * Class that represents a database.
 */
public class Database {

    //DATABASE PATH
    private static final String DATABASE_PATH
        = "jdbc:mysql://localhost:3306/%s";
    private static final String DATABASE_EXTENDED_PATH
        = String.format(DATABASE_PATH, "?user=%s&password=%s");

    //DATABASE SQL
    private static final String CREATE_DATABASE = "CREATE DATABASE %s";

    //Attributes
    private String name;
    private String username;
    private String password;
    private Connection connection;

    /**
     * Creates a database object which is actually a connection to a server.
     * @param name Name of the database
     * @param username Username to access the database
     * @param password Password to access the database
     * @throws ClassNotFoundException Error where sql class can't be found
     * @throws ConnectException Throws if can't access database
     */
    public Database(String name, String username, String password)
        throws ClassNotFoundException, DatabaseException {
        setName(name);
        setUsername(username);
        setPassword(password);
        setConnection();
    }

    /**
     * Sets the name of database.
     * @param name Name of the database
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the username to access the database.
     * @param username Username to access the database
     */
    private void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password to access the database.
     * @param password Password to access the database
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the connection to the database server.
     * @throws ClassNotFoundException Error where sql class can't be found
     * @throws DatabaseException Throws if can't access database
     */
    private void setConnection()
        throws ClassNotFoundException, DatabaseException {
        Class.forName("com.mysql.jdbc.Driver");
        try {
            connection = DriverManager.getConnection(
                String.format(Database.DATABASE_PATH, getName()),
                getUsername(), getPassword());
        } catch (SQLException error) {
            DatabaseException databaseException = new DatabaseException(error);
            if (databaseException.getMessage().equals(
                UNKNOWN_DATABASE.getErrorMessage())) {
                try {
                    connection = DriverManager.getConnection(String.format(
                        DATABASE_EXTENDED_PATH, getUsername(), getPassword()));
                    connection.createStatement().executeUpdate(
                        String.format(CREATE_DATABASE, getName()));
                } catch (SQLException e) {
                    throw new DatabaseException(e);
                }
            } else {
                throw databaseException;
            }
        }
    }

    /**
     * Gets the name of the database.
     * @return Name of the database
     */
    String getName() {
        return name;
    }

    /**
     * Gets the username to access the database.
     * @return Username to access the database
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the password to access the database.
     * @return Password to access the database
     */
    String getPassword() {
        return password;
    }

    /**
     * Gets the wanted table from the database.
     * @param name Name of the table
     * @return Table in the database
     */
    public Table getTable(String name) throws DatabaseException {
        Table table = null;
        try {
            table = new Table(this, name);
            table.select(null, null);
        } catch (DatabaseException error) {
            if (error.getMessage().equals(
                DatabaseException.UNKNOWN_TABLE.getErrorMessage())) {
                createTable("users", null);
            } else {
                throw error;
            }
        }
        return table;
    }

    /**
     * Creates a table in the database.
     * @param tableName Name of the table
     * @param attributes Attributes part of the table (Null or Empty for none)
     * @throws DatabaseException Throws if error with the database
     */
    public void createTable(String tableName, SchemaAttribute[] attributes)
        throws DatabaseException {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("create table ").append(tableName).append(" (\n");
        if (attributes != null && attributes.length > 0) {
            for (SchemaAttribute attribute : attributes) {
                sqlStatement.append("    ").append(attribute).append(",\n");
                if (attribute.getIsPrimaryKey()) {
                    sqlStatement.append(String.format("    primary key (%s),\n",
                        attribute.getName()));
                }
            }
            sqlStatement.replace(sqlStatement.length() - 2,
                sqlStatement.length(), "\n");
        } else {
            sqlStatement.append(new SchemaAttribute("id",
                Attribute.AttributeType.integer, true, true, true))
                .append("\n");
        }
        sqlStatement.append(")");
        queryDataManipulation(sqlStatement.toString());
    }

    /**
     * Queries the database.
     * @param sqlStatement Statement to query the database with
     * @return Results of the query
     * @throws DatabaseException Throws if error with the database
     */
    ResultSet query(String sqlStatement) throws DatabaseException {
        try {
            System.out.println(sqlStatement);
            Statement statement = connection.createStatement();
            return statement.executeQuery(sqlStatement);
        } catch (SQLException error) {
            throw new DatabaseException(error);
        }
    }

    /**
     * Queries the database with a data manipulation statement.
     * @param sqlStatement Data manipulation sql statement
     * @throws DatabaseException Throws if error with the database
     */
    void queryDataManipulation(String sqlStatement) throws DatabaseException {
        try {
            System.out.println(sqlStatement);
            Statement statement = connection.createStatement();
            statement.execute(sqlStatement);
        } catch (SQLException error) {
            throw new DatabaseException(error);
        }
    }

}
