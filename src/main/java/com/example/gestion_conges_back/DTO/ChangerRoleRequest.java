package com.example.gestion_conges_back.DTO;

import com.example.gestion_conges_back.entity.RoleEnum;

public class ChangerRoleRequest {
    private RoleEnum role;

    public ChangerRoleRequest(RoleEnum role) {
        this.role = role;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

}
