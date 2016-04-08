package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.IncentiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncentiveMessageRepo extends JpaRepository<IncentiveMessage, Long> {

    List<IncentiveMessage> getAllByBrandAndModel(String brand, String model);
}
