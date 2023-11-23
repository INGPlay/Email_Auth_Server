package server.api.emailAuth.process.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CompareAuthDTO {

    @NotNull
    private String uuid;

    private String inputCode;
}
