package server.api.emailAuth.process.service.util;

import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class CodeGenerator {

    public String getRandomNumberCode(int lenth) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lenth; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
