package hello.springmvc.basic;
//공용으로 쓸거기 때문에 basic폴더 바로 밑에
import lombok.Data;

/**
 * @Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 를 자동으로 적용
 * 롬복 어노테이션 우클릭, refactor -> delombok 하면 적용된 코드 볼 수 있다
 */
@Data
public class HelloData {
    private String username;
    private int age;
}
