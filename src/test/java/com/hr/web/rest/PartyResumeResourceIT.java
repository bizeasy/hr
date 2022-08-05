package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyResume;
import com.hr.repository.PartyResumeRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartyResumeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyResumeResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RESUME_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESUME_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FILE_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    @Autowired
    private PartyResumeRepository partyResumeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyResumeMockMvc;

    private PartyResume partyResume;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyResume createEntity(EntityManager em) {
        PartyResume partyResume = new PartyResume()
            .text(DEFAULT_TEXT)
            .resumeDate(DEFAULT_RESUME_DATE)
            .fileAttachment(DEFAULT_FILE_ATTACHMENT)
            .fileAttachmentContentType(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(DEFAULT_ATTACHMENT_URL)
            .mimeType(DEFAULT_MIME_TYPE);
        return partyResume;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyResume createUpdatedEntity(EntityManager em) {
        PartyResume partyResume = new PartyResume()
            .text(UPDATED_TEXT)
            .resumeDate(UPDATED_RESUME_DATE)
            .fileAttachment(UPDATED_FILE_ATTACHMENT)
            .fileAttachmentContentType(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .mimeType(UPDATED_MIME_TYPE);
        return partyResume;
    }

    @BeforeEach
    public void initTest() {
        partyResume = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyResume() throws Exception {
        int databaseSizeBeforeCreate = partyResumeRepository.findAll().size();
        // Create the PartyResume
        restPartyResumeMockMvc.perform(post("/api/party-resumes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyResume)))
            .andExpect(status().isCreated());

        // Validate the PartyResume in the database
        List<PartyResume> partyResumeList = partyResumeRepository.findAll();
        assertThat(partyResumeList).hasSize(databaseSizeBeforeCreate + 1);
        PartyResume testPartyResume = partyResumeList.get(partyResumeList.size() - 1);
        assertThat(testPartyResume.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testPartyResume.getResumeDate()).isEqualTo(DEFAULT_RESUME_DATE);
        assertThat(testPartyResume.getFileAttachment()).isEqualTo(DEFAULT_FILE_ATTACHMENT);
        assertThat(testPartyResume.getFileAttachmentContentType()).isEqualTo(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPartyResume.getAttachmentUrl()).isEqualTo(DEFAULT_ATTACHMENT_URL);
        assertThat(testPartyResume.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
    }

    @Test
    @Transactional
    public void createPartyResumeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyResumeRepository.findAll().size();

        // Create the PartyResume with an existing ID
        partyResume.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyResumeMockMvc.perform(post("/api/party-resumes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyResume)))
            .andExpect(status().isBadRequest());

        // Validate the PartyResume in the database
        List<PartyResume> partyResumeList = partyResumeRepository.findAll();
        assertThat(partyResumeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyResumes() throws Exception {
        // Initialize the database
        partyResumeRepository.saveAndFlush(partyResume);

        // Get all the partyResumeList
        restPartyResumeMockMvc.perform(get("/api/party-resumes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyResume.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].resumeDate").value(hasItem(DEFAULT_RESUME_DATE.toString())))
            .andExpect(jsonPath("$.[*].fileAttachmentContentType").value(hasItem(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentUrl").value(hasItem(DEFAULT_ATTACHMENT_URL)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));
    }
    
    @Test
    @Transactional
    public void getPartyResume() throws Exception {
        // Initialize the database
        partyResumeRepository.saveAndFlush(partyResume);

        // Get the partyResume
        restPartyResumeMockMvc.perform(get("/api/party-resumes/{id}", partyResume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyResume.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.resumeDate").value(DEFAULT_RESUME_DATE.toString()))
            .andExpect(jsonPath("$.fileAttachmentContentType").value(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileAttachment").value(Base64Utils.encodeToString(DEFAULT_FILE_ATTACHMENT)))
            .andExpect(jsonPath("$.attachmentUrl").value(DEFAULT_ATTACHMENT_URL))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingPartyResume() throws Exception {
        // Get the partyResume
        restPartyResumeMockMvc.perform(get("/api/party-resumes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyResume() throws Exception {
        // Initialize the database
        partyResumeRepository.saveAndFlush(partyResume);

        int databaseSizeBeforeUpdate = partyResumeRepository.findAll().size();

        // Update the partyResume
        PartyResume updatedPartyResume = partyResumeRepository.findById(partyResume.getId()).get();
        // Disconnect from session so that the updates on updatedPartyResume are not directly saved in db
        em.detach(updatedPartyResume);
        updatedPartyResume
            .text(UPDATED_TEXT)
            .resumeDate(UPDATED_RESUME_DATE)
            .fileAttachment(UPDATED_FILE_ATTACHMENT)
            .fileAttachmentContentType(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .mimeType(UPDATED_MIME_TYPE);

        restPartyResumeMockMvc.perform(put("/api/party-resumes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyResume)))
            .andExpect(status().isOk());

        // Validate the PartyResume in the database
        List<PartyResume> partyResumeList = partyResumeRepository.findAll();
        assertThat(partyResumeList).hasSize(databaseSizeBeforeUpdate);
        PartyResume testPartyResume = partyResumeList.get(partyResumeList.size() - 1);
        assertThat(testPartyResume.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testPartyResume.getResumeDate()).isEqualTo(UPDATED_RESUME_DATE);
        assertThat(testPartyResume.getFileAttachment()).isEqualTo(UPDATED_FILE_ATTACHMENT);
        assertThat(testPartyResume.getFileAttachmentContentType()).isEqualTo(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testPartyResume.getAttachmentUrl()).isEqualTo(UPDATED_ATTACHMENT_URL);
        assertThat(testPartyResume.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyResume() throws Exception {
        int databaseSizeBeforeUpdate = partyResumeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyResumeMockMvc.perform(put("/api/party-resumes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyResume)))
            .andExpect(status().isBadRequest());

        // Validate the PartyResume in the database
        List<PartyResume> partyResumeList = partyResumeRepository.findAll();
        assertThat(partyResumeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyResume() throws Exception {
        // Initialize the database
        partyResumeRepository.saveAndFlush(partyResume);

        int databaseSizeBeforeDelete = partyResumeRepository.findAll().size();

        // Delete the partyResume
        restPartyResumeMockMvc.perform(delete("/api/party-resumes/{id}", partyResume.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyResume> partyResumeList = partyResumeRepository.findAll();
        assertThat(partyResumeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
