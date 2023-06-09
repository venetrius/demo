package arguewise.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
@SpringBootApplication
public class ArgueWise {

	public static void main(String[] args) {
		SpringApplication.run(ArgueWise.class, args);
	}

	@PostMapping
	public String helloWorld() {
		return  "Hello World!";
	}
}
