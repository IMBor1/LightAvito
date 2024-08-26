package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.RegisterDto;
import ru.skypro.homework.dto.user.GetUserInfoDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UpdateUserImageDto;
import ru.skypro.homework.dto.user.UserSetPasswordDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UpdateUserDto registerToUpdateUserDto(RegisterDto registerDto);
    UpdateUserDto UserToUpdateUserDto(User user);
    User UpdateUserDtoToUser(UpdateUserDto updateUserDto);
    User UserSetPasswordDtoToUser(UserSetPasswordDto userSetPasswordDto);
    UserSetPasswordDto UserToUserSetPasswordDto(User user);

    GetUserInfoDto UserToGetUserInfo(User user);
    User GetUserInfoToUser(GetUserInfoDto getUserInfoDto);
    @Mapping(target = "image", source = "avatar.filePath")
    UpdateUserImageDto UpdateUserImageDtoToUser(User user);
    @Mapping(target = "avatar.filePath", source = "image")
    User UserToUpdateUserImageDto(UpdateUserImageDto updateUserImageDto);
}
