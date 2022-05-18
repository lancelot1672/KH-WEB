package member.service;

import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import member.dto.Member;
import member.dto.MemberRole;
import member.repository.JdbcMemberRepository;
import member.repository.MemberRepository;

public class MemberServiceImpl implements MemberService{

	MemberRepository memberRepository;

	@Override
	public Member findMember(String memberId) {
		memberRepository = new JdbcMemberRepository();
		Member member = memberRepository.findByMemberId(memberId);

		return member;
	}

	@Override
	public List<Member> findAllMember() {
		memberRepository = new JdbcMemberRepository();
		return memberRepository.findAll();
	}

	@Override
	public int updateMember(Member member) {
		memberRepository = new JdbcMemberRepository();
		return  memberRepository.updateMember(member);
	}

	@Override
	public void join(Member member) {
		memberRepository = new JdbcMemberRepository();
		memberRepository.save(member);
	}
}
