package member.repository;

import member.dto.Member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);
    Member findByMemberId(String member_id);

    int updateMember(Member member);

    List<Member> findAll();
}
