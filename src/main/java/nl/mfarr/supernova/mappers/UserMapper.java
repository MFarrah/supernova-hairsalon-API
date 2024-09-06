package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.UserRequestDto;
import nl.mfarr.supernova.dtos.UserResponseDto;
import nl.mfarr.supernova.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserRequestDto userRequestDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setPassword(userRequestDto.getPassword());
        return userEntity;
    }

    public static UserResponseDto toResponseDto(UserEntity userEntity) {
        return new UserResponseDto(userEntity.getId(), userEntity.getUsername(), userEntity.getRoles());
    }
}
