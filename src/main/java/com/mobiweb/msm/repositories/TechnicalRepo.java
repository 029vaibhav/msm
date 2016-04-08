package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Technical;
import com.mobiweb.msm.models.enums.Status;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalRepo extends JpaRepository<Technical, Long> {

    //    @Query("select b from Sales b " +
//            "where b.created between ?1 and ?2")
    List<Technical> findAllByCreatedBetween(DateTime created, DateTime created2);

    List<Technical> findAllByCreatedBetweenAndStatusIn(DateTime created, DateTime created2, List<Status> statuses);


    List<Technical> findByCreatedBetweenAndStatusIn(DateTime startDateTime, DateTime endDateDateTime, List<Status> status);

    List<Technical> findByStatusIn(List<Status> status);

    List<Technical> findAllByUsernameAndCreatedBetween(String username, DateTime created, DateTime created2);


}
