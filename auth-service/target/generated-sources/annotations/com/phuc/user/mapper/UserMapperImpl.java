package com.phuc.user.mapper;

import com.phuc.user.dto.request.UserCreateRequest;
import com.phuc.user.dto.request.UserUpdateRequest;
import com.phuc.user.dto.response.UserResponse;
import com.phuc.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
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
        user.phone( request.getPhone() );
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

        userResponse.setAuth0Id( user.getAuth0Id() );
        userResponse.setCompanyId( user.getCompanyId() );
        userResponse.setCreatedAt( user.getCreatedAt() );
        userResponse.setDepartmentId( user.getDepartmentId() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setEmployeeCode( user.getEmployeeCode() );
        userResponse.setFullName( user.getFullName() );
        userResponse.setHireDate( user.getHireDate() );
        userResponse.setManagerId( user.getManagerId() );
        userResponse.setPhone( user.getPhone() );
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

        user.setEmail( request.getEmail() );
        user.setFullName( request.getFullName() );
        user.setHireDate( request.getHireDate() );
        user.setPhone( request.getPhone() );
    }
}
