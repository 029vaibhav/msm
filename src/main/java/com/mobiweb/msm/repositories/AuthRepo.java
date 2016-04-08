package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Long> {


    List<Auth> findAllByUsername(String username);

    Auth findOneByAuth(String authToken);

}
