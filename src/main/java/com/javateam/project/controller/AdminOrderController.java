package com.javateam.project.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.project.domain.OrderVO;
import com.javateam.project.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminOrderController {

	@Inject
	OrderService orderService;
	
	@RequestMapping("/order/order_list")
	public String listOrder(@RequestParam(value = "key", defaultValue = "") String key, Model model) {
	//인자 "key" : 항상 넘기는 값이 아니다. 검색할 때만 쓰임 => 페이지에서 넘길 인자가 없어 오류 발생 => 디폴트값(당장은 필요 없으니 빈 값인 "")을 설정해준다.	

		
		log.info("orderList");
		
		List<OrderVO> orderList = orderService.listOrder(key);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("js_file", "admin_order.js");
		
		return "/admin/order/order_list";
	}
	
	@RequestMapping("/order/order_save")
	public String orderSave(@RequestParam("result") int[] odseqs) {

		log.info("orderSave");
		
		for(int odseq : odseqs) {
			
			orderService.updateOrderResult(odseq);
		}
		
		return "redirect:/admin/order/order_list";
	}
	
	
	
}
