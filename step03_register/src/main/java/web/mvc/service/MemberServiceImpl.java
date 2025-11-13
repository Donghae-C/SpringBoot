package web.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Member;
import web.mvc.exception.ErrorCode;
import web.mvc.exception.MemberAuthenticationException;
import web.mvc.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String duplicateCheck(String id) {
        Member member = memberRepository.duplicateCheck(id);
        String result = "중복입니다";
        if(member == null){
            result = "사용가능합니다.";
        }
        return result;
    }

    @Override
    public void signUp(Member member) {
        if(memberRepository.existsById(member.getId())){
            throw new MemberAuthenticationException(ErrorCode.DUPLICATED);
        }
        member.setPwd(passwordEncoder.encode(member.getPwd()));
        member.setRole("ROLE_USER");
        memberRepository.save(member);
    }
}
