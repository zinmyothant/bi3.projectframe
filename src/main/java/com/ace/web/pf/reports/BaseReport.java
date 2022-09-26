package com.ace.web.pf.reports;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ace.web.pf.dtomodel.ReportRequestModel;
import com.ace.web.pf.util.ApplicationData;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class BaseReport {

	public static JasperPrint exportReport(String inputFile, String outputFile, Map<String, Object> parameters)
			throws IOException, JRException {

		String reportFileName = getFilePath(inputFile);

		JasperReport jasperDesign = JasperCompileManager.compileReport(reportFileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameters, dataSource());
		//System.out.println(dataSource());
		return jasperPrint;
	}

	public static Connection dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@192.168.10.101:1521:mftbuat");
		dataSource.setUsername("CUSTOM");
		dataSource.setPassword("CUSTOM");

		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			return null;
		}
	}

	public static String getFilePath(String filename) throws IOException {
		String path = new ClassPathResource("/reports/" + filename + ApplicationData.REPORT_EXTENSION).getFile()
				.toString();

		return path;
	}
}
