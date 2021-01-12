package SKProjekat2.Servis3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Servis3Application {

	public static void main(String[] args) {
		SpringApplication.run(Servis3Application.class, args);
	}

}
