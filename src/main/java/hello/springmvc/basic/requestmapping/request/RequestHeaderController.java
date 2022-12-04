package hello.springmvc.basic.requestmapping.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//lombok이 제공하는 어노테이션
//private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,
                          //locale정보 언어 정보, 쿠키나 세션에 저장 가능
                          Locale locale,
                          //헤더를 한 번에 다 받는
                          //MultiValueMap은 하나의 키에 여러가지 값 받을 수 있음
                          //keyA=value1&keyA=value2, 그래서 키에 대한 반환값들이 배열
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          //하나 받고 싶을 때, 헤더이름이 host인 값 받기
                          @RequestHeader("host") String host,
                          //myCooki는 쿠키이름, required = false 필수가 아닌
                          @CookieValue(value = "myCookie", required = false) String cookie

    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";
    }
}