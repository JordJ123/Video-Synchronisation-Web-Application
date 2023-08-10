package server.base.database;

import server.base.database.attribute.Attribute;
import server.base.database.attribute.TupleAttribute;

/**
 * Class that represents a tuple from a table.
 */
public class Tuple {

    //Attributes
    private TupleAttribute[] attributes;

    /**
     * Creates a tuple object.
     * @param attributes Attributes of the tuple
     */
    Tuple(TupleAttribute[] attributes) {
        setAttributes(attributes);
    }

    /**
     * Sets the attributes of the tuple.
     * @param attributes Attributes of the tuple
     */
    private void setAttributes(TupleAttribute[] attributes) {
        this.attributes = attributes;
    }

    /**
     * Gets the attributes of the tuple.
     * @return Attributes of the tuple
     */
    public TupleAttribute[] getAttributes() {
        return attributes;
    }

    /**
     * Check if the given tuple is from the same table.
     * @param comparisonTuple Tuple to compare with
     * @return True if the tuples are from the same table
     */
    boolean isSameSchema(Tuple comparisonTuple) {
        if (getAttributes().length != comparisonTuple.getAttributes().length) {
            return false;
        }
        for (int i = 0; i < getAttributes().length - 1; i++) {
            if (!getAttributes()[i].equals(
                comparisonTuple.getAttributes()[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts the tuple object to a string value.
     * @return String value of the tuple object
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Attribute attribute : attributes) {
            stringBuilder.append(attribute).append(" ");
        }
        return stringBuilder.toString();
    }

}
