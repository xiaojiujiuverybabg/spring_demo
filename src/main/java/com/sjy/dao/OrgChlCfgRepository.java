/**
 * 
 */
package com.sjy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sjy.domain.OrgChlCfg;

/**
 * @Title: OrgChlCfgRepository.java
 * @Package com.sjy.dao
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月30日 下午2:42:37
 * @version V1.0
 */
@Repository
public interface OrgChlCfgRepository extends JpaRepository<OrgChlCfg, String> {

}
