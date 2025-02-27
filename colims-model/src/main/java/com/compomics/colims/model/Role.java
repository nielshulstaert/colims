package com.compomics.colims.model;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a role entity in the database. The table name is "group_role" because "role" is a reserved
 * keyword.
 *
 * @author Niels Hulstaert
 */
@Table(name = "group_role")
@Entity
public class Role extends AuditableDatabaseEntity {

    private static final long serialVersionUID = 4331346270790982907L;

    /**
     * The role name.
     */
    @Basic(optional = false)
    @NotBlank(message = "Please insert a role accession.")
    @Length(min = 3, max = 20, message = "Role name length must be between {min} and {max} characters.")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /**
     * The role description.
     */
    @Basic(optional = true)
    @Length(max = 500, message = "Role description length must be less than {max} characters.")
    @Column(name = "description")
    private String description;
    /**
     * The groups this role belongs to.
     */
    @ManyToMany(mappedBy = "roles")
    private List<Group> groups;
    /**
     * The permissions of this role.
     */
    @ManyToMany
    @JoinTable(name = "role_has_permission",
            joinColumns = {
                    @JoinColumn(name = "l_role_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "l_permission_id", referencedColumnName = "id")})
    private List<Permission> permissions = new ArrayList<>();

    /**
     * No-arg constructor.
     */
    public Role() {
    }

    /**
     * Constructor.
     *
     * @param name the role name
     */
    public Role(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!name.equals(role.name)) return false;
        return !(description != null ? !description.equals(role.description) : role.description != null);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

}
