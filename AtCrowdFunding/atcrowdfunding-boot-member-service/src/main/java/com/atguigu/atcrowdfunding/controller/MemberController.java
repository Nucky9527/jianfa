package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.service.MemberService;

@RestController
public class MemberController extends BaseController{

	@Autowired
	private MemberService memberService ;
	
	@RequestMapping("/member/updateEmail")
	public int updateEmail(@RequestBody Member member) {
		return memberService.updateEmail(member);
		
	}
	
	@RequestMapping("/member/saveMemberCert")
	public void saveMemberCert(@RequestBody List<MemberCert> certList) {
		 memberService.saveMemberCert(certList);
	}
	
	@RequestMapping("/member/queryCertByAccttype/{accttype}")
	public List<Cert> queryCertByAccttype(@PathVariable ("accttype") String accttype){
		return memberService.queryCertByAccttype(accttype);
	}
	
	@RequestMapping("/member/updateBasicinfo")
public int updateBasicinfo(@RequestBody Member member) {
		return memberService.updateBasicinfo(member);
		
	}
	
	@RequestMapping("/member/updateAccttype")
	public int updateAccttype(@RequestBody Member member) {
		return memberService.updateAccttype(member);
	}
	
	@RequestMapping("/member/getTicketByMemberid/{memberid}")
	public Ticket getTicketByMemberid(@PathVariable("memberid") Integer memberid) {
		return memberService.getTicketByMemberid(memberid);
		
	}
	
	@RequestMapping("/member/saveTicket")
	public void saveTicket(@RequestBody Ticket ticket) {
		memberService.saveTicket(ticket);
	}
	
	@RequestMapping("/member/queryMemberByLogin/{loginacct}")
	public Member queryMemberByLogin(@PathVariable("loginacct") String loginacct) { //unique约束
		Member member = memberService.queryMemberByLogin(loginacct);
		return member ;
	}
	
}