package in.co.rays.proj4.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.RoleModel;

/**
 * <p>
 * Utility class responsible for generating dynamic HTML select (dropdown)
 * components for JSP pages.
 * </p>
 *
 * <p>
 * Supports:
 * </p>
 * <ul>
 *     <li>Dropdown creation using a HashMap (key-value pairs)</li>
 *     <li>Dropdown creation using a List of objects implementing {@link DropdownListBean}</li>
 *     <li>Automatically selecting a default value</li>
 * </ul>
 *
 * <p>
 * Used heavily in forms like:
 * <ul>
 *     <li>User Registration</li>
 *     <li>Role Selection</li>
 *     <li>Gender Dropdown</li>
 *     <li>Course / College / Subject dropdowns</li>
 * </ul>
 * </p>
 *
 * @author
 * @version 1.0
 */
public class HTMLUtility {

    /**
     * Generates an HTML dropdown (&lt;select&gt;) using a {@link HashMap}.
     *
     * @param name         name of the HTML select element
     * @param selectedVal  value that should be pre-selected
     * @param map          map containing dropdown values (key = option value, value = label)
     * @return HTML string representing a fully constructed &lt;select&gt; element
     */
    public static String getList(String name, String selectedVal, HashMap<String, String> map) {

        StringBuffer sb = new StringBuffer(
                "<select style=\"width: 169px;text-align-last: center;\"; class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        Set<String> keys = map.keySet();
        String val = null;

        for (String key : keys) {
            val = map.get(key);
            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Generates an HTML dropdown (&lt;select&gt;) using a list of objects that
     * implement {@link DropdownListBean}.  
     *  
     * <p>The key-value pair is extracted from:</p>
     * <ul>
     *     <li>{@link DropdownListBean#getKey()}</li>
     *     <li>{@link DropdownListBean#getValue()}</li>
     * </ul>
     *
     * @param name         name of the HTML select element
     * @param selectedVal  value that should be pre-selected
     * @param list         list of objects implementing DropdownListBean
     * @return HTML string representing the dropdown
     */
    public static String getList(String name, String selectedVal, List list) {

        List<DropdownListBean> dd = (List<DropdownListBean>) list;

        StringBuffer sb = new StringBuffer("<select style=\"width: 169px;text-align-last: center;\"; "
                + "class='form-control' name='" + name + "'>");

        sb.append("\n<option selected value=''>-------------Select-------------</option>");

        String key = null;
        String val = null;

        for (DropdownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("\n<option selected value='" + key + "'>" + val + "</option>");
            } else {
                sb.append("\n<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("\n</select>");
        return sb.toString();
    }

    /**
     * Test method: Generates a dropdown using a HashMap.
     */
    public static void testGetListByMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("male", "male");
        map.put("female", "female");

        String selectedValue = "male";
        String htmlSelectFromMap = HTMLUtility.getList("gender", selectedValue, map);

        System.out.println(htmlSelectFromMap);
    }

    /**
     * Test method: Generates a dropdown from the list returned by {@link RoleModel}.
     *
     * @throws Exception if model retrieval fails
     */
    public static void testGetListByList() throws Exception {

        RoleModel model = new RoleModel();

        List list = model.list();

        String selectedValue = "1";

        String htmlSelectFromList = HTMLUtility.getList("role", selectedValue, list);

        System.out.println(htmlSelectFromList);
    }

    public static void main(String[] args) throws Exception {

        testGetListByMap();

        // testGetListByList();
    }
}
