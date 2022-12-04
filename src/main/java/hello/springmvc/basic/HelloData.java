package hello.springmvc.basic;
//공용으로 쓸거기 때문에 basic폴더 바로 밑에
import lombok.Data;

//@Getter , @Setter , @ToString , @EqualsAndHashCode , @RequiredArgsConstructor 를
//자동으로 적용
@Data
public class HelloData {
    private String username;
    private int age;
}
