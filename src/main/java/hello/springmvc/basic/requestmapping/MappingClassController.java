package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

/** Rest API 예씨? */
@RestController
//공통경로 빼놓기, 같은 경로지만 방식에 따라 결과 다르게
@RequestMapping("/mapping/users")
public class MappingClassController {

//    회원 목록 조회: GET /users
//    회원 등록: POST /users
//    회원 조회: GET /users/{userId}
//    회원 수정: PATCH /users/{userId}
//    회원 삭제: DELETE /users/{userId}

    /**
     * GET /mapping/users
     */
    @GetMapping
    public String users() {
        return "get users";
    }

    /**
     * POST /mapping/users
     */
    @PostMapping
    public String addUser() {
        return "post user";
    }

    /**
     * GET /mapping/users/{userId}
     */
    //http://localhost:8080/mapping/users/userA
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    /**
     * PATCH /mapping/users/{userId}
     */
    //수정
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    /**
     * DELETE /mapping/users/{userId}
     */
    //삭제
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }
}

