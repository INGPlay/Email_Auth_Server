package server.api.emailAuth.account.domain.subType;

import server.api.emailAuth.common.subType.EnumBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum implements EnumBase {
    ADMIN("관리자"),
    MANAGER("게시자"),
    USER("이용자")
    ;

    private final String text;
}
