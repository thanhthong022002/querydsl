package com.querydsl.apt.domain.type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Represent entities that have audit information when do update/insert
 *
 * Created by thongnguyen on 12/12/15.
 */
@MappedSuperclass
public abstract class BaseEntity extends TypeDefsEntity {

    @Basic
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Basic
    @Column(name = "updated_at")
    private Date updatedAt;
}
