package com.ace.web.pf.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.ace.web.pf.datamodel.Student;
import com.ace.web.pf.datamodel.Test;
import com.ace.web.pf.datamodel.User;
import com.ace.web.pf.dtomodel.ReportRequestModel;
import com.ace.web.pf.dtomodel.StudentDTO;
import com.ace.web.pf.enums.ErrorMessage;
import com.ace.web.pf.enums.InfoMessages;
import com.ace.web.pf.enums.PageUrls;
import com.ace.web.pf.reports.ExportToExcel;
import com.ace.web.pf.reports.ExportToPDF;
import com.ace.web.pf.repository.StudentRepository;
import com.ace.web.pf.repository.TestRepository;
import com.ace.web.pf.service.impl.StudentServiceImpl;
import com.ace.web.pf.util.ApplicationData;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/generalSetting/student")
public class StudentController extends BaseController {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TestRepository test;
	@Autowired
	@Qualifier("studentService")
	private StudentServiceImpl studentService;

	@GetMapping
	public String vesselPage() {
		return PageUrls.Student.value();
	}
	
	@GetMapping("/home")
	public String home() {
		studentService.findAll();
		System.out.println("lkh");
		System.out.println(studentService.findAll().size());
		return "hello";
	}
	@PostMapping("/dataTable")
	public @ResponseBody DataTablesOutput<Student> vesselData(@RequestBody DataTablesInput dataTablesInput) {
		return studentService.findAll(dataTablesInput);
	}

	@PostMapping("/save")
	public @ResponseBody String addVessel(Student vessel) {
		User userName = commonUtils.loadLoginUserName();

		String name = vessel.getName();
		Student oldVessel = studentService.findByName(name);
		if (vessel.getId() == null) {
			if (oldVessel != null) {
				return messagesUtils.getMessage(ErrorMessage.Duplicate_Error.code());
			}
			vessel.setCreatedDateTime(new Date());
			vessel.setCreatedUserId(userName.getId());
		} else {

			Student updatedvessel = studentService.findById(vessel.getId());

			if (!updatedvessel.getName().equals(vessel.getName())) {
				if (oldVessel != null) {
					return messagesUtils.getMessage(ErrorMessage.Duplicate_Error.code());
				}
			}
			vessel.setCreatedDateTime(updatedvessel.getCreatedDateTime());
			vessel.setCreatedUserId(updatedvessel.getCreatedUserId());
			vessel.setUpdatedDateTime(new Date());
			vessel.setUpdatedUserId(userName.getId());
		}

		try {
			studentService.save(vessel);
		} catch (Exception e) {
			return messagesUtils.getMessage(e.getMessage());
		}

		return messagesUtils.getMessage(InfoMessages.Successfully_Save.code());
	}

	@PostMapping("/findById")
	public @ResponseBody Student findById(long id) {
		return studentService.findById(id);
	}

	@PostMapping("/delete")
	public @ResponseBody String deleteVessel(long id) {

		studentService.delete(id);
		return messagesUtils.getMessage(InfoMessages.Successfully_delete.code());
	}

	@GetMapping("/popup")
	public String popup() {
		return "/pages/generalSetting/popup";
	}
	@GetMapping("/searchname")
	@ResponseBody
	public List<Test> searchname(@RequestParam(value="term",required = false,defaultValue = "")String term) {
	List<Test>searchstudent=	test.findByName1(term);
	System.out.println(searchstudent);
	List<Test> allList=new ArrayList();
	Test stu1=new Test();
	List<String> list=new ArrayList();
	for(Test student:searchstudent) {
		  allList.add(student);
		
	}
	
	return allList;
		
	
	}
	@GetMapping("/myanmarFontTest")
	public void myanmarFontTest(HttpServletResponse response) throws JRException, IOException, SQLException {
		ExportToPDF.exportToPDF("Test_For_Myanmar_Font", "Test For Myanmar", null, response);
		
	}
	@PostMapping("/reportpdf")
	public void exportDetailReport(@RequestParam("start_date") java.sql.Date start_date,
			@RequestParam("end_date") java.sql.Date end_date, Model model, HttpSession session,HttpServletResponse response) throws ParseException, JRException, IOException, SQLException {

		Map<String, Object> parameters=new HashMap();
		parameters.put("start_date",start_date);
		parameters.put("end_date", end_date);
	ExportToPDF.exportToPDF("test_report", "Testing", parameters, response);
		
	}

	@PostMapping("/reportexcel")
	public void exportDetailReportExcel(@RequestParam("start_date") java.sql.Date start_date,
			@RequestParam("end_date") java.sql.Date end_date, Model model, HttpSession session,HttpServletResponse response) throws ParseException, JRException, IOException, SQLException {

		Map<String, Object> parameters=new HashMap<>();
		parameters.put("start_date", start_date);
		parameters.put("end_date",end_date);
		ExportToExcel.exportToXls("test_report", "Testing", parameters, response);

		
	}

	@PostMapping("/report")
	public ModelAndView redirectedToRelatedRoute(HttpServletRequest request,
			@RequestParam("start_date") java.sql.Date start_date, @RequestParam("end_date") java.sql.Date end_date,
			@RequestParam("format") String fmt) {

		request.setAttribute("start_date", request.getParameter("start_date"));
		request.setAttribute("end_date", request.getParameter("end_date"));
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		String redirectedRoute = null;

		switch (fmt) {
		case "pdf":
			redirectedRoute = "generalSetting/student/reportpdf";
			break;
		case "excel":
			redirectedRoute = "generalSetting/student/reportexcel";
			break;
		}

		return new ModelAndView("redirect:/" + redirectedRoute);
	}
	@GetMapping("/reportWithPara")
	public String reportWithPara() {
		return "/pages/generalSetting/reportWithPara";
	}

	@PostMapping("/test_M")
	public void test_M(@RequestParam("address")String address,Model model, HttpSession session,HttpServletResponse response) throws ParseException, JRException, IOException, SQLException {
		
		Map<String, Object> parameters=new HashMap<>();
		parameters.put("address", address);
		ExportToPDF.exportToPDF("ReportWithPara","Report With Parameter", parameters, response);
				
	}
//	
	@GetMapping("/summery")
	public void summary(HttpServletResponse respose) throws JRException, IOException, SQLException {
		ExportToPDF.exportToPDF("Summery", "Summery", null, respose);
	}
	
	
//	
//	@PostMapping("/cis-r1/reportpdf")
//	public PDFReportView exportCisr1Report( Model model, HttpSession session) throws ParseException {
//
//
//		ReportRequestModel request = new ReportRequestModel("BankCashCleanCash", "BankCashCleanCash Report");
//		request.setMetaDataTitle("Test Report");
//
//		model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
//
//		return new PDFReportView();
//	}
//
//	@GetMapping("/test_M_E")
//	public ExcelReportView test_M_E( Model model, HttpSession session) throws ParseException {
//
//		
//		ReportRequestModel request = new ReportRequestModel("test", "BankCashCleanCash Report");
//		request.setMetaDataTitle("BankCashCleanCash Report");
//
//		model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
//
//		return new ExcelReportView();
//	}
//	@PostMapping("/cis-r1/reportexcel")
//	public ExcelReportView exportCisr1Excel( Model model, HttpSession session) throws ParseException {
//
//		
//		ReportRequestModel request = new ReportRequestModel("BankCashCleanCash", "BankCashCleanCash Report");
//		request.setMetaDataTitle("BankCashCleanCash Report");
//
//		model.addAttribute(ApplicationData.REPORT_REQUEST_KEY, request);
//
//		return new ExcelReportView();
//	}
//	
	@GetMapping("/Board_Meeting_page18_image2")
	public void exportBoardMeetingPage18Image2Report(HttpServletResponse response) throws JRException, IOException, SQLException {
		ExportToPDF.exportToPDF("Board_Meeting_page18_image2", "Board_Meeting_page18_image2", null, response);
	}
	
//	@GetMapping("/InvoiceReport")
//	 public ResponseEntity<byte[]> studentReport() throws Exception, JRException {
//	 	//List<InvoiceReportForm> list=new ArrayList<InvoiceReportForm>();
//	 	//list=invoiceService.findall();
//	 	//RoomDTO booking_room=list.get(0).getBooking().getBookingRoom();
//	 	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentRepository.list(),false);
//	 	//System.out.println(invoiceService.findall().size());
//	 		JasperReport compileReport = JasperCompileManager
//	 			.compileReport(new FileInputStream("src/main/resources/reports/ChangeNumber.jrxml"));
//
//	 	Map<String, Object> map = new HashMap<>();
//		
//		
//	 	JasperPrint report = JasperFillManager.fillReport(compileReport, map, dataSource);
//	 	byte[] data = JasperExportManager.exportReportToPdf(report);
//
//	 	HttpHeaders headers = new HttpHeaders();
//	 	headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=InvoiceReport.pdf");
//
//	 	return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
//	 }
	

}
