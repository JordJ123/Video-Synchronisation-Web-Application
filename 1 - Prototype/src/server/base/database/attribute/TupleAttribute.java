package server.base.database.attribute;

/**
 * Represents an attribute as part of a tuple.
 */
public class TupleAttribute extends Attribute {

    //Attributes
    private String value;
    private boolean isCaseSensitive;

    /**
     * Creates an attribute object as part of a tuple.
     * @param name Name of the attribute
     * @param type Type of the attribute
     * @param value Value of the attribute
     * @param isCaseSensitive True if the value is case sensitive
     */
    public TupleAttribute(String name, AttributeType type, String value,
        boolean isCaseSensitive) {
        super(name, type);
        setValue(value);
        setIsCaseSensitive(isCaseSensitive);
    }

    /**
     * Sets the value of the attribute.
     * @param value Value of the attribute
     */
    private void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets if the value is case sensitive.
     * @param isCaseSensitive True if the value is case sensitive
     */
    private void setIsCaseSensitive(boolean isCaseSensitive) {
        this.isCaseSensitive = isCaseSensitive;
    }

    /**
     * Gets the value of the tuple.
     * @return Value of the tuple
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets if the value is case sensitive.
     * @return True if the value is case sensitive
     */
    private boolean getIsCaseSensitive() {
        return isCaseSensitive;
    }

    /**
     * Tuple attribute in a string format.
     * @return String format of a tuple
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if (getIsCaseSensitive()) {
            toString.append("BINARY ");
        }
        if (getType() == AttributeType.character) {
            toString.append("'").append(getValue()).append("'");
        } else {
            toString.append(getValue());
        }
        return toString.toString();
    }

}
