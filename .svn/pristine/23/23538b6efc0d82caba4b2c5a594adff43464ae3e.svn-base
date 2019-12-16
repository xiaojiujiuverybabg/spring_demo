/**
 * 
 */
package com.sjy.dao;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjy.domain.OrgTable;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Repository
public interface OrgTableRepository extends CrudRepository<OrgTable, Long> {

	OrgTable findById(Long id);

	@Query("select o from OrgTable as o where o.org.id = ?1 and o.tableName = ?2")
	OrgTable loadByOrgIdAndTableName(long orgId, String tableName);

	@Modifying
	@Transactional(value = TxType.REQUIRES_NEW)
	@Query("update OrgTable as o set o.tableName = ?1,o.minPay=?3,o.tableType.id=?4,o.tcn.id=?5 where o.id = ?2")
	int updateTable(String tableName, long id, double minPay, long tableType, long tcnId);

	@Query("select o from OrgTable as o where o.org.id = ?1 and o.status!=4") // and o.status!=4
	Iterable<OrgTable> findOrgTablesByOrgId(long orgId);

	@Modifying
	@Transactional(value = TxType.REQUIRES_NEW)
	@Query("update OrgTable as o set o.status = ?2 where o.id = ?1")
	int editTablestatus(long tableId, long status);

	@Query("select o from OrgTable as o where o.org.id = ?1 and o.tableType.id = ?2 and o.tcn.id=?3 and o.status!=4")
	Iterable<OrgTable> findOrgTablesByOrgNumAndType(long orgId, long tableTypeId, long tableCustomerNumId);

	@Query("select o from OrgTable as o where o.org.id = ?1 and o.tcn.id=?2 and o.status!=4")
	Iterable<OrgTable> findOrgTablesByOrgNum(long orgId, long tableCustomerNumId);
}
