package ir.smia.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
//@EnableDiscoveryClient
@SpringBootApplication
//@EnableFeignClients
public class OrganizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationApplication.class, args);
	}

//	@LoadBalanced
//	@Bean
//	public RestTemplate getRestTemplate() {
//		return new RestTemplate();
//	}

}
