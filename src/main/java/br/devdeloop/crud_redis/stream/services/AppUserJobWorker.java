package br.devdeloop.crud_redis.stream.services;

import br.devdeloop.crud_redis.entity.AppUser;
import br.devdeloop.crud_redis.repositories.AppUserRepository;
import io.lettuce.core.Range;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@EnableScheduling
public class AppUserJobWorker {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    private static final String STREAM_KEY = "persistence-jobs";
    private static final String GROUP = "persistence-group";
    private static final String CONSUMER = "persistence-consumer";

    @PostConstruct
    public void init() {
        try {
            redisTemplate.opsForStream().createGroup(STREAM_KEY, ReadOffset.from("0"), GROUP);
            System.out.println("deu certo");
        } catch (Exception e) {
            System.out.println("fudeu");
        }
    }

    @Scheduled(fixedDelay = 2000)
    public ResponseEntity<?> processJob() {
        List<MapRecord<String, Object, Object>> redisRecords =
                redisTemplate.opsForStream().read(
                        Consumer.from(GROUP, CONSUMER),
                        StreamReadOptions.empty().count(10),
                        StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed())
                );

        for (MapRecord<String, Object, Object> record : redisRecords) {
            try {
                Map<Object, Object> recordDataMap = record.getValue();
                AppUser appUser = new AppUser();

                appUser.setUsername(recordDataMap.get("username").toString());
                appUser.setPassword(recordDataMap.get("password").toString());

                appUserRepository.save(appUser);

                redisTemplate.opsForStream().acknowledge(STREAM_KEY, GROUP, record.getId());
            } catch (Exception e) {
                throw new PersistenceException("Fail to persist in database.");
            }
        }
        return ResponseEntity.ok("Entity added into database.");
    }
}
