package com.epam.kuzovatov.entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Pavel on 12.06.2015.
 */
public abstract class AbstractBaseEntity implements Serializable {
    private UUID id;

    public AbstractBaseEntity(UUID id){
        this.id=UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AbstractBaseEntity)) {
            return false;
        }
        AbstractBaseEntity other = (AbstractBaseEntity) obj;
        return getId().equals(other.getId());
    }
}
