package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FramesApplication {
	public static void main(String[] args) {
		SpringApplication.run(FramesApplication.class, args);
	}
}