/**
 * 
 */
package com.sjy.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.sjy.base.domain.SimpleOrg;
import com.sjy.util.UuidRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: OrgChlTrade.java
 * @Package com.sjy.domain
 * @Description: 渠道收益明细
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月30日 下午2:45:42
 * @version V1.0
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgChlTrade extends UuidRootEntity {

	@Column(nullable = false)
	Long tradeId;

	@ManyToOne(fetch = FetchType.LAZY)
	OrgChl orgChl;

	@ManyToOne(fetch = FetchType.LAZY)
	UserChl userChl;

	@ManyToOne(fetch = FetchType.LAZY)
	OrgChlCfgItem item;

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org; // 交易发生机构

	Date tradeDate;

	Integer tradeType;

	Integer payType;

	Long amount; // 实际交易金额

	Long income; // 收益金额

	Date recordDate; // 统计时间

	Long cardUser; // 客户ID

}
