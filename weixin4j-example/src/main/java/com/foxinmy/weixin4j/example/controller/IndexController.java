package com.foxinmy.weixin4j.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foxinmy.weixin4j.mp.WeixinProxy;

@Controller
@RequestMapping("")
public class IndexController {
	@Autowired
	private WeixinProxy weixinProxy;

	@RequestMapping
	public String index() {
		return "index";
	}
}
