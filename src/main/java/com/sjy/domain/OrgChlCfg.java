/**
 * 
 */
package com.sjy.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.sjy.base.domain.SimpleOper;
import com.sjy.base.domain.SimpleOrg;
import com.sjy.util.UuidRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: OrgChlCfg.java
 * @Package com.sjy.domain
 * @Description: 推广渠道分润规则
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月30日 下午2:26:37
 * @version V1.0
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgChlCfg extends UuidRootEntity {

	@Column(length = 128, nullable = false)
	String name;

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org;

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOper oper;

	Date recordDate;

}
