package com.tamanna.demo.web;

import com.tamanna.demo.exception.EntityAlreadyExistsError;
import com.tamanna.demo.exception.EntityNotFoundError;
import com.tamanna.demo.exception.ValidationError;
import com.tamanna.demo.model.dto.CandidateDTO;
import com.tamanna.demo.model.dto.CandidateSlotDTO;
import com.tamanna.demo.services.CandidateService;
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
@RequestMapping(value = "/candidate")
public class CandidateController {

    @Autowired
    private CandidateService service;

    @GetMapping(value = "{id}")
    public ResponseEntity<CandidateDTO> getOne(@PathVariable(value = "id") Integer candidateId) {

        try {
            return new ResponseEntity<>(service.find(candidateId), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> create(@RequestBody CandidateDTO CandidateDTO) {

        try {
            return new ResponseEntity<>(service.create(CandidateDTO), HttpStatus.CREATED);
        } catch(EntityAlreadyExistsError ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Candidate already exists.");
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<CandidateDTO> update(@PathVariable(value = "id") Integer candidateId,
                                                 @RequestBody CandidateDTO CandidateDTO) {

        try {
            return new ResponseEntity<>(service.update(candidateId, CandidateDTO), HttpStatus.CREATED);
        } catch(EntityAlreadyExistsError ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Candidate already exists.");
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteInterviewer(@PathVariable(value = "id") Integer candidateId) {
        try {
            service.delete(candidateId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @GetMapping(value = "{id}/slots")
    public ResponseEntity<List<CandidateSlotDTO>> findSlotsByCandidate(@PathVariable(value = "id") Integer candidateId) {
        try {
            return new ResponseEntity<>(service.findSlotsByCandidate(candidateId), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "{id}/slot")
    public ResponseEntity<CandidateSlotDTO> createSlots(@PathVariable(value = "id") Integer candidateId,
            @RequestBody CandidateSlotDTO candidateSlotDTO) {
        try {
            return new ResponseEntity<>(service.createSlot(candidateId, candidateSlotDTO), HttpStatus.OK);
        } catch (EntityNotFoundError ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (ValidationError ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}