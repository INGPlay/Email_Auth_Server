package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditAuthProcessDTO {
    private String uuid;
    private RequestAuthDTO requestAuthDTO;
}
