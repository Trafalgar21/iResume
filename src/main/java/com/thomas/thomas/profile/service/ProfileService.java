package com.thomas.thomas.profile.service;

import com.thomas.thomas.profile.model.Profile;
import com.thomas.thomas.profile.model.Role;

import java.util.List;

public interface ProfileService {
    Profile saveProfile(Profile profile);

    Role saveRole(Role role);

    void addRoleToProfile(String username, String roleName);

    Profile getProfileByUsername(String username);

    Profile getProfileById(Long id);

    List<Profile> getProfiles();
}
