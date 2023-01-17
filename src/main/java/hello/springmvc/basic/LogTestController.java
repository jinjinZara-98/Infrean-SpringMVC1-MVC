package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * lombok이 제공하는 어노테이션
 * private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
 */
@Slf4j
@RestController
public class LogTestController {

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        /**
         * 시간, info, 프로세스 아이디, 현재 실행한 스레드, 현재 컨트롤러 이름, 메시지
         * {}는 값의 개수만큼
         */

        log.trace("trace log={}", name);

        /**이 로그는 디버그할때  */
        log.debug("debug log={}", name);

        /**
         * System.out.println("name = " + name);와 같음
         * 이 로그는 중요한 정보다
         */
        log.info(" info log={}", name);

        /** 이 로그는 경고 */
        log.warn(" warn log={}", name);

         /** 이 로그는 에러 */
        log.error("error log={}", name);

        /**
         * 로그를 사용하지 않아도 메서드 호출 전 a+b 계산 로직이 먼저 실행됨, 이런 방식으로 사용하면 X
         * info로 지정해놨으면 debug는 출력되지 않으므로 쓸모없는 계산 리소스가 실행되므로 낭비
         * log.debug("debug log={}", name)는 그냥 중지해버려서 상관없음
         */
        log.debug("String concat log=" + name);
        return "ok";
    }
}
