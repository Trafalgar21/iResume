package com.thomas.thomas.profile.controller;

import com.thomas.thomas.profile.model.Profile;
import com.thomas.thomas.profile.model.Role;
import com.thomas.thomas.profile.service.ProfileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profiles")
    public ResponseEntity<List<Profile>> getProfiles(){
        return ResponseEntity.ok().body(profileService.getProfiles());
    }

    @PostMapping("/profile/save")
    public ResponseEntity<Profile> saveProfile(@RequestBody Profile profile){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(profileService.saveProfile(profile));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(profileService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        profileService.addRoleToProfile(form.getUsername(), form.getRoleNName());
        return ResponseEntity.ok().build();
    }
}
@Data
class RoleToUserForm{
    private String username;
    private String roleNName;
}