package hello.springmvc.basic.requestmapping.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
//lombok이 제공하는 어노테이션
//private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
@Slf4j
@Controller
public class RequestBodyJsonController {

    //json형식으로 요청을 했을때
    //json이니까 ObjectMapper객체 생성
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //요청에서 스트림 얻어 대입
        ServletInputStream inputStream = request.getInputStream();
        //스트림은 바이트코드, 바이트코드를 문자로 바꿀때는 인코딩타입 지정해줘야함
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        //어떤값을 어떤 클래스형태로 읽을꺼냐
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        //@Controller는 반환 값이 논리경로이므로 response객체를 통해 http메시지바디에 출력
        response.getWriter().write("ok");
    }

    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    //@Controller 반환값은 논리경로이므로 http메시지바디에 출력하고 싶으면 이 어노테이션 사용
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    //요청오는건 @RequestBody, http메시지바디 읽어 변수에 바로 대입
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype: application/json)
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    /**
     * @RequestBody는 직접 만든 객체 지정 가능
     * objectMapper으로 읽지 말고 바로 HelloData타입으로 받아
     * HttpEntity, @RequestBody를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을
     * 우리가 원하는 문자나 객체 등으로 변환해준다.
     * HTTP 메시지 컨버터는 문자 뿐만 아니라 JSON도 객체로 변환해주는데,
     * 우리가 방금 V2에서 했던 작업을 objectMapper 대신 처리
     * @RequestBody는 생략 불가능
     * 스프링은 @ModelAttribute , @RequestParam 해당 생략시
     * String , int , Integer 같은 단순 타입 = @RequestParam
     * 나머지 = @ModelAttribute 개발자가 직접 만든 클래스
     */
    public String requestBodyJsonV3(@RequestBody HelloData data) {

        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    //들어오는 타입 HelloData 설정해 값을 얻어와 HelloData객체에 대입
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();

        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype: application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    (Accept: application/json)
     */

    /**
     * 반환 타입을 string이 아닌 HelloData로
     * HTTP 메시지 컨버터가 @ResponseBody가 있으면 나갈때도 적용됨
     * 즉 json이 객체가 됬다가 나갈때도 json이 되는
     * @RequestBody 요청, JSON 요청 -> HTTP 메시지 컨버터 -> 객체
     * @ResponseBody 응답, 객체 -> HTTP 메시지 컨버터 -> JSON 응답
     * 응답의 경우에도 @ResponseBody 를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
     * 물론 이 경우에도 HttpEntity 를 사용해도 된다.
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }
}
