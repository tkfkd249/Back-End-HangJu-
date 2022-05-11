package com.project.hang.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hang.entity.Authority;
import com.project.hang.entity.User;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String userid;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    private Set<AuthorityDto> authorityDtoSet;


    public static UserDto from(User user) {

        if(user == null) return null;

        AuthorityDto authority = AuthorityDto.builder()
                .authorityName("ROLE_USER")
                .build();

        return UserDto.builder()
                .userid(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityDtoSet(Collections.singleton(authority))
                .build();
    }

}