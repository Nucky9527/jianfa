package com.atguigu.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface MemberDao {

	@Select("select * from t_member where loginacct=#{loginacct}")
	Member queryMemberByLogin(String loginacct);

	@Insert("insert into t_ticket (memberid,piid,status,pstep) values (#{memberid},#{piid},#{status},#{pstep})")
	void saveTicket(Ticket ticket);

	@Select("select * from t_ticket where memberid=#{memberid} and status = '0'")
	Ticket getTicketByMemberid(Integer memberid);

	@Update("update t_member set accttype=#{accttype} where id = #{id}")
	int updateAccttype(Member member);
	
	@Update("update t_ticket set pstep=#{pstep} where id = #{id}")
	int updateTicketPstep(Ticket ticket);

	@Update("update t_member set realname=#{realname},cardnum=#{cardnum},tel=#{tel} where id = #{id}")
	int updateBasicinfo(Member member);
	
	@Select("select t_cert.* from t_cert,t_account_type_cert where t_cert.id = t_account_type_cert.certid and t_account_type_cert.accttype=#{accttype}")
	List<Cert> queryCertByAccttype(String accttype);

	@Select("select * from t_member where id = #{memberid}")
	Member getMemberById(Integer memberid);
	
	
	void saveMemberCert(List<MemberCert> certList);
	@Update("update t_member set email=#{email} where id = #{id}")
	int updateEmail(Member member);
	@Update("update t_ticket set pstep={pstep},authcode=#{authcode} where id =#{id}")
	void updateTicketPstepAndAuthcode(Ticket ticket);
	
	
	
}
