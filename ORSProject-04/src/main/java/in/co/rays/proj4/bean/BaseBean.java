package in.co.rays.proj4.bean;

import java.sql.Timestamp;

/**
 * The BaseBean class serves as a base entity for all beans in the application.
 * It provides common attributes such as id, createdBy, modifiedBy,
 * createdDatetime, and modifiedDatetime.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public abstract class BaseBean implements DropdownListBean {

    /** Unique identifier of the bean. */
    protected long id;

    /** User who created the record. */
    protected String createdBy;

    /** User who last modified the record. */
    protected String modifiedBy;

    /** Timestamp when the record was created. */
    protected Timestamp createdDatetime;

    /** Timestamp when the record was last modified. */
    protected Timestamp modifiedDatetime;

    /**
     * Gets the unique identifier of the bean.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the bean.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user who created the record.
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the record.
     *
     * @param createdBy the creator's username
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the user who last modified the record.
     *
     * @return modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the user who last modified the record.
     *
     * @param modifiedBy the modifier's username
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Gets the timestamp when the record was created.
     *
     * @return createdDatetime
     */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Sets the timestamp when the record was created.
     *
     * @param createdDatetime the creation timestamp
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * Gets the timestamp when the record was last modified.
     *
     * @return modifiedDatetime
     */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Sets the timestamp when the record was last modified.
     *
     * @param modifiedDatetime the modification timestamp
     */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }
}
