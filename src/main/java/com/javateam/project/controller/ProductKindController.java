package com.javateam.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.project.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductKindController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductKindController.class);
	
	@Autowired
	ProductService productService;
	
	@RequestMapping("/category")
	public String category(@RequestParam(value="kind", defaultValue="1") int kind, Model model) {
		
		log.info("상품 카테고리 : category : {kind}", kind);
		
		model.addAttribute("productKindList", productService.listKindProduct(kind));
		
		return "/product/product_kind";		
	}	
	
	@RequestMapping("/product_detail")
	public String productDetail(@RequestParam("pseq") int pseq, Model model) {	
		log.info("상품 소개 : {}", pseq);
		
//		String page = "/product/productDetail";
		
		model.addAttribute("productVO", productService.getProduct(pseq));
		
		return "/product/product_detail";
	}
	
}
