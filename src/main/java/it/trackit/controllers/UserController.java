package it.trackit.controllers;

import it.trackit.commons.exceptions.ErrorDto;
import it.trackit.commons.exceptions.UserExistsException;
import it.trackit.commons.exceptions.UserNotFoundException;
import it.trackit.dtos.*;
import it.trackit.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final MessageSource messageSource;

  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public PaginatedResponse<UserDto> getAllUsers(
    @PageableDefault(size = 15, sort = {"nome"}) Pageable pageable,
    @RequestParam(name = "search", required = false) String searchText
  ) {
    return userService.getAllUsers(pageable, searchText);
  }

  @GetMapping("/{id}")
  //@PreAuthorize("@userSecurityRules.isCurrentAuthenticatedUser(#userId)")
  @PreAuthorize("isAuthenticated()")
  public UserDto getUser(@PathVariable("id") Long userId) {
    return userService.getUser(userId);
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public UserDto me() {
    return userService.getCurrentAuthenticatedUser();
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
  public ResponseEntity<?> registerUser(
      @Valid @RequestBody RegisterUserRequest request,
      UriComponentsBuilder uriBuilder
  ) {
    var userDto = userService.registerUser(request);
    var uri = uriBuilder.path("/user/{id}").buildAndExpand(userDto.getId()).toUri();
    return ResponseEntity.created(uri).body(userDto);
  }

  @PutMapping("/{id}")
  @PreAuthorize("@userSecurityRules.isCurrentAuthenticatedUser(#userId)")
  public UserDto updateUser(
      @PathVariable("id") Long userId,
      @RequestBody UpdateUserRequest request
  ) {
    return userService.updateUser(userId, request);
  }

  @PostMapping("/{id}/password")
  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
  public ResponseEntity<?> changePassword(
      @PathVariable("id") Long userId,
      @Valid @RequestBody ChangePasswordRequest request
  ) {
    userService.changeUserPassword(userId, request.getNewPassword());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
  public void deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
  }

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<ErrorDto> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      ErrorDto.builder()
        .error("NOT_FOUND")
        .message(ex.getMessage())
        .timestamp(System.currentTimeMillis())
        .build()
    );
  }

  @ExceptionHandler({UserExistsException.class})
  public ResponseEntity<ErrorDto> getCurrentAuthenticatedUser(UserExistsException ex) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
      ErrorDto.builder()
        .error("VALIDATION_ERROR")
        .message(ex.getMessage())
        .errors(ex.getFieldErrors())
        .timestamp(System.currentTimeMillis())
        .build()
    );
  }
}
