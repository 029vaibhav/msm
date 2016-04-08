package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    User findOneByUsername(String username);

    List<User> findAllByDeleted(int deleted);

    List<User> findAllByRoleAndDeleted(Role role,int deleted);

}
