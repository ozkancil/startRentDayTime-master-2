package com.visionrent;

import com.visionrent.controller.UserController;
import com.visionrent.dto.UserDTO;
import com.visionrent.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Test
	void givenAdminRole_whenGetUserById_thenReturnUserDTO() {
		// given
		Long id = 1L;
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		when(userService.getUserById(anyLong())).thenReturn(userDTO);

		// when
		ResponseEntity<UserDTO> responseEntity = userController.getUserById(id);

		// then
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(userDTO, responseEntity.getBody());
	}
}
