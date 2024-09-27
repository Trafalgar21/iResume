package com.thomas.thomas.profile.repository;

import com.thomas.thomas.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {


    @Query("SELECT p FROM Profile p WHERE p.username = ?1")
    Profile findProfileByEmail(String username);

    Profile findProfileByUsername(String username);

//    @Query("SELECT p FROM Profile p WHERE p.id = ?1")
//    Profile findById(Long id);
}

