package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ContactMech;
import com.hr.domain.ContactMechType;
import com.hr.repository.ContactMechRepository;
import com.hr.service.ContactMechService;
import com.hr.service.dto.ContactMechCriteria;
import com.hr.service.ContactMechQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContactMechResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechResourceIT {

    private static final String DEFAULT_INFO_STRING = "AAAAAAAAAA";
    private static final String UPDATED_INFO_STRING = "BBBBBBBBBB";

    @Autowired
    private ContactMechRepository contactMechRepository;

    @Autowired
    private ContactMechService contactMechService;

    @Autowired
    private ContactMechQueryService contactMechQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechMockMvc;

    private ContactMech contactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMech createEntity(EntityManager em) {
        ContactMech contactMech = new ContactMech()
            .infoString(DEFAULT_INFO_STRING);
        return contactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMech createUpdatedEntity(EntityManager em) {
        ContactMech contactMech = new ContactMech()
            .infoString(UPDATED_INFO_STRING);
        return contactMech;
    }

    @BeforeEach
    public void initTest() {
        contactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMech() throws Exception {
        int databaseSizeBeforeCreate = contactMechRepository.findAll().size();
        // Create the ContactMech
        restContactMechMockMvc.perform(post("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isCreated());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMech testContactMech = contactMechList.get(contactMechList.size() - 1);
        assertThat(testContactMech.getInfoString()).isEqualTo(DEFAULT_INFO_STRING);
    }

    @Test
    @Transactional
    public void createContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechRepository.findAll().size();

        // Create the ContactMech with an existing ID
        contactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechMockMvc.perform(post("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMeches() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList
        restContactMechMockMvc.perform(get("/api/contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoString").value(hasItem(DEFAULT_INFO_STRING)));
    }
    
    @Test
    @Transactional
    public void getContactMech() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get the contactMech
        restContactMechMockMvc.perform(get("/api/contact-meches/{id}", contactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMech.getId().intValue()))
            .andExpect(jsonPath("$.infoString").value(DEFAULT_INFO_STRING));
    }


    @Test
    @Transactional
    public void getContactMechesByIdFiltering() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        Long id = contactMech.getId();

        defaultContactMechShouldBeFound("id.equals=" + id);
        defaultContactMechShouldNotBeFound("id.notEquals=" + id);

        defaultContactMechShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactMechShouldNotBeFound("id.greaterThan=" + id);

        defaultContactMechShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactMechShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContactMechesByInfoStringIsEqualToSomething() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString equals to DEFAULT_INFO_STRING
        defaultContactMechShouldBeFound("infoString.equals=" + DEFAULT_INFO_STRING);

        // Get all the contactMechList where infoString equals to UPDATED_INFO_STRING
        defaultContactMechShouldNotBeFound("infoString.equals=" + UPDATED_INFO_STRING);
    }

    @Test
    @Transactional
    public void getAllContactMechesByInfoStringIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString not equals to DEFAULT_INFO_STRING
        defaultContactMechShouldNotBeFound("infoString.notEquals=" + DEFAULT_INFO_STRING);

        // Get all the contactMechList where infoString not equals to UPDATED_INFO_STRING
        defaultContactMechShouldBeFound("infoString.notEquals=" + UPDATED_INFO_STRING);
    }

    @Test
    @Transactional
    public void getAllContactMechesByInfoStringIsInShouldWork() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString in DEFAULT_INFO_STRING or UPDATED_INFO_STRING
        defaultContactMechShouldBeFound("infoString.in=" + DEFAULT_INFO_STRING + "," + UPDATED_INFO_STRING);

        // Get all the contactMechList where infoString equals to UPDATED_INFO_STRING
        defaultContactMechShouldNotBeFound("infoString.in=" + UPDATED_INFO_STRING);
    }

    @Test
    @Transactional
    public void getAllContactMechesByInfoStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString is not null
        defaultContactMechShouldBeFound("infoString.specified=true");

        // Get all the contactMechList where infoString is null
        defaultContactMechShouldNotBeFound("infoString.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactMechesByInfoStringContainsSomething() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString contains DEFAULT_INFO_STRING
        defaultContactMechShouldBeFound("infoString.contains=" + DEFAULT_INFO_STRING);

        // Get all the contactMechList where infoString contains UPDATED_INFO_STRING
        defaultContactMechShouldNotBeFound("infoString.contains=" + UPDATED_INFO_STRING);
    }

    @Test
    @Transactional
    public void getAllContactMechesByInfoStringNotContainsSomething() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);

        // Get all the contactMechList where infoString does not contain DEFAULT_INFO_STRING
        defaultContactMechShouldNotBeFound("infoString.doesNotContain=" + DEFAULT_INFO_STRING);

        // Get all the contactMechList where infoString does not contain UPDATED_INFO_STRING
        defaultContactMechShouldBeFound("infoString.doesNotContain=" + UPDATED_INFO_STRING);
    }


    @Test
    @Transactional
    public void getAllContactMechesByContactMechTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactMechRepository.saveAndFlush(contactMech);
        ContactMechType contactMechType = ContactMechTypeResourceIT.createEntity(em);
        em.persist(contactMechType);
        em.flush();
        contactMech.setContactMechType(contactMechType);
        contactMechRepository.saveAndFlush(contactMech);
        Long contactMechTypeId = contactMechType.getId();

        // Get all the contactMechList where contactMechType equals to contactMechTypeId
        defaultContactMechShouldBeFound("contactMechTypeId.equals=" + contactMechTypeId);

        // Get all the contactMechList where contactMechType equals to contactMechTypeId + 1
        defaultContactMechShouldNotBeFound("contactMechTypeId.equals=" + (contactMechTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactMechShouldBeFound(String filter) throws Exception {
        restContactMechMockMvc.perform(get("/api/contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoString").value(hasItem(DEFAULT_INFO_STRING)));

        // Check, that the count call also returns 1
        restContactMechMockMvc.perform(get("/api/contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactMechShouldNotBeFound(String filter) throws Exception {
        restContactMechMockMvc.perform(get("/api/contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactMechMockMvc.perform(get("/api/contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContactMech() throws Exception {
        // Get the contactMech
        restContactMechMockMvc.perform(get("/api/contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMech() throws Exception {
        // Initialize the database
        contactMechService.save(contactMech);

        int databaseSizeBeforeUpdate = contactMechRepository.findAll().size();

        // Update the contactMech
        ContactMech updatedContactMech = contactMechRepository.findById(contactMech.getId()).get();
        // Disconnect from session so that the updates on updatedContactMech are not directly saved in db
        em.detach(updatedContactMech);
        updatedContactMech
            .infoString(UPDATED_INFO_STRING);

        restContactMechMockMvc.perform(put("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMech)))
            .andExpect(status().isOk());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeUpdate);
        ContactMech testContactMech = contactMechList.get(contactMechList.size() - 1);
        assertThat(testContactMech.getInfoString()).isEqualTo(UPDATED_INFO_STRING);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMech() throws Exception {
        int databaseSizeBeforeUpdate = contactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechMockMvc.perform(put("/api/contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMech)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMech in the database
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMech() throws Exception {
        // Initialize the database
        contactMechService.save(contactMech);

        int databaseSizeBeforeDelete = contactMechRepository.findAll().size();

        // Delete the contactMech
        restContactMechMockMvc.perform(delete("/api/contact-meches/{id}", contactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMech> contactMechList = contactMechRepository.findAll();
        assertThat(contactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
