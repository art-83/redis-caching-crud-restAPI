package br.devdeloop.crud_redis.stream.services;

import br.devdeloop.crud_redis.stream.jobs.AppUserJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppUserJobProducer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String STREAM_KEY = "persistence-jobs";

    public ResponseEntity<?> createJob(AppUserJob appUserJob) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", appUserJob.username());
        map.put("password", appUserJob.password());

        redisTemplate
                .opsForStream()
                .add(StreamRecords
                        .newRecord()
                        .in(STREAM_KEY)
                        .ofMap(map));

        return ResponseEntity.ok("Job created.");
    }
}