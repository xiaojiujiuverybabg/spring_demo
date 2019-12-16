/**
 * 
 */
package com.sjy.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sjy.base.domain.SimpleOrg;
import com.sjy.domain.RuleSet;

/**
 * @Title: RuleSetRepository.java
 * @Package com.sjy.dao
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月10日 下午12:49:15
 * @version V1.0
 */
@Repository
public interface RuleSetRepository extends JpaRepository<RuleSet, Long> {

	@Query("select o from RuleSet as o where o.executeOrg = ?1 and (o.ruleType = ?2 or o.ruleType = ?3) and o.status = ?4 and o.beginTime <= ?5 and o.endTime >= ?5")
	List<RuleSet> getRuleSetForRecommend(SimpleOrg org, int ruleType1, int ruleType2, int status, Date currentDate);

}
