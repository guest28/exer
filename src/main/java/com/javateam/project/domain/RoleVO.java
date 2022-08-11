package com.javateam.project.domain;

public class RoleVO {
	
	private int roleSeq;
	private String id;
	private String role;
	
	//기본 생성자 : 기본 생성자도 필요 하다면 따로 만들어줘야 한다
	public RoleVO() {}
	
	//생성자 오버라이딩 : Source => Generate Constructor Using Fields...
	public RoleVO(String id, String role) {
		this.id = id;
		this.role = role;
	}

	public int getRoleSeq() {
		return roleSeq;
	}
	public void setRoleSeq(int roleSeq) {
		this.roleSeq = roleSeq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "RoleVO [roleSeq=" + roleSeq + ", id=" + id + ", role=" + role + "]";
	}
	
}
