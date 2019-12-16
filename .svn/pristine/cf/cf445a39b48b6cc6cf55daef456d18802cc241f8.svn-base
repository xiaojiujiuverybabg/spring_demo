/**
 * 
 */
package com.sjy.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sjy.dao.OrgChlCfgItemRepository;
import com.sjy.dao.OrgChlCfgRepository;
import com.sjy.dao.OrgChlTradeRepository;
import com.sjy.domain.OrgChlCfg;
import com.sjy.domain.OrgChlCfgItem;
import com.sjy.service.OrgChlCfgService;

/**
 * @Title: OrgChlCfgServiceImpl.java
 * @Package com.sjy.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年1月30日 下午2:43:58
 * @version V1.0
 */
@Transactional
@Service("orgChlCfgService")
public class OrgChlCfgServiceImpl implements OrgChlCfgService {

	@Resource
	OrgChlCfgRepository orgChlCfgRepository;

	@Resource
	OrgChlCfgItemRepository orgChlCfgItemRepository;

	@Resource
	OrgChlTradeRepository orgChlTradeRepository;

	@Override
	public OrgChlCfg getCfg(String cfgId) {
		return orgChlCfgRepository.findOne(cfgId);
	}

	@Override
	public OrgChlCfg saveCfg(OrgChlCfg cfg) {
		// TODO Auto-generated method stub
		return orgChlCfgRepository.save(cfg);
	}

	@Override
	public OrgChlCfgItem saveCfgItem(OrgChlCfgItem cfgItem) {
		// TODO Auto-generated method stub
		return null;
	}

}
