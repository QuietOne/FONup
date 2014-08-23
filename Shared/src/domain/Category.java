package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representation of category.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class Category implements Serializable, GenericDomainObject {

    /**
     * Id of category in base.
     */
    private int id;
    /**
     * Name of category.
     */
    private String name;
    /**
     * Indicator if this category has been approved by administrator.
     */
    private boolean approved;
    /**
     * Parent category of this category.
     */
    private Category parentCategory;
    /**
     * List of all subCategories.
     */
    private List<Category> subCategories;

    public Category() {
        approved = false;
        subCategories = new ArrayList<>();
    }

    public Category(int id) {
        this.id = id;
        approved = false;
        subCategories = new ArrayList<>();
    }

    /**
     * Get the value of subCategories
     *
     * @return the value of subCategories
     */
    public List<Category> getSubCategories() {
        return subCategories;
    }

    /**
     * Set the value of subCategories
     *
     * @param subCategories new value of subCategories
     */
    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * Get the value of parentCategory
     *
     * @return the value of parentCategory
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * Set the value of parentCategory
     *
     * @param parentCategory new value of parentCategory
     */
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * Get the value of approved
     *
     * @return the value of approved
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Set the value of approved
     *
     * @param approved new value of approved
     */
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getTableName() {
        return "category";
    }

    @Override
    public List<GenericDomainObject> getAll(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        return id + ",'" + name + "'," + approved + "," + parentCategory.getId();
    }
}
