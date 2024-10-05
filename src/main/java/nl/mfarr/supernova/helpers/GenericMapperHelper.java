package nl.mfarr.supernova.helpers;

import org.springframework.stereotype.Component;

@Component
public class GenericMapperHelper {

    public <D, E> E mapToEntity(D dto, E entity) {
        // Implement the logic to map from DTO to Entity
        return entity;
    }

    public <E, D> D mapToDto(E entity, D dto) {
        // Implement the logic to map from Entity to DTO
        return dto;
    }
}