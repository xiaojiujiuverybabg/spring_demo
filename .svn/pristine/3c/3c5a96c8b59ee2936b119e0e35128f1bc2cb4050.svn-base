/**
 * 
 */
package com.sjy.task;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjy.constant.ChannelConstant;
import com.sjy.model.CardUserVo;
import com.sjy.model.UserChlVo;
import com.sjy.redis.RedisService;
import com.sjy.service.CrmUserService;
import com.sjy.service.UserChlService;
import com.sjy.table.config.PageResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: StatisticalChannelInfoJob.java
 * @Package com.sjy.task
 * @Description: 统计推广渠道会员信息
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月27日 下午4:50:41
 * @version V1.0
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class StatisticalChannelOfMemberJob implements Job {

	@Resource
	RedisService redisService;

	@Resource
	CrmUserService crmUserService;

	@Resource
	UserChlService userChlService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Set<Serializable> keys = redisService.keys(ChannelConstant.USER_CHL_PRE + "2*");
		if (keys != null && !keys.isEmpty()) {
			keys.stream().forEach(key -> {
				long taskSize = redisService.getSetSize((String) key);
				log.debug("{}---{}", key, taskSize);

				if (taskSize > 0) {
					JSONObject json = null;
					do {
						if (json != null) {
							try {
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("beginDate", json.getString("beginDate"));
								params.put("endDate", json.getString("endDate"));
								// params.put("id", value);
								int pageNo = json.getIntValue("pageNo");
								int pageSize = json.getIntValue("pageSize");
								PageResult page = crmUserService.getUserInfoForChannel(pageNo, pageSize, params);
								page.getContent().forEach(userinfo -> {
									log.debug("{}", JSON.toJSONString(userinfo));
									int count = userChlService.countUserChlByAppIdAndOpenId((String)userinfo.get("appId"),(String)userinfo.get("openId"));
									log.info("count=="+count);
									if(count <= 1){										
										UserChlVo userChlVo = new UserChlVo();
										userChlVo.setAppId((String) userinfo.get("appId"));
										userChlVo.setOpenId((String) userinfo.get("openId"));
										userChlVo.setRegisterDate((Date) userinfo.get("registerDate"));
										userChlVo.setMember(true);
										userChlVo.setNickName((String) userinfo.get("name"));
										userChlVo.setCardUser((Long) userinfo.get("id"));
										userChlService.saveUserChl(userChlVo, (Long) userinfo.get("orgId"),
												(Integer) userinfo.get("orgLevel"), (String) userinfo.get("phone"),
												(Long) userinfo.get("operId"));
									}
									crmUserService.updateChecked((Long)userinfo.get("id"), true);
								});
							} catch (Exception e) {
								log.error("处理渠道会员数据失败", e);
								if (json != null) {
									redisService.pushObjForSet((String) key, json);
								}
							}
						}
					} while ((json = (JSONObject) redisService.popObjForSet((String) key)) != null);
				}
			});
		}
	}

}
