package server.base.database.attribute;

/**
 * Represents a attributes as part of a schema.
 */
public class SchemaAttribute extends Attribute {

    //Attributes
    private boolean isAutoIncrement;
    private boolean isUnique;
    private boolean isPrimaryKey;

    /**
     * Creates an attribute as part of a schema.
     * @param name Name of the attribute
     * @param type Type of the attributes
     * @param isAutoIncrement True if the attribute auto increments
     * @param isUnique True if the attribute must have unique values
     * @param isPrimaryKey True if the attribute is a primary key
     */
    public SchemaAttribute(String name, AttributeType type,
        boolean isAutoIncrement, boolean isUnique, boolean isPrimaryKey) {
        super(name, type);
        setIsAutoIncrement(isAutoIncrement);
        setIsUnique(isUnique);
        setIsPrimaryKey(isPrimaryKey);
    }

    /**
     * Sets if the attribute auto increments.
     * @param isAutoIncrement True if the attributes auto increments
     */
    private void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    /**
     * Sets if the attribute contains only unique values.
     * @param isUnique True if the attribute contains only unique values
     */
    private void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * Sets if the attribute is a primary key.
     * @param isPrimaryKey True if it is a primary key
     */
    private void setIsPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * Gets if the attribute auto increments.
     * @return True if the attribute auto increments
     */
    private boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    /**
     * Gets if the attribute contains unique values only.
     * @return True if the attribute contains unique values only.
     */
    private boolean getIsUnique() {
        return isUnique;
    }

    /**
     * Gets if the attribute is a primary key.
     * @return True if the attribute is a primary key
     */
    public boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * Converts attribute to a string value.
     * @return String value of the attribute
     */
    @Override
    public String toString() {
        return getName() + " " + toStringType() + toStringAutoIncrement()
            + toStringUnique();
    }

    /**
     * Converts the attribute type to a string value.
     * @return String value of the attribute type
     */
    private String toStringType() {
        if (getType() == AttributeType.character) {
            return "char(20)";
        } else if (getType() == AttributeType.integer) {
            return "int";
        } else {
            return getType().toString();
        }
    }

    /**
     * Gets a string value if the attribute auto increments.
     * @return String value if the attribute auto increments
     */
    private String toStringAutoIncrement() {
        if (getIsAutoIncrement()) {
            return " auto_increment";
        } else {
            return "";
        }
    }

    /**
     * Gets a string value if the attribute is unique.
     * @return String value if the attribute is unique
     */
    private String toStringUnique() {
        if (getIsUnique()) {
            return " unique";
        } else {
            return "";
        }
    }

}
