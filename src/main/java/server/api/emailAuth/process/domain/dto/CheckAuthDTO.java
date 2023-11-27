package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CheckAuthDTO {
    private String email;
    private String authCode;
}
