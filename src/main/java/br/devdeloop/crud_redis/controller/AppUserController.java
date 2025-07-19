package br.devdeloop.crud_redis.controller;

import br.devdeloop.crud_redis.stream.jobs.AppUserJob;
import br.devdeloop.crud_redis.stream.services.AppUserJobProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
public class AppUserController {

    @Autowired
    private AppUserJobProducer appUserJobProducer;

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody AppUserJob appUserJob) {
        return appUserJobProducer.createJob(appUserJob);
    }
}
