/**
 * 
 */
package com.sjy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjy.domain.OrgChl;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Repository
public interface OrgChlRepository extends CrudRepository<OrgChl, Long> {

	@Query("select o from OrgChl as o where o.org.id = ?1 and o.chlName = ?2")
	OrgChl loadByOrgIdAndChlName(long orgId, String chlName);

	@Query("select o from OrgChl as o join o.org as org1, "
			+ "SimpleOrgTree as ot join ot.organization as org left join ot.parentTree as pt "
			+ "where org1.id = org.id and o.mobilePhone = ?1 and (pt.organization.id = ?2 or (ot.organization.id = ?2 and (ot.parentOrgLevel = ?3 - 100 or ot.parentOrgLevel is null))) ")
	OrgChl findByMobilePhone(String mobilePhone, Long orgId, Integer orgLevel);

	@Query("select o from OrgChl as o  "
			+ "where o.mobilePhone = ?1 and o.org.id = ?2")
	List<OrgChl> findByMobilePhone(String mobilePhone, Long orgId);
	
	@Query("select o from OrgChl as o where o.oper.id = ?1")
	OrgChl findByOperId(Long operId);

	@Query("select o from OrgChl as o where o.org.id = ?1 and o.yiyeOrg = ?2")
	OrgChl findByYiyeOrg(Long orgId,Long yiyeOrg);
	
	@Query("select o from OrgChl as o where o.cardUser = ?1")
	OrgChl findByCardUser(Long cardUser);
}
