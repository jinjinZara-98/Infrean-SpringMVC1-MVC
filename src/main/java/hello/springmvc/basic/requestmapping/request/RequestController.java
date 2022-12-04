package hello.springmvc.basic.requestmapping.request;

import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestController {

//    @ResponseBody
//    @PostMapping("/request")
//    public String httpmessagerequest(@RequestBody String student) {
//
//        return s
//    }

    @PostMapping("/request")
    public HttpEntity<Student> httpmessagerequest(HttpEntity<Student> student) {

        Student student1 = new Student();
        student1.setName(student.getBody().getName());
        student1.setGrade(student.getBody().getGrade());

        return new HttpEntity<>(student1);
    }

    @PostMapping("/request2")
    public ResponseEntity<Student> httpmessagerequest(RequestEntity<Student> student) {

        Student student1 = new Student();
        student1.setName(student.getBody().getName());
        student1.setGrade(student.getBody().getGrade());

        return new ResponseEntity<>(student1, HttpStatus.ACCEPTED);
    }

    @Data
    static class Student{

        String name;
        int grade;
    }
}
