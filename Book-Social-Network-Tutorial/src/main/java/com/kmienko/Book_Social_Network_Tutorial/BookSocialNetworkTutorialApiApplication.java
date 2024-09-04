package com.kmienko.Book_Social_Network_Tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookSocialNetworkTutorialApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSocialNetworkTutorialApiApplication.class, args);
	}

}
