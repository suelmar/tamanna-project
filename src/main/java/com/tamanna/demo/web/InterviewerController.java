package com.tamanna.demo.web;

import com.tamanna.demo.exception.EntityNotFoundError;
import com.tamanna.demo.exception.EntityAlreadyExistsError;
import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.dto.InterviewerDTO;
import com.tamanna.demo.model.dto.InterviewerSlotDTO;
import com.tamanna.demo.services.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/interviewer")
public class InterviewerController {

    @Autowired
    private InterviewerService service;

    @GetMapping
    public ResponseEntity<List<InterviewerDTO>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<InterviewerDTO> getOne(@PathVariable(value = "id") Integer interviewerId) {

        try {
            return new ResponseEntity<>(service.find(interviewerId), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping
    public ResponseEntity<InterviewerDTO> create(@RequestBody InterviewerDTO interviewerDTO) {

        try {
            return new ResponseEntity<>(service.create(interviewerDTO), HttpStatus.CREATED);
        } catch(EntityAlreadyExistsError ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Interviewer already exists.");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<InterviewerDTO> update(@PathVariable(value = "id") Integer interviewerId,
            @RequestBody InterviewerDTO interviewerDTO) {

        try {
            return new ResponseEntity<>(service.update(interviewerId, interviewerDTO), HttpStatus.CREATED);
        } catch(EntityAlreadyExistsError ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Interviewer already exists.");
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteInterviewer(@PathVariable(value = "id") Integer interviewerId) {
        try {
            service.delete(interviewerId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping(value = "{id}/slots")
    public ResponseEntity<List<InterviewerSlotDTO>> findSlotsByInterviewer(@PathVariable(value = "id") Integer interviewerId) {
        try {
            return new ResponseEntity<>(service.findSlotsByInterviewer(interviewerId), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "{id}/slot")
    public ResponseEntity<InterviewerSlotDTO> createSlots(@PathVariable(value = "id") Integer interviewerId,
            @RequestBody InterviewerSlotDTO interviewerSlotDTO) {
        try {
            return new ResponseEntity<>(service.createSlot(interviewerId, interviewerSlotDTO), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (ValidationError ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}