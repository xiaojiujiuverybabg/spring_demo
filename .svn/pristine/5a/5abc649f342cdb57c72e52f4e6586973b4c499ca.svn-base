/**
 * 
 */
package com.sjy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sjy.base.domain.SimpleOrg;
import com.sjy.base.service.SimpleOrgService;
import com.sjy.dao.RuleSetRepository;
import com.sjy.domain.RuleSet;
import com.sjy.model.CardUserVo;
import com.sjy.service.CrmUserService;
import com.sjy.service.RuleSetService;
import com.sjy.util.FormatUtil;
import com.sjy.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: RuleSetServiceImpl.java
 * @Package com.sjy.service.impl
 * @Description: 会员活动规则服务
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月10日 下午12:50:05
 * @version V1.0
 */
@Slf4j
@Transactional
@Service("ruleSetService")
public class RuleSetServiceImpl implements RuleSetService {

	@Resource
	RuleSetRepository ruleSetRepository;

	@Resource
	SimpleOrgService simpleOrgService;

	@Resource
	CrmUserService crmUserService;

	@Override
	public List<String> getRuleSetForRecommend(String appId, String openId) {
		int ruleType1 = 80201; // 推荐送积分
		int ruleType2 = 90111; // 推荐送优惠券
		int status = 1; // 正在运行
		Date currentDate = new Date();
		List<String> result = new ArrayList<String>(2);
		SimpleOrg org = simpleOrgService.getOrgByAppId(appId);
		log.debug("规则查询参数：机构={}", org.getName());
		List<RuleSet> ruleSets = ruleSetRepository.getRuleSetForRecommend(org, ruleType1, ruleType2, status,
				currentDate);
		String pointStr = "推荐一人成功注册会员";
		String couponStr = "推荐一人成功注册会员";
		CardUserVo owner = null;
		if (ruleSets != null) {
			for (RuleSet ruleSet : ruleSets) {
				JSONArray objs = JSON.parseArray(ruleSet.getRules());
				for (int i = 0; i < objs.size(); i++) {
					JSONObject obj = objs.getJSONObject(i);
					if (ruleType1 == ruleSet.getRuleType()) {
						log.debug("{}-{}-规则结果：{}", ruleSet.getRuleType(), i, obj.toJSONString());
						// 判断是否满足当前规则
						String cardUserLevel = obj.getString("cardUserLevel");
						String point = obj.getString("point");
						boolean match = false;
						if (cardUserLevel != null) {
							if (owner == null) {
								// 获取当前客户信息
								owner = crmUserService.findCardUser(appId, openId);
							}
							List<String> levels = StringUtil.splitForList(cardUserLevel, ",");
							for (String level : levels) {
								if (Integer.valueOf(level.trim()) == owner.getCurrentLevel()) {
									match = true;
									break;
								}
							}
						} else {
							match = true;
						}
						if (match) {
							pointStr += "赠送" + point + "积分;";
						}
					} else if (ruleType2 == ruleSet.getRuleType()) {
						JSONObject goodsAndResult = obj.getJSONObject("goodsAndResult");
						log.debug("{}-{}-规则结果：{}", ruleSet.getRuleType(), i, goodsAndResult.toJSONString());
						// 判断是否满足当前规则
						boolean match = false;
						String cardUserLevel = obj.getString("cardUserLevel");
						if (cardUserLevel != null) {
							if (owner == null) {
								// 获取当前客户信息
								owner = crmUserService.findCardUser(appId, openId);
							}
							List<String> levels = StringUtil.splitForList(cardUserLevel, ",");
							for (String level : levels) {
								if (Integer.valueOf(level.trim()) == owner.getCurrentLevel()) {
									match = true;
									break;
								}
							}
						} else {
							match = true;
						}
						if (match) {
							int numCount = goodsAndResult.getIntValue("numCount"); // 赠送数量
							Long discountAmount = goodsAndResult.getLongValue("discountAmount"); // 赠送面额
							Long discountRate = goodsAndResult.getLongValue("discountRate"); // 赠送折扣
							if (discountAmount != null && discountAmount > 0) {
								couponStr += "赠送" + numCount + "张" + FormatUtil.fenToYuan(discountAmount) + "优惠券;";
							}
							if (discountRate != null && discountRate > 0) {
								couponStr += "赠送" + numCount + "张" + FormatUtil.toDiscountRate(discountRate) + "折扣券;";
							}
						}
					}
				}
			}
		}
		if (!"推荐一人成功注册会员".equals(pointStr)) {
			result.add(pointStr);
		}
		if (!"推荐一人成功注册会员".equals(couponStr)) {
			result.add(couponStr);
		}
		return result;
	}

}
