package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ContactMechPurpose;
import com.hr.repository.ContactMechPurposeRepository;

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
 * Integration tests for the {@link ContactMechPurposeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactMechPurposeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ContactMechPurposeRepository contactMechPurposeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMechPurposeMockMvc;

    private ContactMechPurpose contactMechPurpose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechPurpose createEntity(EntityManager em) {
        ContactMechPurpose contactMechPurpose = new ContactMechPurpose()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return contactMechPurpose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactMechPurpose createUpdatedEntity(EntityManager em) {
        ContactMechPurpose contactMechPurpose = new ContactMechPurpose()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return contactMechPurpose;
    }

    @BeforeEach
    public void initTest() {
        contactMechPurpose = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactMechPurpose() throws Exception {
        int databaseSizeBeforeCreate = contactMechPurposeRepository.findAll().size();
        // Create the ContactMechPurpose
        restContactMechPurposeMockMvc.perform(post("/api/contact-mech-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPurpose)))
            .andExpect(status().isCreated());

        // Validate the ContactMechPurpose in the database
        List<ContactMechPurpose> contactMechPurposeList = contactMechPurposeRepository.findAll();
        assertThat(contactMechPurposeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactMechPurpose testContactMechPurpose = contactMechPurposeList.get(contactMechPurposeList.size() - 1);
        assertThat(testContactMechPurpose.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContactMechPurpose.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createContactMechPurposeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactMechPurposeRepository.findAll().size();

        // Create the ContactMechPurpose with an existing ID
        contactMechPurpose.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMechPurposeMockMvc.perform(post("/api/contact-mech-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechPurpose in the database
        List<ContactMechPurpose> contactMechPurposeList = contactMechPurposeRepository.findAll();
        assertThat(contactMechPurposeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContactMechPurposes() throws Exception {
        // Initialize the database
        contactMechPurposeRepository.saveAndFlush(contactMechPurpose);

        // Get all the contactMechPurposeList
        restContactMechPurposeMockMvc.perform(get("/api/contact-mech-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactMechPurpose.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getContactMechPurpose() throws Exception {
        // Initialize the database
        contactMechPurposeRepository.saveAndFlush(contactMechPurpose);

        // Get the contactMechPurpose
        restContactMechPurposeMockMvc.perform(get("/api/contact-mech-purposes/{id}", contactMechPurpose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactMechPurpose.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingContactMechPurpose() throws Exception {
        // Get the contactMechPurpose
        restContactMechPurposeMockMvc.perform(get("/api/contact-mech-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactMechPurpose() throws Exception {
        // Initialize the database
        contactMechPurposeRepository.saveAndFlush(contactMechPurpose);

        int databaseSizeBeforeUpdate = contactMechPurposeRepository.findAll().size();

        // Update the contactMechPurpose
        ContactMechPurpose updatedContactMechPurpose = contactMechPurposeRepository.findById(contactMechPurpose.getId()).get();
        // Disconnect from session so that the updates on updatedContactMechPurpose are not directly saved in db
        em.detach(updatedContactMechPurpose);
        updatedContactMechPurpose
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restContactMechPurposeMockMvc.perform(put("/api/contact-mech-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactMechPurpose)))
            .andExpect(status().isOk());

        // Validate the ContactMechPurpose in the database
        List<ContactMechPurpose> contactMechPurposeList = contactMechPurposeRepository.findAll();
        assertThat(contactMechPurposeList).hasSize(databaseSizeBeforeUpdate);
        ContactMechPurpose testContactMechPurpose = contactMechPurposeList.get(contactMechPurposeList.size() - 1);
        assertThat(testContactMechPurpose.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContactMechPurpose.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingContactMechPurpose() throws Exception {
        int databaseSizeBeforeUpdate = contactMechPurposeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMechPurposeMockMvc.perform(put("/api/contact-mech-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactMechPurpose)))
            .andExpect(status().isBadRequest());

        // Validate the ContactMechPurpose in the database
        List<ContactMechPurpose> contactMechPurposeList = contactMechPurposeRepository.findAll();
        assertThat(contactMechPurposeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactMechPurpose() throws Exception {
        // Initialize the database
        contactMechPurposeRepository.saveAndFlush(contactMechPurpose);

        int databaseSizeBeforeDelete = contactMechPurposeRepository.findAll().size();

        // Delete the contactMechPurpose
        restContactMechPurposeMockMvc.perform(delete("/api/contact-mech-purposes/{id}", contactMechPurpose.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactMechPurpose> contactMechPurposeList = contactMechPurposeRepository.findAll();
        assertThat(contactMechPurposeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
