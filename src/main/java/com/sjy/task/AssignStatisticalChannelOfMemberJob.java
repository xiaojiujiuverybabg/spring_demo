/**
 * 
 */
package com.sjy.task;

import java.io.Serializable;
import java.util.Calendar;
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

import com.alibaba.fastjson.JSONObject;
import com.sjy.constant.ChannelConstant;
import com.sjy.model.UserChlVo;
import com.sjy.redis.RedisService;
import com.sjy.service.CrmUserService;
import com.sjy.service.UserChlService;
import com.sjy.table.config.PageResult;
import com.sjy.util.DateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: AssignStatisticalChannelOfMemberJob.java
 * @Package com.sjy.task
 * @Description: 查询会员数据，并且分配渠道统计任务
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月29日 上午11:32:23
 * @version V1.0
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class AssignStatisticalChannelOfMemberJob implements Job {

	@Resource
	RedisService redisService;

	@Resource
	CrmUserService crmUserService;

	@Resource
	UserChlService userChlService;

	private final static int pageSize = 50;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Set<Serializable> keys = redisService.keys(ChannelConstant.USER_CHL_PRE + "2*");
		if (keys == null || keys.isEmpty()) {
			// 任务处理时间提前15分钟，防止服务区时差导致数据不全
			Date taskDate = DateUtils.getToadyStart();
			if (redisService.exists(ChannelConstant.Task_Date_Key)) {
				taskDate = DateUtils.parse((String) redisService.get(ChannelConstant.Task_Date_Key),
						"yyyy-MM-dd HH:mm:ss");
			}

			try {
				while (taskDate.before(DateUtils.getToadyEnd())) {
					String key = userChlService.getKey(taskDate);
					long setSize = redisService.getSetSize(key);
					if (setSize == 0) {
						String beginDate = DateUtils.format(DateUtils.getNextTime(taskDate, Calendar.MINUTE, -15),
								"yyyy-MM-dd HH:mm:ss");
						String endDate = DateUtils.format(DateUtils.getDayEndTime(taskDate), "yyyy-MM-dd HH:mm:ss");
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("beginDate", beginDate);
						params.put("endDate", endDate);
						// params.put("id", value);
						PageResult page = crmUserService.getUserInfoForChannel(1, pageSize, params);
						// 获取总的页码数
						int pageNo = page.getPageSize();
						for (int i = 1; i <= pageNo; i++) {
							Map<String,Object> userinfo = page.content.get(i-1);
							UserChlVo userChlVo = userChlService.findUserChlByWx((String) userinfo.get("appId"), (String) userinfo.get("openId"));
							if(userChlVo != null && userChlVo.getMember()){
								continue;
							}
							JSONObject json = new JSONObject();
							json.put("pageNo", i);
							json.put("pageSize", pageSize);
							json.put("beginDate", beginDate);
							json.put("endDate", endDate);
							log.debug("分配获取会员数据任务：{}", json.toJSONString());
							redisService.pushObjForSet(key, json);
						}
					}

					taskDate = DateUtils.getNextDay(taskDate);
				}

				if (taskDate.after(new Date())) {
					taskDate = new Date();
				}
			} catch (Exception e) {
				log.error("分配获取会员数据任务失败", e);
			} finally {
				redisService.set(ChannelConstant.Task_Date_Key, DateUtils.format(taskDate, "yyyy-MM-dd HH:mm:ss"));
			}
		}
	}

}
