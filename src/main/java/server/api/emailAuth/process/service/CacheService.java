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
import server.api.emailAuth.exceptionHandler.exception.WrongCodeRepeatExcepton;
import server.api.emailAuth.process.domain.dto.EditAuthProcessDTO;
import server.api.emailAuth.process.domain.dto.RequestAuthDTO;

import java.util.concurrent.TimeUnit;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CacheService {
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;

    @Value("${redis:path:email}")
    private String EMAIL_AUTH_PATH;
    public void addRequestProcess(EditAuthProcessDTO editAuthProcessDTO) throws JsonProcessingException {

        final String KEY = getKey(editAuthProcessDTO.getUuid());
        hashOperations.putIfAbsent(KEY,
                "request",
                objectMapper.writeValueAsString(editAuthProcessDTO.getRequestAuthDTO())
        );
        expireForEmailInput(KEY);
    }

    public void addCheckProcess(String uuid, String authCode){

        final String KEY = getKey(uuid);

        hashOperations.put(KEY, "authCode", authCode);

        expireForAuthCode(KEY);
    }

    public void countRepeat(String uuid) throws JsonProcessingException {
        final String KEY = getKey(uuid);
        // 시간 갱신
        expireForEmailInput(KEY);
        Long count = hashOperations.increment(KEY, "count", 1);
        log.info("counting : {}", count);
        if (count >= 3){
            RequestAuthDTO request = objectMapper.readValue(
                    (String) hashOperations.get(KEY, "request"), RequestAuthDTO.class
            );
            redisTemplate.delete(KEY);
            throw new WrongCodeRepeatExcepton(request.getFailRedirectUrl());
        }
    }

    public String getCounting(String uuid){
        final String KEY = getKey(uuid);
        return (String) hashOperations.get(KEY, "count");
    }

    public boolean compareAuthCode(String uuid, String inputCode){
        final String KEY = getKey(uuid);
        String authCode = hashOperations.get(KEY, "authCode").toString();
        hashOperations.delete(KEY, "authCode");

        log.info("authCode : {}", authCode);
        log.info("inputCode : {}", inputCode);
        if (authCode.equals(inputCode)){
            return true;
        }

        return false;
    }

    public RequestAuthDTO getRequest(String uuid) throws JsonProcessingException {
        final String KEY = getKey(uuid);
        return objectMapper.readValue((String) hashOperations.get(KEY, "request"), RequestAuthDTO.class);
    }

    public boolean isExistKey(String uuid){
        String KEY = getKey(uuid);
        Long expire = redisTemplate.getExpire(KEY);
        if (expire != null && expire > 0){
            return true;
        }

        return false;
    }

    public Long getTtl(String uuid){
        String KEY = getKey(uuid);
        return redisTemplate.getExpire(KEY);
    }

    public void deleteKey(String uuid){
        String KEY = getKey(uuid);
        redisTemplate.delete(uuid);
    }

    private void expireForEmailInput(String KEY) {
        redisTemplate.expire(KEY, 15, TimeUnit.MINUTES);
    }

    private void expireForAuthCode(String KEY) {
        redisTemplate.expire(KEY, 5, TimeUnit.MINUTES);
    }

    private String getKey(String uuid) {
        return EMAIL_AUTH_PATH + uuid;
    }
}
