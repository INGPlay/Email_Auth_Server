package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class RequestAuthDTO {

    @URL
    @NotEmpty
    private String redirectUrl;

    @URL
    @NotEmpty
    private String failRedirectUrl;
}
