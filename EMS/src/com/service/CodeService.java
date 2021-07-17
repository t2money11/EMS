package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.context.Message;
import com.entity.Code;
import com.exception.ValiationException;
import com.logic.CodeLogic;
import com.util.DbCommonUtil;

@Service
public class CodeService{
	
	private CodeLogic codeLogic = null;
	public void setCodeLogic(CodeLogic codeLogic) {
		this.codeLogic = codeLogic;
	}
	
	@Autowired
	private ValidationAPIService validationAPIService = null;
	
	public Code codeSearch(Code code) throws Exception{
		
		int totalCount = 0;
		
		List<Code> codeList = codeLogic.findCodeByCategoryId(code);
		totalCount = codeList.size();
		if(totalCount != 0){
			
			code.setResultCodeList(codeList);
		}else{
			throw new Exception(Message.NOT_FOUND_RESULT);
		}
		code.getPageInfo().setTotalCount(totalCount);
		
        return code;
    }
	
	@Transactional(rollbackFor=Exception.class)
    public String add(Code code) throws Exception{
		
		//重复主键确认
		if(codeLogic.findByKey(code) != null){
			throw new Exception(Message.DUPLICATED_ERROR);
		}
		DbCommonUtil.setCreateCommon(code);
		try {
			codeLogic.insert(code);
		} catch (Exception e) {
			throw new Exception(Message.INSERT_FAIL);
		}
		return code.getCodeId();
    }
	
	@Transactional(rollbackFor=Exception.class)
    public void delete(String codeIdSelected) throws Exception{
		
		Code code = new Code();
		code.setCodeId(codeIdSelected);
		int count = 0;
		try {
			//删除客户之前，验证客户是否已经被引用
			if(validationAPIService.validateCode(codeIdSelected)){
				throw new ValiationException(Message.USED_CODE);
			}
			count = codeLogic.delete(code);
		} catch (ValiationException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception(Message.DELETE_FAIL);
		}
		if(count == 0){
			throw new Exception(Message.DELETE_NOT_FOUND);
		}
    }
	
}
