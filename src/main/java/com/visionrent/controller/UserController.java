package com.visionrent.controller;

import com.visionrent.dto.UserDTO;
import com.visionrent.dto.request.AdminUserUpdateRequest;
import com.visionrent.dto.request.UpdatePasswordRequest;
import com.visionrent.dto.request.UserUpdateRequest;
import com.visionrent.dto.response.ResponseMessage;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All endpoints for user management except login and register.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //http://localhost:8080/user/auth/all
    //authorization implemented here
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO>allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    //this service could be called, If logged-in user wants to get its own user information
    //http://localhost:8080/user
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<UserDTO> getUser(){
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);
    }
    /**
     * @param id data of the users in DB.
     *           to get any user in the system, admin is able to use this method.
     *           Example -> <a href="http://localhost:8080/user/3/auth">...</a>
     * @return UserDTO
     */
    //http://localhost:8080/user/1/auth
    //Admin create a request to get a user with id
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("{id}/auth")
    public ResponseEntity<UserDTO>getUserById(@PathVariable Long id){
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    //http://localhost:8080/user/5/auth
    @PreAuthorize("(hasRole('ADMIN'))")
    @DeleteMapping("/{id}/auth")
    public ResponseEntity<VRResponse> deleteUser(@PathVariable Long id){
        userService.removeUserById(id);

        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    //http://localhost:8080/user/auth/pages?page=0&size=2&sort=id&direction=ASC
    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/pages")
    public ResponseEntity<Page<UserDTO>> getAllUsersByPage (@RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sort") String prop,
                                                            @RequestParam(value = "direction",required = false,
                                                            defaultValue = "DESC") Direction direction){
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<UserDTO> userDTOPage = userService.getUserPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }
    /*
     * {
    "firstName": "transaction",
    "lastName": "admin1",
    "phoneNumber": "(555) 555-8828",
    "email": "transaction@email.com",
    "address": "New Jersey,US",
    "zipCode": "79877"
}
     */
    // we are updating the logged-in user
    //http://localhost:8080/user
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<VRResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUser(userUpdateRequest);

        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }
    /*
     * {
        "firstName": "Bruce",
        "lastName": "Wayne",
        "email": "bruce@email.com",
        "phoneNumber": "(555) 444-8828",
        "address": "New Jersey,US",
        "zipCode": "65421",
        "builtIn": false,
        "roles": [
            "Customer",
            "Administrator"
        ]
    }
     */
    //http://localhost:8080/user/3/auth
    // we are updating a user according to its ID
    @PreAuthorize("(hasRole('ADMIN'))")
    @PutMapping("/{id}/auth")
    public ResponseEntity<VRResponse> updateUserAuth(@PathVariable Long id,
                                                     @Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest) {

        userService.updateUserAuth(id,adminUserUpdateRequest);

        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

	/*
	 * {
     "oldPassword":"admin",
     "newPassword":"admin1"
     }
	 */

    //	http://localhost:8080/user/auth
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PatchMapping("/auth")
    public ResponseEntity<VRResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){

        userService.updatePassword(updatePasswordRequest);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_PASSWORD_CHANGED_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);

    }







}
