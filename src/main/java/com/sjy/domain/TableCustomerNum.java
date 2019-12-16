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
 * 就餐人数范围entity类
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class TableCustomerNum extends SeqRootEntity implements Cloneable {

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org; // 所属机构

	@Column(name = "min")
	Long min; // 最小人数

	@Column(name = "max")
	Long max; // 最大人数
}
