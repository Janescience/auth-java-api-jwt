package com.spt.app.entity.app;

import com.spt.app.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class MemberType extends BaseEntity{

    private String name;

    @Column(unique = true,length = 2)
    private String code;
}
