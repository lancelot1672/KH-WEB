package jdbc.test;

import member.dto.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

public class MemberServiceTest {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member admin = memberService.findMember("admin");
        System.out.println("admin = " + admin);
    }
}
