/**
 * 
 */
package com.sjy.service;

import java.util.List;

/**
 * @Title: RuleSetService.java
 * @Package com.sjy.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月10日 下午12:49:54
 * @version V1.0
 */
public interface RuleSetService {

	/**
	 * 查询会员推荐权益
	 * 
	 * @param appId
	 * @param openId
	 * @return
	 */
	List<String> getRuleSetForRecommend(String appId, String openId);

}
