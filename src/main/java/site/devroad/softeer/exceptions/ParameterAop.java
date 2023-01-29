package site.devroad.softeer.exceptions;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class ParameterAop {
    Logger logger = LoggerFactory.getLogger(ParameterAop.class);

    //com/example/aop/controller 패키지 하위 클래스들 전부 적용하겠다고 지점 설정
    @Pointcut("execution(* site.devroad.softeer.src..*.*(..))")
    private void cut() {}

    //cut() 메서드가 실행 되는 지점 이전에 before() 메서드 실행
    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        logger.trace(method.getName() + "메서드 실행");

        //메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        //매개변수 배열의 종류와 값을 출력
        for(Object obj : args) {
            logger.trace("type : "+obj.getClass().getSimpleName());
            logger.trace("value : "+obj);
        }
    }

    //cut() 메서드가 종료되는 시점에 afterReturn() 메서드 실행
    //@AfterReturning 어노테이션의 returning 값과 afterReturn 매개변수 obj의 이름이 같아야 함
    @AfterReturning(value = "cut()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj) {
        logger.trace("return obj");
        logger.trace(obj.toString());
    }
}