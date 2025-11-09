package com.phuc.user.mapper;

import com.phuc.user.dto.request.UserCreateRequest;
import com.phuc.user.dto.request.UserUpdateRequest;
import com.phuc.user.dto.response.UserResponse;
import com.phuc.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

