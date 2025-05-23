package edu.mondragon.webengl.domain.user.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class PasswordResetTokenStore {

    private static class TokenData {
        String email;
        String code;
        LocalDateTime expiry;
    }

    private final Map<String, TokenData> codeStore = new ConcurrentHashMap<>();

    // ESTE MÉTODO ES NECESARIO
    public String generateAndStoreCode(String email) {
        String code = String.valueOf((int) (Math.random() * 900000) + 100000); // 6 dígitos
        TokenData data = new TokenData();
        data.email = email;
        data.code = code;
        data.expiry = LocalDateTime.now().plusMinutes(15); // válido por 15 minutos
        codeStore.put(email, data);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        TokenData data = codeStore.get(email);
        return data != null && data.code.equals(code) && LocalDateTime.now().isBefore(data.expiry);
    }

    public void removeCode(String email) {
        codeStore.remove(email);
    }
}
