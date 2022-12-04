package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {

        log.info("helloBasic");

        //@RestController이므로 http메시지바디에 ok찍음
        //@Controller면 뷰이름으로 넘김
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {

        log.info("mappingGetV1");

        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기)
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {

        log.info("mapping-get-v2");

        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     */
    //url자체에 값이 들어가있는
    @GetMapping("/mapping/{userId}")
    //@PathVariable("userId") 템플릿형식으로 꺼낼 수 있음, 매칭 되는 부분 조회
    //@PathVariable("userId") String data을 @PathVariable String data로 가능
    //최근 HTTP API는 다음과 같이 리소스 경로에 식별자를 넣는 스타일을 선호한다
    public String mappingPath(@PathVariable String data) {

        log.info("mappingPath userId={}", data);

        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    //다중 매핑, 어떤 유저가 무슨 주문을 했는가
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {

        log.info("mappingPath userId={}, orderId={}", userId, orderId);

        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    //특정 파라미터 조건 매핑, mode=debug이라는 파라미터 이름이 있어야
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {

        log.info("mappingParam");

        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {

        log.info("mappingHeader");

        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    //몇가지 더 부가적인 기능 제공, Content-Type에 따라 처리, consumes가 Content-Type
    //내 입장에서 소비한다
    //"application/json" 대신 MediaType.APPLICATION_JSON_VALUE 사용가능
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes() {

        log.info("mappingConsumes");

        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    //HTTP 요청 Accept로 맞아야하는, Accept가 text/html이어야지 실행
    //produces는 내가 생산해내는
    //"text/html"대신 MediaType.TEXT_HTML_VALUE 사용가능
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {

        log.info("mappingProduces");

        return "ok";
    }
}
