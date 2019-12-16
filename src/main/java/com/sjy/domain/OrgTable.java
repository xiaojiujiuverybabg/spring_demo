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
 * 桌位二维码entity类
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgTable extends SeqRootEntity implements Cloneable {

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org; // 所属机构

	@ManyToOne(fetch = FetchType.LAZY)
	TableType tableType; // 桌位类型

	@Column(name = "TABLENAME")
	String tableName; // 渠道名称

	@Column(name = "MINPAY")
	Double minPay; // 最低消费金额 默认为0

	@Column(name = "LOCALURL")
	String localUrl; // 本地二维码路径
	@Column(name = "WECHATURL")
	String wechatUrl; // 微信二维码路径
	@Column(name = "qrcodeimgurl")
	String qrcodeImgUrl; // 微信二维码图片路径

	@ManyToOne(fetch = FetchType.LAZY)
	TableCustomerNum tcn;

	@Column(name = "STATUS")
	Long status = 1l;
}
