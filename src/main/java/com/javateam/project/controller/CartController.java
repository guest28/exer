package com.javateam.project.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.project.domain.CartVO;
import com.javateam.project.domain.MemberVO;
import com.javateam.project.service.CartService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CartController {

	@Inject
	CartService cartService;
	
	@RequestMapping("cart_insert")
	public String cartInsert(@RequestParam("pseq") int pseq,
							 @RequestParam("quantity") int quantity,
							 HttpSession session,
							 Model model) {
		
		log.info("cart_insert");
		
		String errMsg = "";
		String path = "";
		
		MemberVO memberVO = (MemberVO)session.getAttribute("LOGIN_USER");
		
		CartVO cartVO = new CartVO();
		
		cartVO.setId(memberVO.getId());
		cartVO.setPseq(pseq);
		cartVO.setQuantity(quantity);
		cartVO.setResult(1);	//주문상태 = 1
		
		//기존 장바구니 현황과 비교하여 중복 상품 점검 => 같은 놈은 새로 추가하지 않고 기존에 더하거나 뺀다는 뜻
		List<CartVO> cartList = cartService.listCart(memberVO.getId());
		
		CartVO checkCartVO = cartService.getCartCheckCart(cartList, cartVO);
		
		//중복 상품이 없을 때 => 장바구니에 insert
		if(checkCartVO == null) {
			
			cartList.add(cartVO);
			
			if(cartService.insertCart(cartVO) == true) {
				
				errMsg = "상품을 장바구니에 담기 성공";
				path = "/cart/cartList";
				model.addAttribute("cartList", cartList);	//기존 장바구니를 변경
			
			} else {
				
				errMsg = "상품을 장바구니에 담기 실패";
				path = "/product/product_detail?pseq=" + pseq;
			}
			
		//중복 상품이 있을 때 => 장바구니에 update
		} else {
			
			if (cartService.updateCart(checkCartVO) == true) {
				
				errMsg = "장바구니 수정 성공";
				path = "/cart/cartList";
				
				//기존 장바구니 정보 변경/추가
				cartList = cartService.listCart(memberVO.getId());
				model.addAttribute("cartList", cartList);	//기존 장바구니를 변경
			
			} else {
				
				errMsg = "장바구니 수정 실패";
				path = "/product/product_detail?pseq=" + pseq;
				model.addAttribute("cartList", cartList);	//기존 장바구니를 변경
			}	
		}
		
		model.addAttribute("err_msg", errMsg);
		model.addAttribute("path", path);
		
		return "/error/error";
	}
	
	@GetMapping("cart_list")
	public String cartList(HttpSession session, Model model) {
		
		log.info("cart_list");
		
		MemberVO memberVO = (MemberVO)session.getAttribute("LOGIN_USER");
		
		List<CartVO> cartList = cartService.listCart(memberVO.getId());

		int totalPrice = 0;
		
		for(CartVO cartVO : cartList) {
			
			totalPrice += cartVO.getPrice2() * cartVO.getQuantity();
		}
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("totalPrice", totalPrice);
		
		
		return "/cart/cart_list";
	}
	
	@RequestMapping("cartDelete")
	public String cartDelete(@RequestParam("cseq") int[] cseqs, Model model) {
		
		log.info("cartDelete");
		
		String errMsg = "";
		int count = 0;
		
		for(int s : cseqs) {
			
			log.info("- " + s);
		}
		
		for(int cseq : cseqs) {
			
			if(cartService.deleteCart(cseq) == true) {
				
				count++;
			}
		}
		if(count == cseqs.length) {
			
			errMsg = "장바구니 삭제에 성공하였습니다.";
		
		} else {
			
			errMsg = "장바구니 삭제에 실패했습니다.";
		}
		
		model.addAttribute("errMsg", errMsg);
		model.addAttribute("movePath", "/cart/cartList");
		return "/cart/cart_list";
		
	}
	
	@RequestMapping("cartUpdate")
	public String cartUpdate(@RequestParam("cseq") int[] cseqs, 
							 @RequestParam("pseq") int[] pseqs,
							 @RequestParam("quantity") int[] quantities,
							 HttpSession session,
							 Model model) {
		
		log.info("cartUpdate : ");
		
		MemberVO memberVO = (MemberVO)session.getAttribute("LOGIN_USER");
		
		String id = memberVO.getId();
		String errMsg = "";
		int count = 0;
		
		CartVO cartVO;
		
		for(int s : cseqs) {
			
			log.info("- " + s);
		}
		
		for(int s : pseqs) {
			
			log.info("- " + s);
		}
		for(int s : quantities) {
			
			log.info("- " + s);
		}
		
		log.info("--------------------------------------------------------------------------------------");
		
		for(int i = 0; i < cseqs.length; i++) {
			
			cartVO = new CartVO();
			cartVO.setCseq(cseqs[i]);
			cartVO.setId(id);
			cartVO.setPseq(pseqs[i]);
			cartVO.setQuantity(quantities[i]);
			cartVO.setResult(1);
		}
		
		if(count == cseqs.length) {

			errMsg = "카트 수정에 성공";
		} else {
			
			errMsg = "카트 수정에 실패";
		}
		
		model.addAttribute("errMsg", errMsg);
		model.addAttribute("movePath", "/cart/cartList");
		
		return "/error/error";
		
	}
}
