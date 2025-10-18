package QCecuring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class QCecuringApplication {

	public static void main(String[] args) {
		SpringApplication.run(QCecuringApplication.class, args);
	}

    @GetMapping("/health-check")
    public String health(){
        return "Okay dude";
    }
}
