package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.CommunicationEvent;
import com.hr.repository.CommunicationEventRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hr.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommunicationEventResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommunicationEventResourceIT {

    private static final ZonedDateTime DEFAULT_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_STRING = "AAAAAAAAAA";
    private static final String UPDATED_FROM_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_TO_STRING = "AAAAAAAAAA";
    private static final String UPDATED_TO_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_CC_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CC_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_STARTED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_STARTED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_ENDED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_ENDED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private CommunicationEventRepository communicationEventRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunicationEventMockMvc;

    private CommunicationEvent communicationEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationEvent createEntity(EntityManager em) {
        CommunicationEvent communicationEvent = new CommunicationEvent()
            .entryDate(DEFAULT_ENTRY_DATE)
            .subject(DEFAULT_SUBJECT)
            .content(DEFAULT_CONTENT)
            .fromString(DEFAULT_FROM_STRING)
            .toString(DEFAULT_TO_STRING)
            .ccString(DEFAULT_CC_STRING)
            .message(DEFAULT_MESSAGE)
            .dateStarted(DEFAULT_DATE_STARTED)
            .dateEnded(DEFAULT_DATE_ENDED)
            .info(DEFAULT_INFO);
        return communicationEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationEvent createUpdatedEntity(EntityManager em) {
        CommunicationEvent communicationEvent = new CommunicationEvent()
            .entryDate(UPDATED_ENTRY_DATE)
            .subject(UPDATED_SUBJECT)
            .content(UPDATED_CONTENT)
            .fromString(UPDATED_FROM_STRING)
            .toString(UPDATED_TO_STRING)
            .ccString(UPDATED_CC_STRING)
            .message(UPDATED_MESSAGE)
            .dateStarted(UPDATED_DATE_STARTED)
            .dateEnded(UPDATED_DATE_ENDED)
            .info(UPDATED_INFO);
        return communicationEvent;
    }

    @BeforeEach
    public void initTest() {
        communicationEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommunicationEvent() throws Exception {
        int databaseSizeBeforeCreate = communicationEventRepository.findAll().size();
        // Create the CommunicationEvent
        restCommunicationEventMockMvc.perform(post("/api/communication-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEvent)))
            .andExpect(status().isCreated());

        // Validate the CommunicationEvent in the database
        List<CommunicationEvent> communicationEventList = communicationEventRepository.findAll();
        assertThat(communicationEventList).hasSize(databaseSizeBeforeCreate + 1);
        CommunicationEvent testCommunicationEvent = communicationEventList.get(communicationEventList.size() - 1);
        assertThat(testCommunicationEvent.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testCommunicationEvent.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testCommunicationEvent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCommunicationEvent.getFromString()).isEqualTo(DEFAULT_FROM_STRING);
        assertThat(testCommunicationEvent.getToString()).isEqualTo(DEFAULT_TO_STRING);
        assertThat(testCommunicationEvent.getCcString()).isEqualTo(DEFAULT_CC_STRING);
        assertThat(testCommunicationEvent.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCommunicationEvent.getDateStarted()).isEqualTo(DEFAULT_DATE_STARTED);
        assertThat(testCommunicationEvent.getDateEnded()).isEqualTo(DEFAULT_DATE_ENDED);
        assertThat(testCommunicationEvent.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createCommunicationEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = communicationEventRepository.findAll().size();

        // Create the CommunicationEvent with an existing ID
        communicationEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationEventMockMvc.perform(post("/api/communication-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEvent)))
            .andExpect(status().isBadRequest());

        // Validate the CommunicationEvent in the database
        List<CommunicationEvent> communicationEventList = communicationEventRepository.findAll();
        assertThat(communicationEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommunicationEvents() throws Exception {
        // Initialize the database
        communicationEventRepository.saveAndFlush(communicationEvent);

        // Get all the communicationEventList
        restCommunicationEventMockMvc.perform(get("/api/communication-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communicationEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].fromString").value(hasItem(DEFAULT_FROM_STRING)))
            .andExpect(jsonPath("$.[*].toString").value(hasItem(DEFAULT_TO_STRING)))
            .andExpect(jsonPath("$.[*].ccString").value(hasItem(DEFAULT_CC_STRING)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].dateStarted").value(hasItem(sameInstant(DEFAULT_DATE_STARTED))))
            .andExpect(jsonPath("$.[*].dateEnded").value(hasItem(sameInstant(DEFAULT_DATE_ENDED))))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }
    
    @Test
    @Transactional
    public void getCommunicationEvent() throws Exception {
        // Initialize the database
        communicationEventRepository.saveAndFlush(communicationEvent);

        // Get the communicationEvent
        restCommunicationEventMockMvc.perform(get("/api/communication-events/{id}", communicationEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communicationEvent.getId().intValue()))
            .andExpect(jsonPath("$.entryDate").value(sameInstant(DEFAULT_ENTRY_DATE)))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.fromString").value(DEFAULT_FROM_STRING))
            .andExpect(jsonPath("$.toString").value(DEFAULT_TO_STRING))
            .andExpect(jsonPath("$.ccString").value(DEFAULT_CC_STRING))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.dateStarted").value(sameInstant(DEFAULT_DATE_STARTED)))
            .andExpect(jsonPath("$.dateEnded").value(sameInstant(DEFAULT_DATE_ENDED)))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCommunicationEvent() throws Exception {
        // Get the communicationEvent
        restCommunicationEventMockMvc.perform(get("/api/communication-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommunicationEvent() throws Exception {
        // Initialize the database
        communicationEventRepository.saveAndFlush(communicationEvent);

        int databaseSizeBeforeUpdate = communicationEventRepository.findAll().size();

        // Update the communicationEvent
        CommunicationEvent updatedCommunicationEvent = communicationEventRepository.findById(communicationEvent.getId()).get();
        // Disconnect from session so that the updates on updatedCommunicationEvent are not directly saved in db
        em.detach(updatedCommunicationEvent);
        updatedCommunicationEvent
            .entryDate(UPDATED_ENTRY_DATE)
            .subject(UPDATED_SUBJECT)
            .content(UPDATED_CONTENT)
            .fromString(UPDATED_FROM_STRING)
            .toString(UPDATED_TO_STRING)
            .ccString(UPDATED_CC_STRING)
            .message(UPDATED_MESSAGE)
            .dateStarted(UPDATED_DATE_STARTED)
            .dateEnded(UPDATED_DATE_ENDED)
            .info(UPDATED_INFO);

        restCommunicationEventMockMvc.perform(put("/api/communication-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommunicationEvent)))
            .andExpect(status().isOk());

        // Validate the CommunicationEvent in the database
        List<CommunicationEvent> communicationEventList = communicationEventRepository.findAll();
        assertThat(communicationEventList).hasSize(databaseSizeBeforeUpdate);
        CommunicationEvent testCommunicationEvent = communicationEventList.get(communicationEventList.size() - 1);
        assertThat(testCommunicationEvent.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testCommunicationEvent.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testCommunicationEvent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCommunicationEvent.getFromString()).isEqualTo(UPDATED_FROM_STRING);
        assertThat(testCommunicationEvent.getToString()).isEqualTo(UPDATED_TO_STRING);
        assertThat(testCommunicationEvent.getCcString()).isEqualTo(UPDATED_CC_STRING);
        assertThat(testCommunicationEvent.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCommunicationEvent.getDateStarted()).isEqualTo(UPDATED_DATE_STARTED);
        assertThat(testCommunicationEvent.getDateEnded()).isEqualTo(UPDATED_DATE_ENDED);
        assertThat(testCommunicationEvent.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingCommunicationEvent() throws Exception {
        int databaseSizeBeforeUpdate = communicationEventRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationEventMockMvc.perform(put("/api/communication-events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(communicationEvent)))
            .andExpect(status().isBadRequest());

        // Validate the CommunicationEvent in the database
        List<CommunicationEvent> communicationEventList = communicationEventRepository.findAll();
        assertThat(communicationEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommunicationEvent() throws Exception {
        // Initialize the database
        communicationEventRepository.saveAndFlush(communicationEvent);

        int databaseSizeBeforeDelete = communicationEventRepository.findAll().size();

        // Delete the communicationEvent
        restCommunicationEventMockMvc.perform(delete("/api/communication-events/{id}", communicationEvent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommunicationEvent> communicationEventList = communicationEventRepository.findAll();
        assertThat(communicationEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
