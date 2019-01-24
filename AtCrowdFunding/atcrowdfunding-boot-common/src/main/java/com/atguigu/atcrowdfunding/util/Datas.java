package com.atguigu.atcrowdfunding.util;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;

public class Datas {

	private List<Integer> ids = new ArrayList<Integer>();
	
	private List<MemberCert> certList = new ArrayList<MemberCert>();
	private List<User> userList ;
	private List<Role> roleList ;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<MemberCert> getCertList() {
		return certList;
	}

	public void setCertList(List<MemberCert> certList) {
		this.certList = certList;
	}
	
	

}
