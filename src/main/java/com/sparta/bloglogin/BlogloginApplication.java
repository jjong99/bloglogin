package com.sparta.bloglogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogloginApplication.class, args);
    }

}
