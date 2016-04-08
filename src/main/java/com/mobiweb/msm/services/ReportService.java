package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Leader;

import java.util.List;

public interface ReportService {

    List<Leader> getLeaderBoard(String startDate,String endDate);



}
