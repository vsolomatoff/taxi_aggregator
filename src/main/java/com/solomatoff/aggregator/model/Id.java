package com.solomatoff.aggregator.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

/**
 * The class defines an identifier (primary key) for other entities
 */
public abstract class Id {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Id() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Id other = (Id) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
