package com.javateam.project.service;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javateam.project.domain.CartVO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/spring/db-context.xml")
@Slf4j
public class CartServiceCheckCartTest {
	
	@Inject
	CartService cartService;

	List<CartVO> cartList;
	CartVO cartVO;
	
	@Before
	public void setUp() {
		
		cartList = new ArrayList<>();
		cartVO = new CartVO();
		
		cartVO.setCseq(1);
		cartVO.setId("abcd1234");
		cartVO.setIndate(new Timestamp(System.currentTimeMillis()));
		cartVO.setPname("슬리퍼");
		cartVO.setPseq(111);
		cartVO.setQuantity(2);
		cartVO.setResult(1);
		
		cartList.add(cartVO);
		
		
		CartVO cartVO = new CartVO();
		
		cartVO.setCseq(2);
		cartVO.setId("abcd1234");
		cartVO.setIndate(new Timestamp(System.currentTimeMillis()));
		cartVO.setPname("힐");
		cartVO.setPseq(222);
		cartVO.setQuantity(5);
		cartVO.setResult(1);
		
		cartList.add(cartVO);
	
	}
	
	@Test
	public void test() {
		
		log.info("CartService.CheckCart.Test");
		
		cartVO = new CartVO();
		
		cartVO.setCseq(3);
		cartVO.setId("abcd1234");
		cartVO.setIndate(new Timestamp(System.currentTimeMillis()));
		cartVO.setPname("힐");
		cartVO.setPseq(222);
		cartVO.setQuantity(3);
		cartVO.setResult(1);
		
		if(cartService.checkCart(cartList, cartVO) == true) {
			
			cartList.add(cartVO);
		}
		
		cartVO = new CartVO();
		
		cartVO.setCseq(4);
		cartVO.setId("abcd1234");
		cartVO.setIndate(new Timestamp(System.currentTimeMillis()));
		cartVO.setPname("힐");
		cartVO.setPseq(222);
		cartVO.setQuantity(2);
		cartVO.setResult(1);
		
		if(cartService.checkCart(cartList, cartVO) == true) {
			
			cartList.add(cartVO);
		}
		
		cartVO = new CartVO();
		
		cartVO.setCseq(5);
		cartVO.setId("abcd1234");
		cartVO.setIndate(new Timestamp(System.currentTimeMillis()));
		cartVO.setPname("슬리퍼");
		cartVO.setPseq(111);
		cartVO.setQuantity(1);
		cartVO.setResult(1);
		
		if(cartService.checkCart(cartList, cartVO) == true) {
			
			cartList.add(cartVO);
		}
		
		for (CartVO vo : cartList) {
			log.info("vo : " + vo);
		}
		
	}
}
