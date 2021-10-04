package com.ebit.apachePOI.ApachePOI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebit.apachePOI.ApachePOI.model.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

}
