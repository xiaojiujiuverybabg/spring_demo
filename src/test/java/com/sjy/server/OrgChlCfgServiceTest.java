/**
 * 
 */
package com.sjy.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.sjy.base.domain.SimpleOper;
import com.sjy.base.domain.SimpleOrg;
import com.sjy.domain.OrgChlCfg;
import com.sjy.service.OrgChlCfgService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: OrgChlCfgServiceTest.java
 * @Package com.sjy.server
 * @Description: OrgChlCfgService单元测试
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年2月5日 上午11:43:57
 * @version V1.0
 */
@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgChlCfgServiceTest {

	@Resource
	OrgChlCfgService orgChlCfgService;

	private static Map<String, ThreadLocal<SimpleDateFormat>> dateFormats = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static SimpleDateFormat getDateFormatter(final String format) {
		ThreadLocal<SimpleDateFormat> formatLocal = dateFormats.get(format);
		if (formatLocal == null) {
			formatLocal = new ThreadLocal<SimpleDateFormat>() {
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat(format);
				}
			};
			dateFormats.put(format, formatLocal);
		}
		return formatLocal.get();
	}

	@Rollback(false)
	@Test
	public void saveCfg() {
		OrgChlCfg cfg = new OrgChlCfg();
		cfg.setName("门店销售人员分润方案");
		cfg.setRecordDate(new Date());
		SimpleOrg org = new SimpleOrg();
		org.setId(200869L);
		cfg.setOrg(org);
		SimpleOper oper = new SimpleOper();
		oper.setId(200570L);
		cfg.setOper(oper);
		orgChlCfgService.saveCfg(cfg);
		log.debug("{}", cfg.getId());
	}

	@Test
	public void getCfg() {
		OrgChlCfg cfg = orgChlCfgService.getCfg("928ab133-8794-4a31-9c04-105ec9f15344");
		SimpleDateFormat formatter = getDateFormatter("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(cfg.getRecordDate());
		log.debug("{}", date);
	}

}
