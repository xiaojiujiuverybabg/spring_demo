/**
 * 
 */
package com.sjy.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjy.domain.OrgChl;
import com.sjy.domain.UserChl;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Repository
public interface UserChlRepository extends CrudRepository<UserChl, Long> {

	UserChl findByAppIdAndOpenId(String appId, String openId);
	
	@Query("select o from UserChl as o where o.appId=?1 and o.openId=?2 order by o.createTime desc")
	List<UserChl> findUserChlsByAppIdAndOpenId(String appId, String openId);

	@Query("select count(o.id) from UserChl as o where o.appId=?1 and o.openId=?2")
	int countByAppIdAndOpenId(String appId, String openId);
	
	UserChl findByCardUser(Long cardUserId);

	UserChl findByChlAndOpenId(OrgChl chl, String openId);
	
	@Query("select o from UserChl as o where o.chl.id=?1")
	List<UserChl> findByChl(Long chlId);

	@Query("select count(o.id) from UserChl as o where o.chl.id=?1")
	int countUserChlByOrgChl(Long chlId);

	@Query("select count(o.id) as gzNum, sum(case when o.member = 1 then 1 else 0 end) as zcNum from UserChl as o where o.chl.id = ?1")
	Map<String, Object> countGzAngZc(Long chlId);
}
