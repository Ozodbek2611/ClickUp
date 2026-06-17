package uz.pdp.online.clickup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClickUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickUpApplication.class, args);
    }

}
