package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class RequestAuthDTO {

    @URL
    @NotEmpty
    private String successRedirectUrl;

    @URL
    @NotEmpty
    private String failRedirectUrl;

    @URL
    private String duplicateCheckerUrl;
}
