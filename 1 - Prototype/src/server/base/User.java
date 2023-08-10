package server.base;

import server.base.database.Table;
import server.base.database.exception.DatabaseException;
import server.base.database.Database;
import server.base.database.View;
import server.base.database.attribute.Attribute;
import server.base.database.attribute.SchemaAttribute;
import server.base.database.attribute.TupleAttribute;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Class that represents a User.
 * @author Jordan Jones
 */
public class User {

    //Static Attributes
    private static Table usersTable;
    private static ReentrantReadWriteLock readWriteLock
        = new ReentrantReadWriteLock();

    //Attributes
    private String username;
    private String password;

    /**
     * Creates a User object.
     * @param username Username of the user
     * @param password Password of the user
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    /**
     * Creates a User object (mainly for JSON purposes).
     */
    public User() {

    }

    /**
     * Creates the users table when the class is loaded.
     */
    static {
        try {
            usersTable
                = new Database("videosync", "root", "").getTable("users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the read write lock for the users data.
     * @return Read write lock for the users data
     */
    public static ReentrantReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    /**
     * Sets the username of the user.
     * @param username Username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     * @param password Password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username of the user.
     * @return Username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     * @return password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Saves the user into the database users table.
     * @throws ClassNotFoundException Error with sql class
     * @throws DatabaseException Error with the database
     */
    public void save() throws ClassNotFoundException, DatabaseException {
        usersTable.insertInto(new TupleAttribute[]{
            new TupleAttribute("username",
                Attribute.AttributeType.character, username, false),
            new TupleAttribute("password",
                Attribute.AttributeType.character, password, false),
        });
    }

    /**
     * Checks if the details are correct to the details in the database.
     * @return True if the details are correct
     * @throws ClassNotFoundException Throws when can't find sql class
     * @throws DatabaseException Throws when there is an error with the database
     */
    public boolean areDetailsCorrect() throws DatabaseException {
        SchemaAttribute[] schema = new SchemaAttribute[]{
            new SchemaAttribute("username", Attribute.AttributeType.character,
                false, true, false),
            new SchemaAttribute("password", Attribute.AttributeType.character,
                false, false, false)
        };
        TupleAttribute[] attributes = new TupleAttribute[]{
            new TupleAttribute("username", Attribute.AttributeType.character,
                getUsername(), false),
            new TupleAttribute("password", Attribute.AttributeType.character,
                getPassword(), true)
        };
        View user = usersTable.select(schema, attributes);
        return user.getTuples().length == 1;
    }

    /**
     * Checks if the given object is the same.
     * @param obj Object to compare with
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return getUsername().equals(((User) obj).getUsername());
        }
        return false;
    }

    /**
     * Generates the hash code value for the user.
     * @return Hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

}
