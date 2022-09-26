package com.ace.web.pf.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.ace.web.pf.dtomodel.ReportRequestModel;
import com.ace.web.pf.util.ApplicationData;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class ExportToExcel {

	public static void exportToXls(String inputFile, String outputFile,Map<String, Object> parameters, HttpServletResponse response)
			throws JRException, IOException, SQLException {

		JasperPrint jasperPrint = BaseReport.exportReport(inputFile, outputFile, parameters);

		JRXlsxExporter exporter = new JRXlsxExporter();
		SimpleXlsxReportConfiguration reportConfigXLS = new SimpleXlsxReportConfiguration();
		reportConfigXLS.setSheetNames(new String[] { "Sheet 1" });
		exporter.setConfiguration(reportConfigXLS);

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

		response.setHeader("Content-Disposition", "inline;filename=" + outputFile + ApplicationData.EXCEL_EXTENSION);
		response.setContentType("application/octet-stream");
		exporter.exportReport();

	}
}
