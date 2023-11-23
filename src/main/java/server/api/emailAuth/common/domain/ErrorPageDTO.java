package server.api.emailAuth.common.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorPageDTO {
    private String errorMessage;
    private String referer;
}
