package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representation of test.
 *
 * @author Jelena Tabaš
 * @author Tihomir Radosavljević
 * @version 2.0
 */
public class Test implements Serializable{

    /**
     * Id of test in base.
     */
    private int id;
    /**
     * Category to which this test belongs.
     */
    private Category category;

    /**
     * List of test items on this test
     */
    private List<TestItem> listItems;

    public Test() {
        listItems = new ArrayList<>();
    }

    public Test(int id) {
        this.id = id;
        listItems = new ArrayList<>();
    }

    public Test(int id, Category category) {
        this.id = id;
        this.category = category;
        listItems = new ArrayList<>();
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of listItems
     *
     * @return the value of listItems
     */
    public List<TestItem> getListItems() {
        return listItems;
    }

    /**
     * Set the value of listItems
     *
     * @param listItems new value of listItems
     */
    public void setListItems(List<TestItem> listItems) {
        this.listItems = listItems;
    }
}
