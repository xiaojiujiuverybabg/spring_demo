/**
 * 
 */
package com.sjy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sjy.domain.OrgChl;
import com.sjy.domain.UserChl;
import com.sjy.model.UserChlVo;

/**
 * @Title: UserChlService.java
 * @Package com.sjy.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月29日 下午8:23:53
 * @version V1.0
 */
public interface UserChlService {

	String getKey(Date taskDate);

	OrgChl findOrgChlByWx(String appId, String openId);

	UserChlVo findUserChlByWx(String appId, String openId);
	
	int countUserChlByAppIdAndOpenId(String appId, String openId);

	void saveUserChl(UserChlVo userChlVo, Long orgId, Integer orgLevel, String mobilePhone, Long operId);

	/**
	 * 扫码关注用户
	 * 
	 * @param appId
	 * @param openId
	 * @param nickName
	 * @param orgChlId
	 */
	void saveUserChl(String appId, String openId, String nickName, Long orgChlId);

	/**
	 * 获取渠道关注人数和注册人数
	 * 
	 * @param orgchlId
	 * @return
	 */
	Map<String, Object> getUserChlByOrgChl(Long orgchlId);

	/**
	 * 获取推荐人信息
	 * 
	 * @param appId
	 * @param openId
	 * @return
	 */
	Map<String, Object> getRecommender(String appId, String openId);
	
	public Map<String, Object> getChlDetailList(Long chlId);
	
	public Map<String, Object> getTradeInfoByOrgChl(Long orgChlId);
}
