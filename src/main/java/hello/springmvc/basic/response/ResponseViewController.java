package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//hello.html의 data
@Controller
public class ResponseViewController {

    //url에 /response-view-v1 들어오면
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        //논리경로 response/hello를 파라미터로 넣은 ModelAndView객체
        //response/hello 로 뷰 리졸버가 실행되어서 뷰를 찾고, 렌더링
        //다음 경로의 뷰 템플릿이 렌더링 templates/response/hello.htm
        //보내는 값이 있으니 정적인 static으로 가지 않음
        ModelAndView mav = new ModelAndView("response/hello")
                //data란 이름에 hello!이라는 값을 추가
                .addObject("data", "hello!");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    //String으로 반환하려면 Model객체 필요
    public String responseViewV2(Model model) {

        model.addAttribute("data", "hello!!");

        //ModelAndView가 아닌 논리경로를 반환
        //@ResponseBody 있으면 http메시지 바디에 출려됨
        return "response/hello";
    }

    //권장하지 않는 방법, url경로이름과 뷰 논리이름과 같으면 반환값 없어도됨
    //HttpServletResponse , OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면
    //요청 URL을 참고해서 논리 뷰 이름으로 사용
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }
}
