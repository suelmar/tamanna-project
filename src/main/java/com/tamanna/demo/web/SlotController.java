package com.tamanna.demo.web;

import com.tamanna.demo.model.dto.InterviewerSlotDTO;
import com.tamanna.demo.services.InterviewerSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/slot")
public class SlotController {

    @Autowired
    private InterviewerSlotService service;

    @GetMapping(value = "query")
    public ResponseEntity<List<InterviewerSlotDTO>> queryAvailableSlots(
                @RequestParam(name = "candidateId") Integer candidateId, @RequestParam(name = "interviewerId") List<Integer> interviewerIds) {

        return new ResponseEntity<>(service.findAvailableSlots(interviewerIds, candidateId), HttpStatus.OK);
    }
}
