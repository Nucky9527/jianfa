package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface MemberService {
	Member queryMemberByLogin(String loginacct);

	void saveTicket(Ticket ticket);

	Ticket getTicketByMemberid(Integer memberid);

	int updateAccttype(Member member);

	int updateBasicinfo(Member member);

	List<Cert> queryCertByAccttype(String accttype);

	void saveMemberCert(List<MemberCert> certList);

	int updateEmail(Member member);
	
	
}
