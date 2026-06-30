package bf.digital.pilotage.service.user;

import bf.digital.pilotage.dto.request.ChangePasswordRequest;
import bf.digital.pilotage.dto.request.CreateUserRequest;
import bf.digital.pilotage.dto.request.UpdateProfileRequest;
import bf.digital.pilotage.dto.request.UpdateUserRequest;
import bf.digital.pilotage.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
    void desactiverUser(Long id);
    UserResponse getProfile(String email);
    UserResponse updateProfile(String email, UpdateProfileRequest request);
    void changePassword(String email, ChangePasswordRequest request);
}