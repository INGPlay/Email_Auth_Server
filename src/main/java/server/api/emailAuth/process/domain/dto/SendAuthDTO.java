package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class SendAuthDTO {

    @NotNull
    private String uuid;

    @Email
    @NotNull
    private String email;
}
