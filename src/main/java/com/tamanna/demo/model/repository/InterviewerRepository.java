package com.tamanna.demo.model.repository;

import com.tamanna.demo.model.entity.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewerRepository extends JpaRepository<Interviewer, Integer> {

}