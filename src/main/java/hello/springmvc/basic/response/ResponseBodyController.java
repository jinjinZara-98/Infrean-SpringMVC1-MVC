package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//REST API HTTP API
//lombok이 제공하는 어노테이션
//private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
@Slf4j
//@Controller는 반환 값이 논리경로
@Controller
//@RestController는 @Controller와 @ResponseBody합친거
//그래서 @RestController도 @ResponseBody다 적용됨
//@ResponseBody를 클래스에 붙이면 메서드에 다 붙게됨
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException
    {
        //HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달
        response.getWriter().write("ok");
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {

//        ResponseEntity 엔티티는 HttpEntity 를 상속 받았는데, HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
//        ResponseEntity 는 여기에 더해서 HTTP 응답 코드를 설정
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

//    HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다. ResponseEntity 도 동일한 방식으로 동작
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {

        return "ok";
    }

    //jspn 처리할때, 위에는 문자를 처리할때
    // HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어서 반환
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {

        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        //출력할 메시지값, 상태를 파라미터로 넣어 반환
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    //API만들때는 대부분 이렇게
    //제네릭에 HelloData를 넣은게 아닌 반환 타입으로 지정하는
    //상태는 어노테이션으로
    //ResponseEntity면 값과 상태를 한꺼번에 반환 가능
    //@ResponseBody이므로 상태를 반환하고 싶다면 @ResponseStatus붙이기
    //애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {

        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}

