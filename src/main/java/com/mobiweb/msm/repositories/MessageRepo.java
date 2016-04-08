package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Message;
import com.mobiweb.msm.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findAllByRole(Role role);
}
