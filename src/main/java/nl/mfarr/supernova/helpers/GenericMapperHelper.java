package nl.mfarr.supernova.helpers;
import org.modelmapper.ModelMapper;

public class GenericMapperHelper {

    private static final ModelMapper modelMapper = new ModelMapper();

    // Generic method to map a DTO to an entity
    public static <D, E> E mapToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    // Generic method to map an entity to a DTO
    public static <E, D> D mapToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
