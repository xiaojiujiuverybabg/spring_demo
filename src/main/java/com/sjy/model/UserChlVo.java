/**
 * 
 */
package com.sjy.model;

import java.util.Date;

import com.sjy.util.SeqRootEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Title: UserChlVo.java
 * @Package com.sjy.model
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月27日 下午3:35:54
 * @version V1.0
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserChlVo extends SeqRootEntity {

	OrgChlVo chl; // 渠道信息

	String appId;

	String openId;

	String nickName; // 昵称

	Date createTime; // 关注时间

	Date registerDate;// 注册时间

	Boolean member = false; // 是否会员
	
	Long cardUser; // 关联会员[CardUser]Id
}
