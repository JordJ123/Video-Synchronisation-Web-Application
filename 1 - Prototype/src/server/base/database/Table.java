package server.base.database;

import server.base.database.attribute.Attribute;
import server.base.database.attribute.SchemaAttribute;
import server.base.database.attribute.TupleAttribute;
import server.base.database.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;

import static server.base.database.exception.DatabaseException.DUPLICATE_COLUMN;
import static server.base.database.exception.DatabaseException.UNKNOWN_COLUMN;

/**
 * Class that represents a table from a database.
 */
public class Table {

    //Attributes
    private Database database;
    private String name;

    /**
     * Creates a table object from the database.
     * @param database Database this table is from
     * @param name Name of the table
     */
    Table(Database database, String name) {
        setDatabase(database);
        setName(name);
    }

    /**
     * Sets the database this table is from.
     * @param database Database this table is from
     */
    private void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * Sets the name of the table.
     * @param name Name of the table
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the table.
     * @return Name of the table
     */
    private String getName() {
        return name;
    }

    /**
     * Adds a column to the table.
     * @param schema Set of attributes to add to the table
     * @throws DatabaseException Throws if there is an error with the database
     */
    private void addColumn(SchemaAttribute[] schema) throws DatabaseException {
        for (SchemaAttribute schemaAttribute : schema) {
            try {
                StringBuilder sqlStatement = new StringBuilder();
                sqlStatement.append("alter table ").append(getName())
                    .append("\n").append("add column ").append(schemaAttribute)
                    .append("\n");
                database.queryDataManipulation(sqlStatement.toString());
            } catch (DatabaseException databaseException) {
                if (!databaseException.getMessage().equals(
                    DUPLICATE_COLUMN.getErrorMessage())) {
                    throw databaseException;
                }
            }
        }
    }

    /**
     * Gets a view from the table.
     * @param schema Attributes of the view (Null or Empty if all)
     * @param where Values of tuple attributes (Null or Empty if all)
     * @return View specified from the table
     * @throws SQLException Error with the class's sql
     */
    public View select(SchemaAttribute[] schema, TupleAttribute[] where)
        throws DatabaseException {
        try {
            StringBuilder sqlStatement = new StringBuilder();
            sqlStatement.append("select ");
            if (schema != null && schema.length > 0) {
                for (Attribute schemaAttribute : schema) {
                    sqlStatement.append(schemaAttribute.getName()).append(", ");
                }
                sqlStatement.replace(sqlStatement.length() - 2,
                    sqlStatement.length(), "\n");
            } else {
                sqlStatement.append("*\n");
            }
            sqlStatement.append("from ").append(getName()).append("\n");
            if (where != null && where.length > 0) {
                sqlStatement.append("where ");
                for (Attribute wherePart : where) {
                    sqlStatement.append(wherePart.getName()).append(" = ")
                        .append(wherePart).append(" AND ");
                }
                sqlStatement.replace(sqlStatement.length() - " AND ".length(),
                    sqlStatement.length(), "");
            }
            ResultSet results = database.query(sqlStatement.toString());
            ArrayList<Tuple> tuples = new ArrayList<>();
            while (results.next()) {
                int columns = results.getMetaData().getColumnCount();
                TupleAttribute[] attributes = new TupleAttribute[columns];
                for (int i = 0; i < columns; i++) {
                    attributes[i] = (new TupleAttribute(
                        results.getMetaData().getColumnName(i + 1),
                        Attribute.getCorrespondingType(
                            results.getMetaData().getColumnType(i + 1)),
                        results.getString(i + 1), false));
                }
                tuples.add(new Tuple(attributes));
            }
            return new View(tuples.toArray(new Tuple[0]));
        } catch (DatabaseException databaseException) {
            if (databaseException.getMessage().equals(
                UNKNOWN_COLUMN.getErrorMessage())) {
                addColumn(schema);
                return select(schema, where);
            } else {
                throw databaseException;
            }
        } catch (SQLException error) {
            throw new DatabaseException(error);
        }
    }

    /**
     * Insert tuple in the table.
     * @param attributes Attributes of the table
     * @throws SQLException Attributes are do not match table's schema
     */
    public void insertInto(TupleAttribute[] attributes)
        throws DatabaseException {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("insert into ").append(getName()).append(" (");
        for (Attribute attribute : attributes) {
            sqlStatement.append(attribute.getName()).append(", ");
        }
        sqlStatement.replace(sqlStatement.length() - 2,
            sqlStatement.length(), ")");
        sqlStatement.append(" values (");
        for (TupleAttribute attribute : attributes) {
            sqlStatement.append(attribute).append(", ");
        }
        sqlStatement.replace(sqlStatement.length() - 2,
            sqlStatement.length(), ")");
        database.queryDataManipulation(sqlStatement.toString());
    }

}
