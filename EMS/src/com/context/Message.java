package com.context;

public class Message {
	
	/**
	 * DB関連_共通
	 */
	public static final String NOT_FOUND_RESULT = "没有符合当前查寻条件的数据";
	public static final String UPDATE_NOT_FOUND = "更新失败，该数据已经被删除，请在查寻画面重新查寻确认";
	public static final String DELETE_NOT_FOUND = "削除失败，该数据已经被删除，请在查寻画面重新查寻确认";
	public static final String INPUT_ERROR = "提交失败，请根据提示修改后重新提交";
	public static final String INSERT_FAIL = "添加处理失败";
	public static final String INSERT_SUCCESS = "添加处理成功";
	public static final String UPDATE_FAIL = "更新处理失败";
	public static final String DELETE_FAIL = "削除处理失败";
	public static final String DELETE_SUCCESS = "削除处理成功";
	public static final String EMPTY_ERROR = "必须输入";
	public static final String ID_ERROR = "格式错误，请输入%s位数字";
	public static final String DUPLICATED_ERROR = "添加失败，该数据已经存在，请检查";
	public static final String PAGE_VALIDATE_ERROR = "页面内容验证有错误，请根据画面上提示修改后重新提交";
	public static final String SYNCHRONIZED_ERROR = "变更对象中引用的部分数据已经被修改，请回到上一页重新操作，以便获取最新的数据";
	
	public static final String NO_CUSTOMER_AUTH = "当前用户不是该客户的担当，无权操作该数据";
	public static final String PVC_UNMATCH = "当前订单中有产品的客户或者供应商信息存在不匹配的情况，请删除该产品重新添加。（产品型号 : %s, 版本 : %s）";
	public static final String USER_CATEGORY_AUTH_ERROR = "当前用户权限不足，不能选择该用户类型";
	public static final String TP_UNMATCH = "输入的订单号中不存在此产品（订单号 : %s, 产品型号 : %s, 版本 : %s）";
	public static final String USED_INQUIRY = "当前询单中有询价对象已经被其他询单引用，不能删除";
	public static final String USED_PRODUCTION = "当前产品信息已经被引用，不能删除";
	public static final String USED_CUSTOMER = "当前客户信息已经被引用，不能删除";
	public static final String USED_VENDOR = "当前供应商信息已经被引用，不能删除";
	public static final String USED_CODE = "当前Code信息已经被引用，不能删除";
	

	/**
	 * DB関連_用户
	 */
	public static final String SUCCESS_CREATE_USER = "成功添加用户 : ";
	public static final String SUCCESS_UPDATE_USER = "成功更新用户 : ";
	public static final String SUCCESS_DELETE_USER = "成功削除用户 : ";
	public static final String SUCCESS_UPDATE_PASSWORD = "成功更新密码";
	public static final String SUCCESS_PASSWORDRESET_USER = "成功重置密码，用户 : ";
	public static final String DUPLICATED_USER = "添加失败，该用户已经存在，请检查用户ID";
	public static final String PASSWORD_UNMATCH = "2次输入的新密码不一致，请重新输入";
	public static final String PASSWORD_SAME = "旧密码不能与新密码相同，请重新输入";
	public static final String PASSWORDOLD_WRONG = "旧密码不正确，请重新输入";
	
	/**
	 * DB関連_产品
	 */
	public static final String SUCCESS_CREATE_PRODUCTION = "成功添加产品。（产品型号 : %s, 版本 : %s）";
	public static final String SUCCESS_UPDATE_PRODUCTION = "成功更新产品。（产品型号 : %s, 版本 : %s）";
	public static final String SUCCESS_DELETE_PRODUCTION = "成功削除产品。（产品型号 : %s, 版本 : %s）";
	public static final String DUPLICATED_PRODUCTION = "添加失败，该产品已经存在，请检查产品ID";
	public static final String UPDATE_VERSION_PRODUCTION = "该数据已经被引用，请选择最新版本的数据进行变更";
	public static final String PRODUCTIONID_INPUTERROR = "产品型号不能包含双引号或单引号";
	
	/**
	 * DB関連_供应商
	 */
	public static final String SUCCESS_CREATE_VENDOR = "成功添加供应商 : ";
	public static final String SUCCESS_UPDATE_VENDOR = "成功更新供应商 : ";
	public static final String SUCCESS_DELETE_VENDOR = "成功削除供应商 : ";
	public static final String DUPLICATED_VENDOR = "添加失败，该供应商已经存在，请检查供应商ID";
	public static final String NOT_EXISTS_VENDOR = "引用的供应商信息不存在或已经被删除，请重新选择。（供应商ID : %s）";
	
	/**
	 * DB関連_客户
	 */
	public static final String SUCCESS_CREATE_CUSTOMER = "成功添加客户 : ";
	public static final String SUCCESS_UPDATE_CUSTOMER = "成功更新客户 : ";
	public static final String SUCCESS_DELETE_CUSTOMER = "成功削除客户 : ";
	public static final String DUPLICATED_CUSTOMER = "添加失败，该客户已经存在，请检查客户ID";
	public static final String NOT_EXISTS_CUSTOMER = "引用的客户信息不存在或已经被删除，请重新选择。（客户ID : %s）";
	
	/**
	 * DB関連_客户
	 */
	public static final String SUCCESS_CREATE_USER_CUSTOMER = "成功添加客户担当信息 : ";
	public static final String SUCCESS_UPDATE_USER_CUSTOMER = "成功更新客户担当信息 : ";
	public static final String SUCCESS_DELETE_USER_CUSTOMER = "成功削除客户担当信息 : ";
	
	/**
	 * DB関連_订单
	 */
	public static final String SUCCESS_CREATE_ORDER = "成功添加订单 : ";
	public static final String SUCCESS_UPDATE_ORDER = "成功更新订单 : ";
	public static final String SUCCESS_DELETE_ORDER = "成功削除订单 : ";
	public static final String DUPLICATED_PRODUCTION_IN_ORDER = "该产品已经在订单中了，无法重复添加（产品型号：%s，版本：%s）";
	public static final String TRADEORDER_DELETE_ERROR_1 = "该订单有发货记录，无法删除，如果要执行删除操作，必须先删除相关发货记录。";
	public static final String TRADEORDER_DELETE_ERROR_2 = "该订单有投诉记录，无法删除，如果要执行删除操作，必须先删除相关投诉记录。";
	public static final String CONTRACTNO_ERROR = "合同号输入不正确，格式：RP[2位年份]-[3位客户编号]-[2位序号]+空格+客户简称";
	public static final String DUPLICATED_CONTRACTNO = "该合同号已经存在，无法添加重复合同号的订单";
	public static final String DUPLICATED_PO = "该PO已经存在，无法添加重复PO的订单";
	public static final String WRONG_QUANTITY = "和分批发货总数不等";
	public static final String WRONG_ORDER = "请按顺序填写";
	public static final String NOT_EXISTS_INQUIRY = "询价信息不存在（产品型号：%s，版本：%s，美金单价：%s）";
	public static final String NOT_INPUT_INQUIRY = "询单编号必须输入";
	public static final String NOT_FOUND_INQUIRY = "该询单编号不存在，价格自动设定失败";
	public static final String CUSTOMER_INPUT_INQUIRY = "询单编号的客户和当前订单的客户不一致";
	
	/**
	 * DB関連_询单
	 */
	public static final String SUCCESS_CREATE_INQUIRY = "成功添加询单 : ";
	public static final String SUCCESS_UPDATE_INQUIRY = "成功更新询单 : ";
	public static final String SUCCESS_DELETE_INQUIRY = "成功削除询单 : ";
	public static final String DUPLICATED_INQUIRY = "添加失败，该询单已经存在，请检查询单ID";
	public static final String DUPLICATED_PRODUCTION_IN_INQUIRY = "该产品已经在询单中了，无法重复添加（产品型号：%s，版本：%s）";
	public static final String WRONG_TAX_RETURN = "产品退税率只能是10%%，13%%，否则无法计算本次买断价（产品型号：%s，版本：%s）";
	
	/**
	 * DB関連_发货单
	 */
	public static final String SUCCESS_CREATE_RECEIPT = "成功添加发货单 : ";
	public static final String SUCCESS_UPDATE_RECEIPT = "成功更新发货单 : ";
	public static final String SUCCESS_DELETE_RECEIPT = "成功削除发货单 : ";
	public static final String DUPLICATED_RECEIPT = "添加失败，该发货单已经存在，请检查发货单ID";
	public static final String DUPLICATED_PRODUCTION_IN_RECEIPT = "该产品已经在发货单中了，无法重复添加（订单号：%s，产品型号：%s，版本：%s）";
	public static final String DUPLICATED_PRODUCTION_IN_RECEIPT_4C = "该产品已经在发货单中了，无法重复添加（投诉单号：%s，订单号：%s，产品型号：%s，版本：%s）";
	public static final String NOT_EXISTS_PRODUCTION_IN_RECEIPT = "该订单产品不存在或已从订单中删除，无法作为发货对象保存（订单号：%s，产品型号：%s，版本：%s）";
	public static final String HAS_NOCONFIRMED_TRADEORDER_4_R = "该发货单明细中存在未确定的订单，请先将订单确定后在操作该发货单（未确定的订单号：%s）";
	public static final String DUPLICATED_RECEIPTNO = "该发票号码已经存在，无法添加重复发票号码的发货单";
	public static final String NO_NEED_RELATE_RECEIPTNO = "需要报关的发货单不能填写关联报关发票号码";
	public static final String NOT_EXISTS_RELATE_RECEIPTNO = "填写的关联报关发票号码不存在，或不是该用户的发票号码";
	public static final String RELATE_RECEIPTNO = "该发货单存在关联报关，无法删除或改成无需报关";
	public static final String RECEIPTNO_ERROR_0 = "发票号码输入不正确，非报关发票号码不能以该格式输入。格式：[2位年份]YCFT18[3位序号]";
	public static final String RECEIPTNO_ERROR_1 = "发票号码输入不正确，报关发票号码必须以该格式输入。格式：[2位年份]YCFT18[3位序号]";
	
	/**
	 * DB関連_投诉
	 */
	public static final String SUCCESS_CREATE_COMPLAINT = "成功添加投诉 : ";
	public static final String SUCCESS_UPDATE_COMPLAINT = "成功更新投诉 : ";
	public static final String SUCCESS_DELETE_COMPLAINT = "成功削除投诉 : ";
	public static final String DUPLICATED_COMPLAINT = "添加失败，该投诉已经存在，请检查投诉编号";
	public static final String DUPLICATED_PRODUCTION_IN_COMPLAINT = "该产品已经在投诉单中了，无法重复添加（产品型号：%s，版本：%s）";
	public static final String WRONG_ALERTDATEFROM = "提醒开始日期必须设置在投诉处理期限之前";
	public static final String NOT_FOUND_PO = "没有查到包含该产品，且状态是确定或完成的订单(PO：%s, 产品型号：%s，版本：%s)";
	public static final String NOT_FOUND_TRADRORDERID = "没有查到包含该产品，且状态是确定或完成的订单(订单编号：%s, 产品型号：%s，版本：%s)";
	public static final String ADD_MULTI = "当前投诉单明细中存在产品型号，版本，PO，处理方式都相同的重复数据，请确认后删除多余的数据<br/>（产品型号：%s，版本：%s，PO：%s，处理方式：%s）";
	public static final String INPUT_ERROR_4_NOREF = "没有引入订单信息，不能填写";
	public static final String NOT_INPUT_ERROR = "选择退货或退款的话，必须填写";
	public static final String HAS_NOCONFIRMED_TRADEORDER_4_C = "该投诉单明细中存在未确定的订单，请先将订单确定后在操作该投诉单（未确定的订单号：%s）";
	public static final String COMPLAINT_DELETE_ERROR = "该投诉单有发货记录，无法删除，如果要执行删除操作，必须先删除相关发货记录。";
	
	/**
	 * 画面関連_共通
	 */
	public static final String DATE_IUPUT_ERROR = "日期格式不正确，请按照以下格式输入，例：1983-02-27";
	public static final String DOUBLE_IUPUT_ERROR = "请输入浮点数";
	public static final String MAIL_IUPUT_ERROR = "请输入正确的邮箱地址格式";
	public static final String NO_ZERO_IUPUT_ERROR = "不能为0";
	public static final String RATE_IUPUT_ERROR = "请输入正确的数字形式（小数最多2位），不能大于100";
	public static final String INT_IUPUT_ERROR = "请输入整数";
	public static final String USERID_INPUT_ERROR = "只能是数字+字母，长度最短4位，最长16位";
	public static final String PASSWORD_INPUT_ERROR = "只能是数字+字母，长度最短6位，最长16位";
	public static final String DUPLICATESUBMIT_ERROR = "该页面已经过期，请按<a id=\"topMenu\" href=\"#\">返回</a>回到主页 : ";
	public static final String ENTER = "</br>";
	
	/**
	 * 画面関連_主页
	 */
	public static final String ALERT_UNREPLY = "投诉单编号：%s 还没有反馈，请及时处理";
	public static final String ALERT_UNDEAL = "投诉单编号：%s 还没有处理完，请在%s 之前处理完";
	
	/**
	 * 画面関連_用户
	 */
	public static final String USERNAME_EMPTY_ERROR = "用户的中文名和英文名不能都为空";
	
	/**
	 * 画面関連_订单
	 */
	public static final String PRODUCTION_EMPTY_ERROR = "请先选择产品";
	public static final String ADVANCEPAYMENT_COMBO_ERROR = "已预付的情况，相关项目都要输入";
	public static final String DELETE_STATUS_ERROR = "该订单已经被确定或完成，无法删除";
	public static final String COMPLETE_STATUS_ERROR = "该订单有产品未发完，无法改变成完成状态";
	
	/**
	 * 画面関連_投诉
	 */
	public static final String WRONG_TRADEORDERID = "订单号输入有误，该订单中不存在该产品或者该订单状态未确定";
	
	/**
	 * 画面関連_数据分析
	 */
	public static final String YEAR_REQUIRED = "年份必须选择";
	public static final String RATE_REQUIRED = "汇率必须输入";
	public static final String RATE_FORMAT_ERROR = "汇率请输入数字（最多2位小数）";
	
	/**
	 * 画面関連_登录
	 */
	public static final String LOGINID_IUPUT_ERROR = "请输入用户ID";
	public static final String PASSWORD_IUPUT_ERROR = "请输入密码";
	public static final String WRONG_USERID_PASSWORD = "用户名或密码不正确， 请重新输入";
	
	/**
	 * 画面标题
	 */
	public static final String PAGETITLE_TOPMENU = "主页";
	
	public static final String PAGETITLE_USERSEARCH = "用户信息检索";
	public static final String PAGETITLE_USERUPDATE = "用户信息变更";
	public static final String PAGETITLE_USERADD = "用户信息添加";
	public static final String PAGETITLE_USERREF = "用户信息查看";
	
	public static final String PAGETITLE_PRODUCTIONSEARCH = "产品信息检索";
	public static final String PAGETITLE_PRODUCTIONUPDATE = "产品信息变更";
	public static final String PAGETITLE_PRODUCTIONADD = "产品信息添加";
	public static final String PAGETITLE_PRODUCTIONREF = "产品信息查看";
	
	public static final String PAGETITLE_VENDORSEARCH = "供应商信息检索";
	public static final String PAGETITLE_VENDORUPDATE = "供应商信息变更";
	public static final String PAGETITLE_VENDORADD = "供应商信息添加";
	public static final String PAGETITLE_VENDORREF = "供应商信息查看";
	
	public static final String PAGETITLE_CUSTOMERSEARCH = "客户信息检索";
	public static final String PAGETITLE_CUSTOMERUPDATE = "客户信息变更";
	public static final String PAGETITLE_CUSTOMERADD = "客户信息添加";
	public static final String PAGETITLE_CUSTOMERREF = "客户信息查看";
	
	public static final String PAGETITLE_CODESEARCH = "Code信息检索";
	public static final String PAGETITLE_CODEUPDATE = "Code信息变更";
	public static final String PAGETITLE_CODEADD = "Code信息添加";
	
	public static final String PAGETITLE_USER_CUSTOMERSEARCH = "客户担当信息检索";
	public static final String PAGETITLE_USER_CUSTOMERUPDATE = "客户担当信息变更";
	public static final String PAGETITLE_USER_CUSTOMERADD = "客户担当信息添加";
	public static final String PAGETITLE_USER_CUSTOMERREF = "客户担当信息查看";
	
	public static final String PAGETITLE_ORDERSEARCH = "订单信息检索";
	public static final String PAGETITLE_ORDERUPDATE = "订单信息变更";
	public static final String PAGETITLE_ORDERADD = "订单信息添加";
	public static final String PAGETITLE_ORDERREF = "订单信息查看";
	
	public static final String PAGETITLE_INQUIRYSEARCH = "询单信息检索";
	public static final String PAGETITLE_INQUIRYUPDATE = "询单信息变更";
	public static final String PAGETITLE_INQUIRYADD = "询单信息添加";
	public static final String PAGETITLE_INQUIRYREF = "询单信息查看";
	
	public static final String PAGETITLE_RECEIPTSEARCH = "发货单信息检索";
	public static final String PAGETITLE_RECEIPTUPDATE = "发货单信息变更";
	public static final String PAGETITLE_RECEIPTADD = "发货单信息添加";
	public static final String PAGETITLE_RECEIPTREF = "发货单信息查看";
	
	public static final String PAGETITLE_COMPLAINTSEARCH = "投诉信息检索";
	public static final String PAGETITLE_COMPLAINTUPDATE = "投诉信息变更";
	public static final String PAGETITLE_COMPLAINTADD = "投诉信息添加";
	public static final String PAGETITLE_COMPLAINTREF = "投诉信息查看";
	
	public static final String PAGETITLE_ANALYSIS = "数据分析";

}
