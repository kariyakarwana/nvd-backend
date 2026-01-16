package com.example.NVD_back;

import com.example.NVD_back.model.Role;
import com.example.NVD_back.model.User;
import com.example.NVD_back.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NvdBackApplication {

	public static void main(String[] args) {

//		Dotenv dotenv = Dotenv.configure()
//				.ignoreIfMissing()
//				.load();
//
//		dotenv.entries().forEach(entry ->
//						System.setProperty(entry.getKey(), entry.getValue())
//		);

		SpringApplication.run(NvdBackApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository repo, PasswordEncoder encoder) {
		return args -> {
			if(repo.count() == 0){
				User admin = new User();
				admin.setEmail("admin@nvd.com");
				admin.setPassword(encoder.encode("admin123"));
				admin.setRole(Role.ADMIN);
				repo.save(admin);
			}
		};
	}

}
