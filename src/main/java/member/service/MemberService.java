package member.service;

import member.dto.Member;

public interface MemberService {
	Member findMember(String memberId);
	void join(Member member);

	int updateMember(Member member);
}
