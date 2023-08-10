package server.base.database;

/**
 * Represents a view from a table.
 */
public class View {

    //Attributes
    private Tuple[] tuples;

    /**
     * Creates a view from a table.
     * @param tuples Tuples from a table
     */
    public View(Tuple[] tuples) {
        setTuples(tuples);
    }

    /**
     * Sets the tuples of the view.
     * @param tuples Tuples of the view as long as they are from the same table
     */
    private void setTuples(Tuple[] tuples) {
        for (int i = 0; i < tuples.length - 1; i++) {
            if (!tuples[i].isSameSchema(tuples[i + 1])) {
                throw new IllegalArgumentException("Tuples must be from the "
                    + "same table");
            }
        }
        this.tuples = tuples;
    }

    /**
     * Gets the tuples from the view.
     * @return Tuples from the view
     */
    public Tuple[] getTuples() {
        return tuples;
    }

    /**
     * View in a string format.
     * @return String format of the view object
     */
    public String toString() {
        if (tuples.length != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Tuple tuple : tuples) {
                stringBuilder.append(tuple).append("\n");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        } else {
            return "Empty";
        }
    }

}
