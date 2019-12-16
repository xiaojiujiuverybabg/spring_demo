/**
 * 
 */
package com.sjy.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.sjy.util.UuidRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: OrgChlCfgItem.java
 * @Package com.sjy.domain
 * @Description: 推广渠道分润规则明细
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月30日 下午2:36:14
 * @version V1.0
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgChlCfgItem extends UuidRootEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	OrgChlCfg cfg;

	// 交易类型
	Integer tradeType;

	// 支付类型
	Integer payType;

	// 1:每笔固定、2:每笔比率
	Integer type;

	Double amount;

}
