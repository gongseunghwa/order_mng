package kr.co._29cm.homework.order_mng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
@EnableWebMvc
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
