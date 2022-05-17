package member.service;

import java.io.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public void join(Member member) {
		memberRepository = new JdbcMemberRepository();
		memberRepository.save(member);
	}
}
