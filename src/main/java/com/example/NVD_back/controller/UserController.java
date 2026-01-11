package com.example.NVD_back.controller;

import com.example.NVD_back.model.Role;
import com.example.NVD_back.model.User;
import com.example.NVD_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Transactional
public class UserController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/healthworker")
    @PreAuthorize("hasRole('ADMIN')")
    public User addHealthWorker(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.HEALTH_WORKER);
        return repo.save(user);
    }

    @PostMapping("/citizen")
    @PreAuthorize("hasAnyRole('ADMIN','HEALTH_WORKER')")
    public User addCitizen(@RequestBody User user){

        if(repo.findByNic(user.getNic()).isPresent())
            throw new RuntimeException("NIC already registered");

        user.setPassword(encoder.encode("123456")); //default Citizen password
        user.setRole(Role.CITIZEN);
        return repo.save(user);
    }

    // ✅ Get all health workers
    @GetMapping("/healthworkers")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEALTH_WORKER')")
    public List<User> getAllHealthWorkers() {
        return repo.findAll().stream()
                .filter(user -> user.getRole() == Role.HEALTH_WORKER)
                .collect(Collectors.toList());
    }

    // ✅ Get health worker by ID
    @GetMapping("/healthworker/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HEALTH_WORKER')")
    public User getHealthWorkerById(@PathVariable Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Health worker not found"));
        if (user.getRole() != Role.HEALTH_WORKER) {
            throw new RuntimeException("User is not a health worker");
        }
        return user;
    }

    // ✅ Update health worker
    @PutMapping("/healthworker/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateHealthWorker(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Health worker not found"));

        user.setFullname(updatedUser.getFullname());
        user.setEmail(updatedUser.getEmail());
        user.setTitle(updatedUser.getTitle());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(updatedUser.getPassword()));
        }

        return repo.save(user);
    }

    // ✅ Delete health worker
    @DeleteMapping("/healthworker/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteHealthWorker(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // ✅ Get current user profile
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public User getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Update own profile (for health workers and citizens)
    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public User updateProfile(@RequestBody Map<String, Object> updates) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update allowed fields
        if (updates.containsKey("fullname")) {
            user.setFullname((String) updates.get("fullname"));
        }
        if (updates.containsKey("dob")) {
            user.setDob(java.time.LocalDate.parse((String) updates.get("dob")));
        }
        if (updates.containsKey("title") && user.getRole() == Role.HEALTH_WORKER) {
            user.setTitle((String) updates.get("title"));
        }
        if (updates.containsKey("phone")) {
            user.setPhone((String) updates.get("phone"));
        }
        if (updates.containsKey("address")) {
            user.setAddress((String) updates.get("address"));
        }

        return repo.save(user);
    }

    // ✅ Change password
    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@RequestBody Map<String, String> passwords) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repo.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldPassword = passwords.get("oldPassword");
        String newPassword = passwords.get("newPassword");

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(encoder.encode(newPassword));
        repo.save(user);

        return "Password changed successfully";
    }

}
