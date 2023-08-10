package server.base.room;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents an update for a room.
 */
public class RoomUpdate {

    //Attribute
    @JsonProperty("updateType") private String updateType;
    @JsonProperty("information") private String information;

    /**
     * Creates a room update.
     * @param updateType Type of update
     * @param information Information about the update
     */
    public RoomUpdate(String updateType, String information) {
        setUpdateType(updateType);
        setInformation(information);
    }

    /**
     * Sets the type of update.
     * @param updateType Type of update
     */
    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    /**
     * Sets the information about the update.
     * @param information information about the update
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Gets the type of update.
     * @return Type of update
     */
    public String getUpdateType() {
        return updateType;
    }

    /**
     * Gets the information about the update.
     * @return information about the update
     */
    public String getInformation() {
        return information;
    }

}
