/**
 * 
 */
package com.sjy.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.sjy.base.domain.SimpleOper;
import com.sjy.base.domain.SimpleOrg;
import com.sjy.util.SeqRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: RuleSet.java
 * @Package com.sjy.domain
 * @Description: 活动规则
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月10日 下午12:42:53
 * @version V1.0
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class RuleSet extends SeqRootEntity implements Cloneable {

	@Column(name = "ruleType")
	int ruleType; // 规则类型

	String name; // 规则名称

	Date beginTime; // 生效时间

	Date endTime; // 失效期

	String mainJudge; // 主条件标识（例如手续费规则的XXX手续费、商品规则的XXX商品)

	Date createTime; // 创建时间

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOper creator; // 创建者

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg createOrg; // 规则创建机构

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg executeOrg; // 规则所属机构

	@Column(name = "rule_status")
	int status;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	String rules; // 规则 [[name1:value1],[name2:value2]]

	@Lob
	@Basic(fetch = FetchType.LAZY)
	String defResult; // 缺省结果

	String ruleDesc; // 规则描述

	Long photo_id; // 规则图片

	String flag; // 当flag=1时 隐藏，不查询出来,当flag=null时，查询出来
}
