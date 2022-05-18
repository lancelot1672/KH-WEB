package member.repository;

import member.dto.Member;

public interface MemberRepository {
    void save(Member member);
    Member findByMemberId(String member_id);

    int updateMember(Member member);
}
