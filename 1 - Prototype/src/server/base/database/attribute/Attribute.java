package server.base.database.attribute;

import java.util.Objects;

import static java.sql.Types.CHAR;
import static java.sql.Types.INTEGER;

/**
 * Represents an attribute of a schema or tuple.
 */
public abstract class Attribute {

    //CONSTANTS
    private static final String CORRESPONDING_TYPE_ERROR
        = "No corresponding type for sql type number %s";

    //Attributes
    private String name;
    private AttributeType type;

    /**
     * Creates an attribute object for a schema or a tuple.
     * @param name Name of the attribute
     * @param type Type for the attribute
     */
    public Attribute(String name, AttributeType type) {
        setName(name);
        setType(type);
    }

    /**
     * Attribute Types.
     */
    public enum AttributeType {
        character, integer
    }

    /**
     * Gets the corresponding type for the given sql type.
     * @param sqlType SQL type number
     * @return Attribute type
     */
    public static AttributeType getCorrespondingType(int sqlType) {
        if (sqlType == CHAR) {
            return AttributeType.character;
        } else if (sqlType == INTEGER) {
            return AttributeType.integer;
        } else {
            throw new IllegalArgumentException(
                String.format(CORRESPONDING_TYPE_ERROR, sqlType));
        }
    }

    /**
     * Sets the name of the attribute.
     * @param name Name of the attribute
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the type of the attribute.
     * @param type Type of the attribute
     */
    private void setType(AttributeType type) {
        this.type = type;
    }

    /**
     * Gets the name of the attribute.
     * @return Name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the attribute.
     * @return Type of the attribute
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * Checks if the given object is the same.
     * @param obj Object to compare with
     * @return True if the objects are the same
     */
    public boolean equals(Object obj) {
        if (obj instanceof Attribute) {
            if (getName().equals(((Attribute) obj).getName())
                && getType().equals(((Attribute) obj).getType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

}
