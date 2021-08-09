package com.querydsl.apt.domain.type;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "brand_contact")
public class BrandContact extends BaseAuditEntity<Audit> {

    @Id
    @Column(name = "brand_contact_id")
    private Integer id;

    @Column(name = "work_phones")
    @Type(type = "string-array")
    private String[] workPhone;

    @Column(name = "homephone")
    @Type(type = "string-array")
    private String[] homephone;

    @Column(name = "mobilephone")
    @Type(type = "list-array")
    private List<String> mobilephone;

    @Column(name = "jsonb")
    @Type(type = "jsonb")
    private List<String> jsonbphone;

    @Column(name = "hstore")
    @Type(type = "hstore")
    private Map<String, String> hstore;
}
