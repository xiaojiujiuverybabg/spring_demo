/**
 * 
 */
package com.sjy.server;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sjy.service.RuleSetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: RuleSetServiceTest.java
 * @Package com.sjy.server
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月10日 下午1:18:47
 * @version V1.0
 */
@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleSetServiceTest {

	@Resource
	RuleSetService ruleSetService;

	@Test
	public void getRuleSetForRecommend() {
		String appId = "wx433b05bbcc5778da";
		String openId = "oBBEBwUWtRidp-toO4Te4_1sHWM8";
		List<String> list = ruleSetService.getRuleSetForRecommend(appId, openId);
		list.forEach(l -> {
			log.debug(l);
		});
	}
}
