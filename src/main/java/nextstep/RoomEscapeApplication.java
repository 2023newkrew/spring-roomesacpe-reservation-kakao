package nextstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomEscapeApplication {

    public static void main(String[] args) {
        // 스프링 애플리케이션 실행
        SpringApplication.run(RoomEscapeApplication.class, args);
        // 콘솔 애플리케이션 실행
        ConsoleEscapeApplication.run();
    }
}