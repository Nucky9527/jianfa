package com.atguigu.atcrowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.dao.MemberDao;
import com.atguigu.atcrowdfunding.service.ActivitiService;
import com.atguigu.atcrowdfunding.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao ;
	
	@Autowired
	private ActivitiService activitiService;
	
	@Override
	public Member queryMemberByLogin(String loginacct) {
		return memberDao.queryMemberByLogin(loginacct);
	}

	@Override
	public void saveTicket(Ticket ticket) {
		memberDao.saveTicket(ticket);
		
	}

	@Override
	public Ticket getTicketByMemberid(Integer memberid) {
		
		return memberDao.getTicketByMemberid(memberid);
	}

	@Override
	public int updateAccttype(Member member) {
		int i = memberDao.updateAccttype(member);
		if (i == 1) {
			Ticket ticket = memberDao.getTicketByMemberid(member.getId());
			ticket.setPstep("basicinfo");
			memberDao.updateTicketPstep(ticket);
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("piid", ticket.getPiid());
			param.put("loginacct", member.getLoginacct());
			activitiService.completeTask(param);
		}
		return i;
	}

	@Override
	public int updateBasicinfo(Member member) {
		
		int i = memberDao.updateBasicinfo(member);
		if(i==1) {
			Ticket ticket = memberDao.getTicketByMemberid(member.getId());
			ticket.setPstep("certupload");
			memberDao.updateTicketPstep(ticket);
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("piid", ticket.getPiid());
			param.put("loginacct", member.getLoginacct());
			param.put("flag", true);
			activitiService.completeTask(param);
		}
		return i;
	}

	@Override
	public List<Cert> queryCertByAccttype(String accttype) {
	
		return memberDao.queryCertByAccttype(accttype);
	}

	@Override
	public void saveMemberCert(List<MemberCert> certList) {
		MemberCert memberCert = certList.get(0);
		Integer memberid = memberCert.getMemberid();
		Member member = memberDao.getMemberById(memberid);
		memberDao.saveMemberCert(certList);
		Ticket ticket = memberDao.getTicketByMemberid(memberid);
		ticket.setPstep("checkemail");
		memberDao.updateTicketPstep(ticket);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("piid", ticket.getPiid());
		param.put("loginacct", member.getLoginacct());
		param.put("falg", true);
		
		activitiService.completeTask(param);
	}

	@Override
	public int updateEmail(Member member) {
		int count = memberDao.updateEmail(member);
		if(count==1) {
			StringBuilder authcode = new StringBuilder();
			for (int i = 1; i <= 4; i++) {
				authcode.append(new Random().nextInt(10));
			}
			
			Ticket ticket = memberDao.getTicketByMemberid(member.getId());
			ticket.setPstep("checkauthcode");
			ticket.setAuthcode(authcode.toString());
			memberDao.updateTicketPstepAndAuthcode(ticket);
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("piid", ticket.getPiid());
			param.put("loginacct", member.getLoginacct());
			param.put("flag", true);
			param.put("email", member.getEmail());
			param.put("authcode", authcode.toString());
			
			activitiService.completeTask(param);
			
		}
		return count;
	}

}
