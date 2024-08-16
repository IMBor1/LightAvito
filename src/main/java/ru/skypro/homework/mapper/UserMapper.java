package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UpdateUserImageDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UpdateUserDto UpdateUserDtoToUser(User user);
    UpdateUserImageDto UpdateUserImageDtoToUser(User user);
    UserSetPasswordDto UserSetPasswordDtoToUser(User user);
    User UserToUpdateUserDto(UpdateUserDto updateUserDto);
    User UserToUpdateUserImageDto(UpdateUserImageDto updateUserImageDto);

    User UserToUserSetPasswordDto(UserSetPasswordDto userSetPasswordDto);

}
