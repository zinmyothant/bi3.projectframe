package com.ace.web.pf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/generalSetting/student")
public class RouteController {
	@GetMapping("/counter1")
	public String showAccountReport() {
		return "/pages/generalSetting/counter1";
	}

	@GetMapping("/counter2")
	public String showBranchReport() {
		return "/pages/generalSetting/counter2";
	}

	@GetMapping("/bank-cash-scroll-report")
	public String showBankCashScroll() {
		return "/pages/generalSetting/bank-cash-scroll-report";
	}

	@GetMapping("/ctr1-r1")
	public String showCtr1R1() {
		return "/pages/generalSetting/ctr1-r1";
	}

	@GetMapping("/ctr1-r2")
	public String showCtr1R2() {
		return "/pages/generalSetting/ctr1-r2";
	}

	@GetMapping("/ctr1-r3")
	public String showCtr1R3() {
		return "/pages/generalSetting/ctr1-r3";
	}

	@GetMapping("/ctr2-r1")
	public String showCtr2R1() {
		return "/pages/generalSetting/ctr2-r1";
	}

	@GetMapping("/ctr2-r2")
	public String showCtr2R2() {
		return "/pages/generalSetting/ctr2-r2";
	}

	@GetMapping("/ctr2-r3")
	public String showCtr2R3() {
		return "/pages/generalSetting/ctr2-r3";
	}
	
	@GetMapping("/cis")
	public String showDetailTrial() {
		return "/pages/generalSetting/cis";
	}

	@GetMapping("/cis-r1")
	public String showCisR1() {
		return "/pages/generalSetting/cis-r1";
	}

	@GetMapping("/cis-r2")
	public String showCisR2() {
		return "/pages/generalSetting/cis-r2";
	}

	@GetMapping("/cis-r3")
	public String showCis2R3() {
		return "/pages/generalSetting/cis-r3";
	}
}
