package server.api.emailAuth.account.domain;

import server.api.emailAuth.account.domain.subType.OAuthTypeEnum;
import server.api.emailAuth.account.domain.subType.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class RegisterAccountDTO {
    private Long id;

    @Pattern(regexp = "^[a-z0-9]{8,20}$", message = "8 ~ 20자의 영어 소문자 및 숫자로만 구성되야 합니다.")
    private String accountName;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%\\-+]{8,32}", message = "8 ~ 32자의 영어 및 숫자 그리고 특수문자 !,@,#,$,%,-,+를 허용됩니다.")
    private String accountPassword;

    private String accountPasswordCheck;

    @Pattern(regexp = "^[가-힣A-Za-z0-9]{4,20}$", message = "한글 및 영어 숫자로 이루어질 수 있습니다.")
    private String accountDisplayName;

    @NotNull(message = "생성할 계정을 선택해주세요.")
    private RoleEnum accountRole;
    private OAuthTypeEnum oauthType;
}
