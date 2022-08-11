package com.javateam.project.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.project.domain.CartVO;
import com.javateam.project.domain.MemberVO;
import com.javateam.project.domain.QnaVO;
import com.javateam.project.service.QnaService;

@Controller
@RequestMapping("qna")
public class QnaController {

	private static final Logger log 
	= LoggerFactory.getLogger(QnaController.class);
	
	@Inject
	QnaService qnaService;
	
	@RequestMapping("qna_list")
	public String qnaList(HttpSession session, Model model) {

		log.info("qnaList");
		
		String id = ((MemberVO)session.getAttribute("LOGIN_USER")).getId();
		
		model.addAttribute("qnaList", qnaService.listQna(id));
		
		return "/qna/qna_list";
	}
	
	@RequestMapping("qna_view")
	public String qnaView(int qseq, Model model) {
		
		log.info("qnaView");
		
		model.addAttribute("qnaVO", qnaService.getQna(qseq));
		
		return "/qna/qna_view";
	}
	
	@RequestMapping("qna_write_form")
	public String qnaWirteForm() {
		
		log.info("qnaWriteForm");
		
		
		return "/qna/qna_write";
	}
	

	@RequestMapping("qna_write")
	public String qnaWirte(QnaVO qnaVO, HttpSession session, Model model) {
		
		log.info("qnaWrite : " + qnaVO);
		
		String id = ((MemberVO)session.getAttribute("LOGIN_USER")).getId();
		qnaVO.setId(id);
		
		String msg = "";
		String movePath = "";
		
		
		if(qnaService.insertQna(qnaVO) == true) {
			
			msg = "게시글 저장 성공";
			movePath = "/qna/qna_list";
			
		} else {
			
			msg = "게시글 저장 실패";
			movePath = "/qna/qna_write_form";
			
		}
 		
		model.addAttribute("err_msg", msg);
		model.addAttribute("move_path", movePath);
		
		
		return "/error/error";
	}
	
}
