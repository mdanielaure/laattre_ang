package com.laattre.backen.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laattre.backen.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
