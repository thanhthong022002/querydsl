package com.querydsl.apt.domain.type;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class BaseAuditEntity<T extends BaseAuditItem> extends BaseEntity {

    @Column(name = "audits_history")
    @Type(type = "jsonb")
    private List<T> audits;

    public void addAudit(T audit) {
        if (audits == null) {
            audits = new ArrayList<>();
        }

        if (audit != null) {
            audits.add(0, audit);
        }
    }
}
