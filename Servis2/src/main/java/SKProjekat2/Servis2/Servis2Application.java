package SKProjekat2.Servis2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Servis2Application {

	public static void main(String[] args) {
		SpringApplication.run(Servis2Application.class, args);
	}

}
