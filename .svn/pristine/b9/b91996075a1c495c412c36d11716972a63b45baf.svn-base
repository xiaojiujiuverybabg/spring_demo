/**
 * 
 */
package com.sjy.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sjy.domain.OrgChl;
import com.sjy.service.OrgChlService;
import com.sjy.service.RuleSetService;
import com.sjy.service.UserChlService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: OrgChlController.java
 * @Package com.sjy.controller
 * @Description: 渠道二维码服务API
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月9日 下午6:51:15
 * @version V1.0
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class OrgChlController {

	@Resource
	OrgChlService orgChlService;

	@Resource
	UserChlService userChlService;

	@Resource
	RuleSetService ruleSetService;

	@ApiOperation(value = "获取推荐渠道二维码信息", notes = "获取推荐渠道二维码信息")
	@PostMapping("/orgChl")
	@ResponseBody
	public OrgChl getCfg(@RequestParam("appId") String appId, @RequestParam("openId") String openId) {
		return orgChlService.getOrgChl(appId, openId);
	}

	@ApiOperation(value = "获取推荐渠道二维码关注和注册用户数据", notes = "获取推荐渠道二维码关注和注册用户数据")
	@GetMapping("/userChl/{orgChlId}")
	@ResponseBody
	public Map<String, Object> getUserChl(@PathVariable("orgChlId") Long orgChlId) {
		// 获取关注人数+注册人数
		return userChlService.getUserChlByOrgChl(orgChlId);
	}

	@ApiOperation(value = "获取推荐用户注册成功权益", notes = "获取推荐用户注册成功权益")
	@PostMapping("/getRuleSetForRecommend")
	@ResponseBody
	public List<String> getRuleSetForRecommend(@RequestParam("appId") String appId,
			@RequestParam("openId") String openId) {
		try {
			return ruleSetService.getRuleSetForRecommend(appId, openId);
		} catch (Exception e) {
			log.error("获取推荐用户注册成功权益失败", e);
			return null;
		}
	}

	@ApiOperation(value = "获取推荐人信息", notes = "获取推荐人信息")
	@PostMapping("/getRecommender")
	@ResponseBody
	public Map<String, Object> getRecommender(@RequestParam("appId") String appId,
			@RequestParam("openId") String openId) {
		return userChlService.getRecommender(appId, openId);
	}
}
