package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.UserRequestDto;
import nl.mfarr.supernova.dtos.UserResponseDto;
import nl.mfarr.supernova.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserEntity toEntity(UserRequestDto dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(dto.getPassword());
        return userEntity;
    }


    public UserResponseDto toDto(UserEntity entity) {
        return new UserResponseDto(entity.getUsername());
    }
}
