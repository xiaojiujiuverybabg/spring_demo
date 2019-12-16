/**
 * 
 */
package com.sjy.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjy.domain.OrgChlCfg;
import com.sjy.domain.OrgChlCfgItem;
import com.sjy.service.OrgChlCfgService;

import io.swagger.annotations.ApiOperation;

/**
 * @Title: OrgChlCfgController.java
 * @Package com.sjy.controller
 * @Description: 分润规则服务API
 * @author liyan
 * @email 289149734@qq.com
 * @date 2018年2月5日 上午8:43:33
 * @version V1.0
 */
@RestController
@RequestMapping("/api")
public class OrgChlCfgController {

	@Resource
	OrgChlCfgService orgChlCfgService;

	@ApiOperation(value = "保存渠道分润规则信息", notes = "保存渠道分润规则信息")
	@PostMapping("/orgChlCfg")
	public OrgChlCfg saveCfg(OrgChlCfg cfg) {
		return orgChlCfgService.saveCfg(cfg);
	}

	@ApiOperation(value = "保存渠道分润规则明细信息", notes = "保存渠道分润规则明细信息")
	@PostMapping("/orgChlCfgItem")
	public OrgChlCfgItem saveCfgItem(OrgChlCfgItem cfgItem) {
		return orgChlCfgService.saveCfgItem(cfgItem);
	}

}
