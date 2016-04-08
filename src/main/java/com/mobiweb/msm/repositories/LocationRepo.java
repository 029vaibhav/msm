package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Location;
import com.mobiweb.msm.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {

    Location findOneByRole(Role role);

}
