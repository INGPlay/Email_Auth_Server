package server.api.emailAuth.example.domain;

import server.api.emailAuth.common.entity.Example;
import server.api.emailAuth.example.domain.dto.EditExampleDTO;
import server.api.emailAuth.example.domain.dto.ResponseVO;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,    // "spring"
        injectionStrategy = InjectionStrategy.FIELD
)
public interface ExampleMapper {

    @Mapping(source = "editExampleDTO.id", target="id", ignore=true)
    Example requestInsert(EditExampleDTO editExampleDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void requestUpdate(EditExampleDTO editExampleDTO, @MappingTarget Example example);

    ResponseVO entityToResponse(Example example);
}
