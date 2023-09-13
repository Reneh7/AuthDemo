package com.example.Authentication.Repositories;

import com.example.Authentication.Models.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepo extends JpaRepository<SiteUser,Long> {
    SiteUser findByUsername(String username);
}
