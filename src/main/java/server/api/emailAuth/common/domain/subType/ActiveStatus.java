package server.api.emailAuth.common.domain.subType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ActiveStatus implements EnumBase {
    ACTIVE("활성화"),
    INACTIVE("비활성화")
    ;

    private final String text;
    @Override
    public String getText() {
        return this.text;
    }
}
