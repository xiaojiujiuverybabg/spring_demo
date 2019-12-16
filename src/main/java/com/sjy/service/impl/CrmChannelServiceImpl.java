package com.sjy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sjy.base.domain.SimpleOrg;
import com.sjy.base.service.SimpleOrgService;
import com.sjy.constant.ChannelConstant;
import com.sjy.dao.OrgTableRepository;
import com.sjy.dao.TableTypeRepository;
import com.sjy.dao.UserTableRepository;
import com.sjy.domain.OrgChl;
import com.sjy.domain.OrgTable;
import com.sjy.domain.TableType;
import com.sjy.domain.UserTable;
import com.sjy.model.OrgChlVo;
import com.sjy.service.CrmChannelService;
import com.sjy.service.OrgChlService;
import com.sjy.service.UserChlService;
import com.sjy.service.WeixinMpService;
import com.sjy.util.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("crmChannelService")
public class CrmChannelServiceImpl implements CrmChannelService {
	@Resource
	OrgChlService orgChlService;

	@Resource
	UserChlService userChlService;

	@Resource
	WeixinMpService weixinMpService;

	@Resource
	SimpleOrgService simpleOrgService;

	@Resource
	OrgTableRepository orgTableRepository;

	@Resource
	UserTableRepository userTableRepository;
	
	@Resource
	TableTypeRepository tableTypeRepository;

	/**
	 * 生成机构二维码
	 * 
	 * @param orgId
	 * @return
	 */
	@Override
	public Map<String, Object> genOrgQrcode(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (id.indexOf("table") != -1) {// 桌位
				log.debug("id:{}  id.substring:{}", id, id.substring(5));
				long tableId = Long.parseLong(id.substring(5));
				OrgTable orgTable = orgTableRepository.findById(tableId);
				if (orgTable == null)
					throw new Exception("桌位信息不正确");
				SimpleOrg org = orgTable.getOrg();
				log.debug("是否已生成过二维码：{}", (orgTable.getWechatUrl() == null));
				log.debug("orgId:{}", org.getId());
				if (orgTable.getWechatUrl() == null) {
					// 发卡机构
					SimpleOrg issuerOrg = simpleOrgService.getIssuerOrg(org);
					if (issuerOrg == null)
						throw new Exception("发卡机构不存在");
					String wechatUrl = weixinMpService.genQrCode(issuerOrg.getAppId(), id);
					if (wechatUrl == null)
						throw new Exception("生成二维码失败");
					log.debug("wechatUrl：{}", wechatUrl);
					orgTable.setWechatUrl(wechatUrl);
					orgTableRepository.save(orgTable);
				}
				result.put("wechatUrl", orgTable.getWechatUrl());
				result.put("localUrl", orgTable.getLocalUrl());
				result.put("qrcodeimgurl", orgTable.getQrcodeImgUrl());
				log.debug("orgTable.wechatUrl：{}", orgTable.getWechatUrl());
				log.debug("orgTable.localUrl：{}", orgTable.getLocalUrl());
				log.debug("orgTable.qrcodeimgurl：{}", orgTable.getQrcodeImgUrl());
				result.put("success", 1);
				result.put("remark", "生成二维码成功");
			}
		} catch (Throwable t) {
			log.error("error:", t);
			result.put("success", 0);
			result.put("remark", t.getMessage());
		}
		return result;
	}

	@Override
	public OrgChlVo saveChl(OrgChlVo orgChlVo) {
		OrgChl orgChl = orgChlService.save(orgChlVo);
		return BeanUtils.copyBeanNotNull2Bean(orgChl, OrgChlVo.class);
	}

	@Override
	public OrgChlVo getChl(long chlId) {
		OrgChl orgChl = orgChlService.findOne(chlId);
		OrgChlVo orgChlVo = BeanUtils.copyBeanNotNull2Bean(orgChl, OrgChlVo.class);
		if (orgChl.getOper() != null) {
			orgChlVo.setOperId(orgChl.getOper().getId());
			orgChlVo.setOperName(orgChl.getOper().getName());
		}
		orgChlVo.setOrgId(orgChl.getOrg().getId());
		orgChlVo.setOrgName(orgChl.getOrg().getName());
		orgChlVo.setYiyeOrg(orgChl.getYiyeOrg());
		
		return orgChlVo;
	}

	@Override
	public void deleteChl(long chlId) {
		orgChlService.delete(chlId);
	}

	@Override
	public OrgChlVo getChlByWx(String appId, String openId) {
		OrgChl orgChl = userChlService.findOrgChlByWx(appId, openId);
		if (orgChl != null) {
			OrgChlVo orgChlVo = BeanUtils.copyBeanNotNull2Bean(orgChl, OrgChlVo.class);
			if (orgChl.getOper() != null) {
				orgChlVo.setOperId(orgChl.getOper().getId());
				orgChlVo.setOperName(orgChl.getOper().getName());
			}
			orgChlVo.setOrgId(orgChl.getOrg().getId());
			orgChlVo.setOrgName(orgChl.getOrg().getName());
			return orgChlVo;
		}
		return null;
	}

	@Override
	public Map<String, Object> doDingYueEvent(String appId, String openId, String sceneId, String nickName) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (sceneId.indexOf("table") != -1) {// 桌位
				log.info("doDingYueEvent--------appId:{},openId:{},sceneId:{},nickName:{}", appId, openId, sceneId,
						nickName);
				long tableId = Long.parseLong(sceneId.substring(5));
				OrgTable orgTable = orgTableRepository.findById(Long.valueOf(tableId));
				if (orgTable == null)
					throw new Exception("该桌位不存在");
				// 发卡机构
				SimpleOrg issuerOrg = simpleOrgService.getOrgByAppId(appId);
				log.info("issuerOrg:{}", issuerOrg);
				if (issuerOrg == null)
					throw new Exception("该机构不存在");

				UserTable userTable = userTableRepository.loadByAppIdAndOpenId(appId, openId);
				log.info("userTable:{}", userTable);
				if (userTable != null) {// throw new Exception("该用户已经绑定桌位");
					if (tableId == userTable.getTable().getId()) {// 相同桌位
						result.put("lastScanTime", userTable.getCreateTime().getTime());
					} else {
						result.put("lastScanTime", 0l);
					}
					log.info("start update userTable");
					userTable.setTable(orgTable);
					userTable.setCreateTime(new Date());
					userTableRepository.save(userTable);
					log.info("save userTable success");
					result.put("success", 1);
					result.put("remark", "更新成功");

					result.put("orgTableName", orgTable.getTableName().trim());
					result.put("orgName", orgTable.getOrg().getName().trim());
					return result;
				} else {
					log.info("start save userTable");
					UserTable newUserTable = new UserTable();
					newUserTable.setAppId(appId);
					newUserTable.setOpenId(openId);
					newUserTable.setTable(orgTable);
					if (nickName != null && !"".equals(nickName))
						newUserTable.setNickName(nickName);
					else
						newUserTable.setNickName("");
					newUserTable.setCreateTime(new Date());
					userTableRepository.save(newUserTable);
					log.info("save userchl success");
					result.put("success", 1);
					result.put("remark", "添加成功");
					result.put("lastScanTime", 0l);
					result.put("orgTableName", orgTable.getTableName().trim());
					result.put("orgName", orgTable.getOrg().getName().trim());
					return result;
				}
			} else {// 渠道
				log.info("doDingYueEvent--------appId:{},openId:{},sceneId:{},nickName:{}", appId, openId, sceneId,
						nickName);
				sceneId = sceneId.replaceAll(ChannelConstant.WX_SCAN_KEY, "");

				log.info("start save userchl");
				userChlService.saveUserChl(appId, openId, nickName, Long.valueOf(sceneId));
				log.info("save userchl success");

				result.put("success", 1);
				result.put("remark", "添加成功");
				return result;
			}

		} catch (Exception e) {
			log.error("doDingYueEvent fail:", e);
			result.put("success", 0);
			result.put("remark", e.getMessage());
			return result;
		}
	}

	@Override
	public Map<String, Object> doScanEvent(String appId, String openId, String sceneId, String nickName) {
		Map<String, Object> result = new HashMap<String, Object>();
		log.info("doScanEvent--------appId:{},openId:{},sceneId:{},nickName:{}", appId, openId, sceneId, nickName);
		try {
			if (sceneId.indexOf("table") != -1) {// 桌位
				log.info("doScanEvent--------appId:{},openId:{},sceneId:{},nickName:{}", appId, openId, sceneId,
						nickName);
				long tableId = Long.parseLong(sceneId.substring(5));
				OrgTable orgTable = orgTableRepository.findById(Long.valueOf(tableId));
				if (orgTable == null)
					throw new Exception("该桌位不存在");
				// 发卡机构
				SimpleOrg issuerOrg = simpleOrgService.getOrgByAppId(appId);
				log.info("issuerOrg:{}", issuerOrg);
				if (issuerOrg == null)
					throw new Exception("该机构不存在");

				UserTable userTable = userTableRepository.loadByAppIdAndOpenId(appId, openId);
				log.info("userTable:{}", userTable);
				if (userTable != null) {// throw new Exception("该用户已经绑定桌位");
					if (tableId == userTable.getTable().getId()) {
						result.put("lastScanTime", userTable.getCreateTime().getTime());
					} else {
						result.put("lastScanTime", 0l);
					}
					log.info("start update userTable");
					userTable.setTable(orgTable);
					userTable.setCreateTime(new Date());
					userTableRepository.save(userTable);
					log.info("save userTable success");
					result.put("success", 1);
					result.put("remark", "更新成功");
					result.put("orgTableName", orgTable.getTableName().trim());
					result.put("orgName", orgTable.getOrg().getName().trim());
					return result;
				} else {
					log.info("start save userTable");
					UserTable newUserTable = new UserTable();
					newUserTable.setAppId(appId);
					newUserTable.setOpenId(openId);
					newUserTable.setTable(orgTable);
					if (nickName != null && !"".equals(nickName))
						newUserTable.setNickName(nickName);
					else
						newUserTable.setNickName("");
					newUserTable.setCreateTime(new Date());
					userTableRepository.save(newUserTable);
					log.info("save userTable success");
					result.put("lastScanTime", 0l);
					result.put("orgTableName", orgTable.getTableName().trim());
					result.put("orgName", orgTable.getOrg().getName().trim());
					result.put("success", 1);
					result.put("remark", "添加成功");
					return result;
				}
			}
			result.put("success", 0);
			result.put("remark", "非扫描桌号");
			return result;

		} catch (Exception e) {
			log.error("doScanEvent fail:", e);
			result.put("success", 0);
			result.put("remark", e.getMessage());
			return result;
		}
	}

	@Override
	public Map<String, Object> getChlDetailList(Long chlId) {
		
		return userChlService.getChlDetailList(chlId);
	}
	public Map<String, Object> getTradeInfoByOrgChl(Long orgChlId){
		return userChlService.getTradeInfoByOrgChl(orgChlId);
	}
	@Override
	public List<Map<String, Object>> getTableTypeListByOrgId(long orgId) {
		List<Map<String, Object>> result=new ArrayList<Map<String,Object>>();
		List<TableType> ttList=tableTypeRepository.getTableTypeListByOrgId(orgId);
		if(!ttList.isEmpty()){
			for(TableType tt:ttList){
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", tt.getId());
				map.put("name", tt.getName());
				map.put("orgId", tt.getOrg().getId());
//				map.put("ifPay", tt.getIfPay());
//				map.put("money", tt.getMoney());
				result.add(map);
			}
		}
		return result;
	}
}
