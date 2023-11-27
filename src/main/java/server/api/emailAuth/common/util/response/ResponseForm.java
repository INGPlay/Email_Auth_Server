package server.api.emailAuth.common.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseForm {
    private Boolean result;

    private Map<String, Object> data;

    public ResponseForm(boolean result) {
        this.result = result;
    }
}
