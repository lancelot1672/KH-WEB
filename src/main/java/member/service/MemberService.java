package member.service;

import member.dto.Member;

import java.util.List;

public interface MemberService {
	Member findMember(String memberId);
	void join(Member member);

	List<Member> findAllMember();
	int updateMember(Member member);
}
