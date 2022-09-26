package com.ace.web.pf.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.ace.web.pf.dtomodel.ReportRequestModel;
import com.ace.web.pf.util.ApplicationData;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class ExportToPDF {

	public static void exportToPDF(String inputFile, String outputFile, Map<String, Object> parameters,
			HttpServletResponse response) throws JRException, IOException, SQLException {

		JasperPrint jasperPrint = BaseReport.exportReport(inputFile, outputFile, parameters);

		ServletOutputStream outputStream = response.getOutputStream();
		response.setHeader("Content-Disposition", "inline;filename=" + outputFile + ApplicationData.PDF_EXTENSION);
		response.setContentType("application/pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
}
