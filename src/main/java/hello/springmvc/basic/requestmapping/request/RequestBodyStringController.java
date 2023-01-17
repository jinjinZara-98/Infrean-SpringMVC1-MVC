package hello.springmvc.basic.requestmapping.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * http메시지바디에 있는 값 일기
 * lombok이 제공하는 어노테이션
 * private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    //postman으로 post방식으로 text선택해 바디에 문자 써놓으면 읽어 출력
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //요청에서 스트림 얻어 대입
        ServletInputStream inputStream = request.getInputStream();
        //스트림은 바이트코드, 바이트코드를 문자로 바꿀때는 인코딩타입 지정해줘야함
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        //http메시지바디에 출력
        response.getWriter().write("ok");
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     *
     * HttpServletRequest과 HttpServletResponse 대신 InputStream와 Writer로
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter)  throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        //위에랑 다르게 response를 쓰지 않고 바로
        responseWriter.write("ok");
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편라하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 스트림 대신 http메시지 컨버터
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        /**
         * HttpEntity는 헤더,바디 정보를 편리하게 조회, 요청 파라미터를 조회하는 기능은 없음
         * HttpEntity를 상속받은 RequestEntity, ResponseEntity도 있음
         * RequestEntity HttpMethod, url 정보가 추가, 요청에서 사용
         * ResponseEntity HTTP 상태 코드 설정 가능, 응답에서 사용
         * 요청 제네릭타입이 String이면 StreamUtils.copyToString 역할 실행
         * httpEntity.getBody()로 http메시지바디를 꺼내 대입
         */
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        //반환 객체의 파라미터에 바디메시지 넣기 가능, 이렇게 응답에도 사용가능
        return new HttpEntity<>("ok");
        //바디메시지 출력하고 상태코드도 출력되게
        //return new ResponseEntity<String>("Hello World", responseHeaders,HttpStatus.CREATED)
    }

    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */

    /**
     * 이렇게 가장 많이 씀
     * @Controller 반환값은 논리경로이므로 http메시지바디에 출력하고 싶으면 이 어노테이션 사용
     * @param messageBody
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    /**
     * @RequestBody는 직접 만든 객체 지정 가능
     * 이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam ,@ModelAttribute 와는 전혀 관계가 없다.
     * 요청오는건 @RequestBody로 http메시지바디 읽어 변수에 바로 대입
     * 헤더 정보가 필요하면 HttpEntity나 @RequestHeader 사용
     */
    public String requestBodyStringV4(@RequestBody String messageBody) {

        log.info("messageBody={}", messageBody);

        return "ok";
    }
    //HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을
    // 우리가 원하는 문자나 객체 등으로 변환
}
