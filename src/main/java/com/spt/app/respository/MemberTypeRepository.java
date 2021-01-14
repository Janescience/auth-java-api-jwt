package com.spt.app.respository;

import com.spt.app.entity.app.MemberType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTypeRepository extends CrudRepository<MemberType, Integer> {
    MemberType findByCode(@Param("code") String code);
}
