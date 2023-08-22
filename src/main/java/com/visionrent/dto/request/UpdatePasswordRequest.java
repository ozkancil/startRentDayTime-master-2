package com.visionrent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = "Please Provide Old Password")
    private String oldPassword;

    @NotBlank(message = "Please Provide New Password")
    @Size(min = 4, max = 20,message="Please Provide Correct Size for Password")
    private String newPassword;


}
