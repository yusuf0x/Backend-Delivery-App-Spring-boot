package com.delivery.app.controllers;

import com.delivery.app.models.Address;
import com.delivery.app.models.User;
import com.delivery.app.payload.request.ChangePasswordRequest;
import com.delivery.app.payload.request.UserProfileEditRequest;
import com.delivery.app.payload.response.ApiResponse;
import com.delivery.app.payload.response.ApiResponseWithData;
import com.delivery.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> allUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get profile", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get profile", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> editProfile(@PathVariable Long userId,@RequestBody UserProfileEditRequest editRequest) {
        try {
            userService.editUserProfile(userId,editRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Updated Profile"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            userService.changePassword(userId,changePasswordRequest);
            return ResponseEntity.ok(new ApiResponse(true, "Password Changed"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{userId}/change-image-profile")
    public ResponseEntity<?> changeImageProfile(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            userService.changeProfileImage(userId, file);
            return ResponseEntity.ok(new ApiResponse(true, "Picture changed"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    // addresses
    @GetMapping("/{userId}/addresses")
    public ResponseEntity<?> getAddressesForUser(@PathVariable Long userId) {
        try {
            List<Address> addresses = userService.getUserAddresses(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "List the Addresses", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }


    @DeleteMapping("/{userId}/delete-address/{addressId}")
    public ResponseEntity<?> deleteStreetAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        try {
            userService.deleteAddress(userId,addressId);
            return ResponseEntity.ok(new ApiResponse(true, "Address deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/add-address")
    public ResponseEntity<?> addStreetAddress( @PathVariable  Long userId,@RequestBody Address addressRequest) {
        try {
            userService.addAddress(addressRequest, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Address added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/{userId}/get-address")
    public ResponseEntity<?> getAddressOne(@PathVariable Long userId) {
        try {
            Address address = userService.getOneAddress(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "One Address", address));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update-notification-token")
    public ResponseEntity<?> updateNotificationToken(@PathVariable Long userId, @RequestParam String token) {
        try {
            userService.updateNotificationToken(userId, token);
            return ResponseEntity.ok(new ApiResponse(true, "Token updated"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/admin-notification-tokens")
    public ResponseEntity<?> getAdminNotificationTokens() {
        try {
            List<String> tokens = userService.getAdminNotificationTokens();
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/update-delivery-to-client/{deliveryId}")
    public ResponseEntity<?> updateDeliveryToClient(@PathVariable Long deliveryId) {
        try {
            userService.updateUserRoleToClient(deliveryId);
            return ResponseEntity.ok(new ApiResponse(true, "Delivery To Client"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/all-deliveries")
    public ResponseEntity<?> getAllDeliveries() {
        try {
            List<User> deliveries = userService.getAllDeliveries();
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get All Delivery", deliveries));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/all-clients")
    public ResponseEntity<?> getAllClients() {
        try {
            List<User> clients = userService.getAllClients();
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get All Clients", clients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
