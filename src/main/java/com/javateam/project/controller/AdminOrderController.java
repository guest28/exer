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
	
	@RequestMapping("orderList")
	public String listOrder(@RequestParam("key") String key, Model model) {
		
		log.info("orderList");
		
		List<OrderVO> orderList = orderService.listOrder(key);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("js_file", "admin_order.js");
		
		return "/admin/order/order_list";
	}
	
	@RequestMapping("/order/orderSave")
	public String orderSave(@RequestParam("result") int[] odseqs) {

		log.info("orderSave");
		
		for(int odseq : odseqs) {
			
			orderService.updateOrderResult(odseq);
		}
		
		return "redirect:/admin/order/orderList";
	}
	
	
	
}
