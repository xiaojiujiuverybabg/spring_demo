/**
 * 
 */
package com.sjy.server;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sjy.dao.OrgChlRepository;
import com.sjy.domain.OrgChl;
import com.sjy.model.OrgChlVo;
import com.sjy.service.OrgChlService;

/**
 * @Title: OrgChlServiceTest.java
 * @Package com.sjy.server
 * @Description: OrgChlService单元测试
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年4月11日 下午6:55:47
 * @version V1.0
 */
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgChlServiceTest {

	@Resource
	OrgChlService orgChlService;
	
	@Resource
	OrgChlRepository orgChlRepository;

	@Test
	public void saveOrgChl() {
		/**
		 * {id:'200321',orgId:'100011',operId:'100005',orgName:'宣武门店  ',chlName:'李奈斯发展用户',operName:'众联宣武门店长姜辉',mobilePhone:'',bankName:'',bankNo:''}
		 */
		OrgChlVo orgChlVo = new OrgChlVo();
		orgChlVo.setId(200321L);
		orgChlVo.setOrgId(100011L);
		orgChlVo.setOperId(100005L);
		orgChlVo.setChlName("李奈斯发展用户");
		orgChlVo.setMobilePhone("17612226320");
		orgChlService.save(orgChlVo);
		
		OrgChl orgChlDb = orgChlRepository.findByOperId(orgChlVo.getOperId());
	}
}
