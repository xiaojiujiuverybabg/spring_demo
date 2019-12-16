/**
 * 
 */
package com.sjy.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjy.domain.UserTable;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Repository
public interface UserTableRepository extends CrudRepository<UserTable, Long> {

	UserTable findById(Long id);

	@Query("select o from UserTable as o where o.appId = ?1 and o.openId = ?2")
	UserTable loadByAppIdAndOpenId(String appId, String openId);

	@Query("select count(o) from UserTable as o where o.table.id=?1")
	Long countUserTableByOrgTable(Long tableId);

	@Query("select o from UserTable as o where o.createTime<?1")
	List<UserTable> getShixiaoUserTable(Date date);
}
