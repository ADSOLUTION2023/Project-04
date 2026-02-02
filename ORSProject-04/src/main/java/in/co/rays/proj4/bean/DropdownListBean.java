package in.co.rays.proj4.bean;

/**
 * The DropdownListBean interface defines the structure for beans
 * that can be represented in dropdown lists. Implementing classes
 * must provide a key (usually the ID) and a value (usually the name
 * or label to be displayed).
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public interface DropdownListBean {

    /**
     * Returns the unique key of the bean, typically used as the
     * value attribute in HTML dropdowns.
     *
     * @return the key as a String
     */
    public String getKey();

    /**
     * Returns the display value of the bean, typically shown
     * as the visible text in dropdown lists.
     *
     * @return the display value as a String
     */
    public String getValue();
}
