package com.sjy.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.sjy.base.domain.SimpleOper;
import com.sjy.base.domain.SimpleOrg;
import com.sjy.util.SeqRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 渠道推广二维码entity类
 * 
 * @copyright(c) Copyright SJY Corporation 2016.
 * 
 * @since 2016年12月9日
 * @author liyan
 * @e-mail 289149734@qq.com
 * 
 */
@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgChl extends SeqRootEntity implements Cloneable {

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOrg org; // 所属机构

	@Column(name = "CHLNAME")
	String chlName; // 渠道名称

	@Column(name = "LOCALURL")
	String localUrl; // 本地二维码路径

	@Column(name = "WECHATURL")
	String wechatUrl; // 微信二维码路径

	@Column(name = "qrcodeimgurl")
	String qrcodeImgUrl; // 微信二维码图片路径

	@ManyToOne(fetch = FetchType.LAZY)
	SimpleOper oper; // 关联操作员

	@Column(length = 11)
	String mobilePhone; // 关联手机号

	Long cardUser; // 关联会员[CardUser]Id

	@Column(length = 128)
	String bankName; // 开户行信息

	@Column(length = 32)
	String bankNo; // 银行账号

	@ManyToOne(fetch = FetchType.LAZY)
	OrgChlCfg cfg; // 分润规则
	
	@Transient
	String shareUserName;
	
	@Transient
	String shareUserHeadimgurl;
	
	@Column(name = "orgchl_Type")
	Integer orgchlType;	//渠道类型1：本机构 2：异业机构
	
	@Column(name = "yiyeOrg_id")
	Long yiyeOrg; // 异业机构
	
	@Column(name = "rebid")
	Integer rebid;//分润模式
	
	@Column(name = "rebRatio")
	Float  rebRatio;//分润比例
	
}
