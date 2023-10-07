package com.delivery.app.services;

import com.delivery.app.repositories.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
}
