package server.api.emailAuth.common.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ResponseForm {
    private Boolean result;

    private Map<String, Object> data;

    public ResponseForm(boolean result) {
        this.result = result;
    }
}
