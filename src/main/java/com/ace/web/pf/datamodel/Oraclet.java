package com.ace.web.pf.datamodel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.ace.web.pf.dtomodel.ReportRequestModel;
import com.ace.web.pf.util.ApplicationData;

import oracle.sql.TIMESTAMP;


public class Oraclet {
	private String BANK_IDNAME;
	private String LEDGER_NO;
	private String CUR_CODE;
	private TIMESTAMP VD_DATE;

	public String getBANK_IDNAME() {
		return BANK_IDNAME;
	}

	public void setBANK_IDNAME(String bANK_IDNAME) {
		BANK_IDNAME = bANK_IDNAME;
	}

	public String getLEDGER_NO() {
		return LEDGER_NO;
	}

	public void setLEDGER_NO(String lEDGER_NO) {
		LEDGER_NO = lEDGER_NO;
	}

	public String getCUR_CODE() {
		return CUR_CODE;
	}

	public void setCUR_CODE(String cUR_CODE) {
		CUR_CODE = cUR_CODE;
	}

	public TIMESTAMP getVD_DATE() {
		return VD_DATE;
	}

	public void setVD_DATE(TIMESTAMP vD_DATE) {
		VD_DATE = vD_DATE;
	}

	public int getAPP_AMT() {
		return APP_AMT;
	}

	public void setAPP_AMT(int aPP_AMT) {
		APP_AMT = aPP_AMT;
	}

	private int APP_AMT;
	
//	@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
//	Map<String, Object> parameters = new HashMap<>();
//	parameters.put("start_date", startDate);
//	parameters.put("end_date", endDate);
//	ReportRequestModel request = new ReportRequestModel("test_report", "Test Report");
//	request.setMetaDataTitle("Test Report");
//	request.setParameters(parameters);
//	model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);

}
