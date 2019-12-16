package com.sjy.server;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sjy.constant.ChannelConstant;
import com.sjy.dao.OrgChlRepository;
import com.sjy.domain.OrgChl;
import com.sjy.model.UserChlVo;
import com.sjy.redis.RedisService;
import com.sjy.service.CrmUserService;
import com.sjy.service.UserChlService;
import com.sjy.table.config.PageResult;
import com.sjy.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

/**
 * @Title: UserChlServiceTest.java
 * @Package
 * @Description: UserChlService单元测试
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月29日 下午2:39:18
 * @version V1.0
 */
@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserChlServiceTest {

	@Resource
	RedisService redisService;

	@Resource
	CrmUserService crmUserService;

	@Resource
	UserChlService userChlService;

	@Resource
	OrgChlRepository orgChlRepository;

	@Test
	public void redisSetGet() {
		redisService.set(ChannelConstant.USER_CHL_PRE + "taskDate", "2018-01-29");
		redisService.set(ChannelConstant.USER_CHL_PRE + "taskDate", "2018-01-29");
		log.debug("{}", redisService.get(ChannelConstant.USER_CHL_PRE + "taskDate"));
	}

	@Test
	public void getOrgChl() {
		OrgChl orgChl = orgChlRepository.findByMobilePhone("17612226320", 100004L, 200);
		log.debug("{}", JSON.toJSONString(orgChl));
	}

	@Test
	public void assginTask() {
		int pageSize = 50;
		// Date taskDate = DateUtils.parse("2017-01-01", "yyyy-MM-dd");
		Date taskDate = DateUtils.parse("2018-01-01", "yyyy-MM-dd");
		while (taskDate.before(DateUtils.getToadyEnd())) {
			String key = userChlService.getKey(taskDate);
			long taskSize = redisService.getSetSize(key);
			log.debug("任务【AssignStatisticalChannelOfMemberJob】, taskDate = {}, taskSize = {}",
					DateUtils.format(taskDate, "yyyy-MM-dd"), taskSize);
			if (taskSize == 0) {
				String beginDate = DateUtils.format(taskDate, "yyyy-MM-dd");
				String endDate = DateUtils.format(taskDate, "yyyy-MM-dd");
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("beginDate", beginDate);
				params.put("endDate", endDate);
				// params.put("id", value);
				PageResult page = crmUserService.getUserInfoForChannel(1, pageSize, params);
				if (page.getTotal() > 0) {
					// 获取总的页码数
					int pageNo = page.getPageSize();
					for (int i = 1; i <= pageNo; i++) {
						JSONObject json = new JSONObject();
						json.put("pageNo", i);
						json.put("pageSize", pageSize);
						json.put("beginDate", beginDate);
						json.put("endDate", endDate);
						json.put("total", page.getContent().size());
						log.debug("分配获取会员数据任务：{}", json.toJSONString());
						redisService.pushObjForSet(key, json);
					}
				}
			}

			taskDate = DateUtils.getNextDay(taskDate);
		}
	}

	@Test
	public void redisKeys() {
		Set<Serializable> sets = redisService.keys("channel:user:2*");
		sets.forEach(key -> {
			long taskSize = redisService.getSetSize((String) key);
			log.debug("{}---{}", key, taskSize);
		});
	}

	@Test
	public void statisticalTask() {
		Set<Serializable> keys = redisService.keys(ChannelConstant.USER_CHL_PRE + "2*");
		keys.forEach(key -> {
			long taskSize = redisService.getSetSize((String) key);
			log.debug("{}---{}", key, taskSize);

			if (taskSize > 0) {
				JSONObject json = null;
				do {
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
							UserChlVo userChlVo = new UserChlVo();
							userChlVo.setAppId((String) userinfo.get("appId"));
							userChlVo.setOpenId((String) userinfo.get("openId"));
							userChlVo.setRegisterDate((Date) userinfo.get("registerDate"));
							userChlVo.setMember(true);
							userChlVo.setNickName((String) userinfo.get("name"));
							userChlService.saveUserChl(userChlVo, (Long) userinfo.get("orgId"),
									(Integer) userinfo.get("orgLevel"), (String) userinfo.get("phone"),
									(Long) userinfo.get("operId"));
						});
					} catch (Exception e) {
						log.error("处理渠道会员数据失败", e);
						if (json != null) {
							redisService.pushObjForSet((String) key, json);
						}
					}
				} while ((json = (JSONObject) redisService.popObjForSet((String) key)) != null);
			}
		});
	}

}
