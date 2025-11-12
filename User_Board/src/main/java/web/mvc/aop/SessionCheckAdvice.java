package web.mvc.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SessionCheckAdvice {
    private final HttpServletRequest request;

    @Around("execution(* web.mvc.controller.FreeBoardController.*(..))")
    public Object sessionCheck(ProceedingJoinPoint pjp) throws Throwable {
        log.info("session check start");
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attr != null){
            HttpSession session = attr.getRequest().getSession(false);
            if(session == null || session.getAttribute("loginUser") == null){
                throw new BasicException(ErrorCode.ACCESS_DENIED);
            }
        }


        Object proceed = pjp.proceed();

        return proceed;
    }

    ////컨트롤러 메서드쪽이라서 Request를 주입받을 수 있음.. 보통의 AOP처럼 Service 전후처리면 이렇게 주입받지 못함
    /*@Before("execution(* web.mvc.controller.FreeBoardController.*(..))")
    public void before(JoinPoint joinPoint) throws Throwable {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginUser") == null){
            throw new BasicException(ErrorCode.ACCESS_DENIED);
        }
    }*/

}
