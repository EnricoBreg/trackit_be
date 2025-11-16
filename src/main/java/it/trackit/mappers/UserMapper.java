package it.trackit.mappers;

import it.trackit.dto.RegisterUserRequest;
import it.trackit.dto.UpdateUserRequest;
import it.trackit.dto.UserDto;
import it.trackit.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
  User toEntity(RegisterUserRequest request);

  // il valore nullValuePropertyMappingStrategy dice a mapstruct di ignorare
  // i campi null della source e di lasciare così come sono i campi del target
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(UpdateUserRequest request, @MappingTarget User user);
}
