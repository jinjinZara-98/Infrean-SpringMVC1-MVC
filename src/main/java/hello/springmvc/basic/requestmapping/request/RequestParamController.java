package hello.springmvc.basic.requestmapping.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//lombok이 제공하는 어노테이션
//private final Logger log = LoggerFactory.getLogger(getClass())을 대신하는 역할
@Slf4j
//@Controller는 반환 값이 논리경로
@Controller
public class RequestParamController {
    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */
    //hello-form에서 적은 값을 전송하면 여기로 바로 옴
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //파라미터의 값들을 갖고와 대입
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        //void타입인데 response에 값쓰면 값 쓴게 나옴
        log.info("username={}, age={}", username, age);

        //홈페이지에 출력, http 메시지바디
        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    //@Controller 반환값은 논리경로이므로 http메시지바디에 출력하고 싶으면 이 어노테이션 사용
    @ResponseBody
    //url이 /request-param-v2로 들어왔을때
    @RequestMapping("/request-param-v2")
    public String requestParamV2(

            //파라미터 값을 변수에 받음
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);

        return "ok";
    }

    //이게 가장 베스트
    @ResponseBody
    //url이 /request-param-v2로 들어왔을때
    @RequestMapping("/request-param-v3")
    public String requestParamV3(

            //파라미터 값을 변수에 받음
            //파라미터이름과 변수이름이 같다면 파라미터이름 명시 안해도됨됨
           @RequestParam String userName, @RequestParam int age) {

        log.info("username={}, age={}", userName,  age);

        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능, 개발자가 만든 클래스타입이 아니면
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4 (String username, int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * @RequestParam.required
     * /request-param -> username이 없으므로 예외
     *
     * 주의!
     * /request-param?username= -> 빈문자로 통과
     *
     * 주의!
     * /request-param
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            //이 값이 무조건 들어와야 하는지 아닌 설정
            //true인데 안들어오면 오류, ""도 값이 될 수 있음
            @RequestParam(required = true) String username,

            //required = false이기 때문에 null이 들어갈 수 있게 Integer로
            @RequestParam(required = false) Integer age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            //값이 안넘어오면 이 값으로 하겠다, ""이런 빈문자값도 디폴트값을 넣어줌
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    //모든 파라미터 값을 다 받는, MultiMap으로도 받기 가능
    //파라미터값이 1개인게 확실하면 Map으로 해도 되지만 그렇지 않으면 MultiMap
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때
    자세히 설명
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    //HelloData객체를 생성해 url요청 파라미터 값을 필드에 다 대입해줌
    //요청 파라미터 이름과 같은 필드를 찾아 대입
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입 = @RequestParam
     * argument resolver 로 지정해둔 타입 외 = @ModelAttribute
     */
    //@ModelAttribute 생략도가능, 개발자가 만든 클래스들은 자동으로 @ModelAttribute 생김
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
}
