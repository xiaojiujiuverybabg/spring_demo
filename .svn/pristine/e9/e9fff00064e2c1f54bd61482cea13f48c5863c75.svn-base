/**
 * 
 */
package com.sjy.dao;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sjy.domain.TableType;

/**
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@Repository
public interface TableTypeRepository extends JpaRepository<TableType, Long> {

	TableType findById(Long id);

	// @Modifying
	// @Transactional(value = TxType.REQUIRES_NEW)
	// @Query("update TableType as o set o.name = ?1,o.ifPay=?3,o.money=?4 where
	// o.id = ?2")
	// int updateTableType(String tableTypeName, long id,long ifPay,double
	// money);
	@Modifying
	@Transactional(value = TxType.REQUIRES_NEW)
	@Query("update TableType as o set o.name = ?1 where o.id = ?2")
	int updateTableType(String tableTypeName, long id);

	@Query("select count(o) from OrgTable as o where o.tableType.id = ?1")
	Long countOrgTableByTableType(Long tableTypeId);

	@Query("select o from TableType as o where o.name = ?1 and o.org.id = ?2")
	TableType loadByTableTypeNameAndOrg(String tableTypeName, long orgId);

	@Query("select o from TableType as o where o.org.id = ?1")
	List<TableType> getTableTypeListByOrgId(long orgId);

}
