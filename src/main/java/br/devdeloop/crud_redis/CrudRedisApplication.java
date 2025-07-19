package br.devdeloop.crud_redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CrudRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudRedisApplication.class, args);
    }

}
