package com.visionrent;

import com.visionrent.domain.Role;
import com.visionrent.domain.enums.RoleType;
import com.visionrent.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryTest {

	@Mock
	private RoleRepository roleRepository;

	@Test
	void givenRoleType_whenFindByType_thenReturnRole() {
		// given
		RoleType roleType = RoleType.ROLE_ADMIN;
		Role expectedRole = new Role();
		expectedRole.setType(roleType);
		when(roleRepository.findByType(eq(roleType))).thenReturn(Optional.of(expectedRole));

		// when
		Optional<Role> result = roleRepository.findByType(roleType);

		// then
		assertEquals(expectedRole, result.get());
	}

	@Test
	void givenNonExistingRoleType_whenFindByType_thenReturnEmptyOptional() {
		// given
		RoleType roleType = RoleType.ROLE_CUSTOMER;
		when(roleRepository.findByType(eq(roleType))).thenReturn(Optional.empty());

		// when
		Optional<Role> result = roleRepository.findByType(roleType);

		// then
		assertEquals(Optional.empty(), result);
	}
}

