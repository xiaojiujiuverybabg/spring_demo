package com.sjy.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
public class UserTable extends SeqRootEntity implements Cloneable {

	@ManyToOne(fetch = FetchType.LAZY)
	OrgTable table; // 桌位信息

	@Column(name = "appId")
	String appId; //

	@Column(name = "openId")
	String openId; //
	
	@Column(name = "nickName")
	String nickName; // 昵称
	
	@Column(name = "createTime")
	Date createTime; // 关注时间
	
}
