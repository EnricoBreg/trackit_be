package it.trackit.mappers;

import it.trackit.dto.UserDto;
import it.trackit.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
}
