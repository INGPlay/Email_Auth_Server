package server.api.emailAuth.config.converter;

import server.api.emailAuth.common.subType.ActiveStatus;
import server.api.emailAuth.common.subType.EnumBase;
import org.springframework.core.convert.converter.Converter;

public class StringToActiveStatusConverter implements Converter<String, ActiveStatus> {
    @Override
    public ActiveStatus convert(String s) {
        return EnumBase.getEnumFromString(ActiveStatus.class, s);
    }
}
