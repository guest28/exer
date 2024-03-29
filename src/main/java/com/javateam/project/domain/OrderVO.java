package com.javateam.project.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderVO {
	
	private int oseq;
	private int odseq;
	private int pseq;
	private int quantity;
	private int price1;
	private String phone;
	private String id;
	private String mname;
	private String pname;
	private String address1;
	private String address2;
	private String zipNum;
	private String result;
	private Timestamp indate;

}
