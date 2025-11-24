package com.phuc.auth.mapper;

import com.phuc.auth.dto.request.UserCreateRequest;
import com.phuc.auth.dto.request.UserUpdateRequest;
import com.phuc.auth.dto.response.UserResponse;
import com.phuc.auth.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.companyId( request.getCompanyId() );
        user.departmentId( request.getDepartmentId() );
        user.email( request.getEmail() );
        user.employeeCode( request.getEmployeeCode() );
        user.fullName( request.getFullName() );
        user.hireDate( request.getHireDate() );
        user.managerId( request.getManagerId() );
        user.positionId( request.getPositionId() );
        user.status( request.getStatus() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setAddress( user.getAddress() );
        userResponse.setAuth0Id( user.getAuth0Id() );
        userResponse.setAvatarUrl( user.getAvatarUrl() );
        userResponse.setCompanyId( user.getCompanyId() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setDepartmentId( user.getDepartmentId() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setEmployeeCode( user.getEmployeeCode() );
        userResponse.setFullName( user.getFullName() );
        userResponse.setHireDate( user.getHireDate() );
        userResponse.setManagerId( user.getManagerId() );
        userResponse.setPhoneNumber( user.getPhoneNumber() );
        userResponse.setPositionId( user.getPositionId() );
        userResponse.setStatus( user.getStatus() );
        userResponse.setUpdatedAt( user.getUpdatedAt() );
        userResponse.setUserId( user.getUserId() );

        return userResponse;
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setAddress( request.getAddress() );
        user.setAvatarUrl( request.getAvatarUrl() );
        user.setCompanyId( request.getCompanyId() );
        user.setDepartmentId( request.getDepartmentId() );
        user.setEmail( request.getEmail() );
        user.setFullName( request.getFullName() );
        user.setHireDate( request.getHireDate() );
        user.setManagerId( request.getManagerId() );
        user.setPhoneNumber( request.getPhoneNumber() );
        user.setPositionId( request.getPositionId() );
        user.setStatus( request.getStatus() );
    }
}
