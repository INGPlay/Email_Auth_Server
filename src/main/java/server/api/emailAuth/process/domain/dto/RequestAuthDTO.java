package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class RequestAuthDTO {

    @URL
    @NotNull
    private String redirectUrl;
}
