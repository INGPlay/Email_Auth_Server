package server.api.emailAuth.process.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import server.api.emailAuth.process.domain.dto.EditAuthProcessDTO;

import java.util.concurrent.TimeUnit;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CacheService {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;

    @Value("${redis:path:email}")
    private String EMAIL_AUTH_PATH;
    public void addRequestProcess(EditAuthProcessDTO editAuthProcessDTO) throws JsonProcessingException {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        final String KEY = getKey(editAuthProcessDTO.getUuid());
        hashOperations.putIfAbsent(KEY,
                "request",
                objectMapper.writeValueAsString(editAuthProcessDTO.getRequestAuthDTO())
        );
        redisTemplate.expire(KEY, 30, TimeUnit.MINUTES);
    }

    public String getKey(String uuid) {
        return EMAIL_AUTH_PATH + uuid;
    }
}
