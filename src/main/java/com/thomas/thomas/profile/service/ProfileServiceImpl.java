package com.thomas.thomas.profile.service;

import com.thomas.thomas.profile.repository.ProfileRepository;
import com.thomas.thomas.profile.repository.RoleRepository;
import com.thomas.thomas.profile.model.Profile;
import com.thomas.thomas.profile.model.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional @Slf4j
public class ProfileServiceImpl implements ProfileService, UserDetailsService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = profileRepository.findProfileByUsername(username);

        if(profile == null){
            log.error("Profile not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            log.error("Profile found {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //Get all roles of the user and pass it to authorities (SimpleGrantedAuthority)
        profile.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(profile.getUsername(),
                profile.getPassword(),
                authorities);
    }
    @Override
    public Profile saveProfile(Profile profile) {
        log.info("Saving new profile {}", profile.getName());
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profileRepository.save(profile);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToProfile(String username, String roleName) {
        log.info("Adding role {} to profile {}", roleName, username);
        Profile profile = profileRepository.findProfileByUsername(username);
        Role role = roleRepository.findByName(roleName);
        profile.getRoles().add(role);
    }

    @Override
    public Profile getProfileByUsername(String username) {
        log.info("Get profile {}", username);
        return profileRepository.findProfileByUsername(username);
    }

    @Override
    public  Profile getProfileById(Long id){
        log.info("Get profile by ID {}", id);
        return profileRepository.getById(id);
    }

    @Override
    public List<Profile> getProfiles() {
        log.info("Get all profiles");
        return profileRepository.findAll();
    }

}
