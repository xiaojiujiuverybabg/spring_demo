package com.sjy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.sjy.base.domain.SimpleOrg;
import com.sjy.util.SeqRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道推广二维码entity类
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TableType extends SeqRootEntity implements Cloneable {

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org; // 所属机构

	@Column(name = "name")
	String name; // 桌位类型

	@Column(name = "ifpay")
	Long ifPay; // 是否需要订金 0不需要 1需要 默认0

	@Column(name = "money")
	Double money; // 订金金额 默认0 单位（元）
}
