package com.app.agriculturalproducts.util;

/**
 * http配置信息
 * @author 杨甲鯤
 * @date 2016-1-18
 */

public class Configure {
	//域名
	private static final String DOMAIN_NAME = "http://211.149.152.49/";

	//登陆判断
	public static final String LOGIN_URL = DOMAIN_NAME + "supervise/loginFromPhone.do";
	//修改密码
	public static final String UPDATE_PASSWORD_URL = DOMAIN_NAME + "supervise/updatePasswordFromPhone.do";
	//通过帐号获取员工信息
	public static final String GET_EMPLOYEE_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getEmployeeByUsernameFromPhone.do";
	//通过帐号获取田地信息
	public static final String GET_FIELD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getFieldByUsernameFromPhone.do";
	//通过帐号获取种植信息
	public static final String GET_PLANTRECORD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getPlantRecordByUsernameFromPhone.do";
	//通过帐号获取施肥信息
	public static final String GET_FERTILIZERECORD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getFertilizeRecordByUsernameFromPhone.do";
	//通过帐号获取防治信息
	public static final String GET_PREVENTIONRECORD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getPreventionRecordByUsernameFromPhone.do";
	//通过帐号获取采摘信息
	public static final String GET_PICKRECORD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getPickRecordByUsernameFromPhone.do";
	//通过帐号获取其他信息
	public static final String GET_OTHERRECORD_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getOtherRecordByUsernameFromPhone.do";
	//通过帐号获取库存信息
	public static final String GET_PERSONALSTOCK_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getPersonalStockByUsernameFromPhone.do";
	//通过帐号获取出库库存信息
	public static final String GET_OUTPERSONALSTOCKDETAIL_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getOutPersonalStockDetailByUsernameFromPhone.do";
	//通过帐号获取入库库存信息
	public static final String GET_ENTERPERSONALSTOCKDETAIL_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getEnterPersonalStockDetailByUsernameFromPhone.do";
	//通过帐号获取库存品种信息
	public static final String GET_SEED_PERSONALSTOCK_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getSeedFromPersonalStockByUsernameFromPhone.do";
	//通过帐号获取库存肥料信息
	public static final String GET_FERTILIZER_PERSONALSTOCK_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getFertilizerFromPersonalStockByUsernameFromPhone.do";
	//通过帐号获取库存农药信息
	public static final String GET_PESTICIDE_PERSONALSTOCK_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getPesticideFromPersonalStockByUsernameFromPhone.do";
	//添加种植信息
	public static final String ADD_PLANTRECORD_URL = DOMAIN_NAME + "supervise/addPlantRecordFromPhone.do";
	//添加施肥信息
	public static final String ADD_FERTILIZERECORD_URL = DOMAIN_NAME + "supervise/addFertilizeRecordFromPhone.do";
	//添加防治信息
	public static final String ADD_PREVENTIONRECORD_URL = DOMAIN_NAME + "supervise/addPreventionRecordFromPhone.do";
	//添加采摘信息
	public static final String ADD_PICKRECORD_URL = DOMAIN_NAME + "supervise/addPickRecordFromPhone.do";
	//添加其他信息
	public static final String ADD_OTHERRECORD_URL = DOMAIN_NAME + "supervise/addOtherRecordFromPhone.do";
	//通过帐号获取任务信息
	public static final String GET_WORKTASK_BY_USERNAME_URL = DOMAIN_NAME + "supervise/getWorkTaskByUsernameFromPhone.do";
	//修改密码
	public static final String UPDATE_WORKTASKLIST_STATUS_URL = DOMAIN_NAME + "supervise/updateWorkTaskListStatusFromPhone.do";
}
