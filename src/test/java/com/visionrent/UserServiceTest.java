package com.visionrent;

import com.visionrent.domain.User;
import com.visionrent.exception.ResourceNotFoundException;
import com.visionrent.repository.UserRepository;
import com.visionrent.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void givenValidId_whenGetById_thenReturnUser() {
		// given
		Long id = 1L;
		User user = new User();
		user.setId(id);
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		// when
		User result = userService.getById(id);

		// then
		assertEquals(id, result.getId());
	}

	@Test
	void givenInvalidId_whenGetById_thenThrowException() {
		// given
		Long id = 2L;
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		// when/then
		assertThrows(ResourceNotFoundException.class, () -> userService.getById(id));
	}
}
