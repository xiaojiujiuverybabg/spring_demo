/**
 * 
 */
package com.sjy.server;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.sjy.task.AssignStatisticalChannelOfMemberJob;
import com.sjy.task.StatisticalChannelOfMemberJob;
import com.sjy.task.dao.ScheduleJobRepository;
import com.sjy.task.domain.ScheduleJob;

/**
 * @Title: InitTask.java
 * @Package com.sjy.server
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月29日 下午7:28:53
 * @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitTask {

	@Resource
	ScheduleJobRepository scheduleJobRepository;

	@Test
	@Rollback(false)
	public void init() {
		List<ScheduleJob> jobs = new ArrayList<ScheduleJob>();
		ScheduleJob job1 = new ScheduleJob();
		job1.setJobClass(AssignStatisticalChannelOfMemberJob.class.getName());
		job1.setJobName("AssignStatisticalChannelOfMember");
		job1.setJobGroup("StatisticalChannel");
		job1.setJobStatus(1);
		job1.setJobDesc("查询会员数据，并且分配渠道统计任务");
		job1.setCronExpression("0/1 * * * * ?"); // 每秒钟执行一次
		jobs.add(job1);

		ScheduleJob job2 = new ScheduleJob();
		job2.setJobClass(StatisticalChannelOfMemberJob.class.getName());
		job2.setJobName("StatisticalChannelOfMember[1]");
		job2.setJobGroup("StatisticalChannel");
		job2.setJobStatus(1);
		job2.setJobDesc("统计推广渠道会员信息");
		job2.setCronExpression("0/1 * * * * ?");// 每秒钟执行一次
		jobs.add(job2);

		ScheduleJob job3 = new ScheduleJob();
		job3.setJobClass(StatisticalChannelOfMemberJob.class.getName());
		job3.setJobName("StatisticalChannelOfMember[2]");
		job3.setJobGroup("StatisticalChannel");
		job3.setJobStatus(1);
		job3.setJobDesc("统计推广渠道会员信息");
		job3.setCronExpression("0/1 * * * * ?");// 每秒钟执行一次
		jobs.add(job3);
		scheduleJobRepository.save(jobs);
	}

}
