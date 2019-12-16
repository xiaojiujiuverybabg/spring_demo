package com.sjy.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;

import com.sjy.model.OrgChlVo;

public interface CrmChannelService {
	/**
	 * 生成机构二维码
	 * 
	 * @param orgId
	 * @return
	 */
	Map<String, Object> genOrgQrcode(String chlId);

	/**
	 * 新增、更新渠道信息
	 * 
	 * @param orgChlVo
	 * @return
	 */
	OrgChlVo saveChl(OrgChlVo orgChlVo);

	/**
	 * 获取渠道信息
	 * 
	 * @param orgChlVo
	 * @return
	 */
	OrgChlVo getChl(long chlId);

	/**
	 * 删除渠道信息
	 * 
	 * @param chlId
	 */
	void deleteChl(long chlId);

	/**
	 * 通过用户openId查询是否是通过关注推广渠道二维码关注
	 * 
	 * @param appId
	 * @param openId
	 * @return
	 */
	OrgChlVo getChlByWx(String appId, String openId);

	Map<String, Object> doDingYueEvent(String appId, String openId, String sceneId, String nickName);

	Map<String, Object> doScanEvent(String appId, String openId, String sceneId, String nickName);
	
	
	Map<String, Object> getChlDetailList(Long chlId);
	
	public Map<String, Object> getTradeInfoByOrgChl(Long orgChlId);

	public List<Map<String,Object>> getTableTypeListByOrgId(long orgId);
}
