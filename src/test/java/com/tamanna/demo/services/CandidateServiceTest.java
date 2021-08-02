package com.tamanna.demo.services;

import com.tamanna.demo.exception.EntityNotFoundError;
import com.tamanna.demo.model.dto.CandidateDTO;
import com.tamanna.demo.model.entity.Candidate;
import com.tamanna.demo.model.repository.CandidateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CandidateServiceTest {

    @InjectMocks
    private CandidateService service;

    @Mock
    private CandidateRepository repository;

    @Before
    public void setup() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(new Candidate().setId(1).setName("Suelmar Castro")));
    }

    @Test
    public void find() throws EntityNotFoundError {
        CandidateDTO candidate = service.find(1);

        Assert.assertEquals(new Integer(1), candidate.getId());
        Assert.assertEquals("Suelmar Castro", candidate.getName());
    }

    @Test(expected = EntityNotFoundError.class)
    public void find_with_entity_not_found_Error() throws EntityNotFoundError {
        service.find(2);
    }

}