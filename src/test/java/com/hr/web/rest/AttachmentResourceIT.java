package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Attachment;
import com.hr.repository.AttachmentRepository;
import com.hr.service.AttachmentService;
import com.hr.service.dto.AttachmentCriteria;
import com.hr.service.AttachmentQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AttachmentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttachmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_URL = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentQueryService attachmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttachmentMockMvc;

    private Attachment attachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createEntity(EntityManager em) {
        Attachment attachment = new Attachment()
            .name(DEFAULT_NAME)
            .fileAttachment(DEFAULT_FILE_ATTACHMENT)
            .fileAttachmentContentType(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(DEFAULT_ATTACHMENT_URL)
            .mimeType(DEFAULT_MIME_TYPE);
        return attachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attachment createUpdatedEntity(EntityManager em) {
        Attachment attachment = new Attachment()
            .name(UPDATED_NAME)
            .fileAttachment(UPDATED_FILE_ATTACHMENT)
            .fileAttachmentContentType(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .mimeType(UPDATED_MIME_TYPE);
        return attachment;
    }

    @BeforeEach
    public void initTest() {
        attachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttachment() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();
        // Create the Attachment
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachment)))
            .andExpect(status().isCreated());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate + 1);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAttachment.getFileAttachment()).isEqualTo(DEFAULT_FILE_ATTACHMENT);
        assertThat(testAttachment.getFileAttachmentContentType()).isEqualTo(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAttachment.getAttachmentUrl()).isEqualTo(DEFAULT_ATTACHMENT_URL);
        assertThat(testAttachment.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
    }

    @Test
    @Transactional
    public void createAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attachmentRepository.findAll().size();

        // Create the Attachment with an existing ID
        attachment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttachmentMockMvc.perform(post("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachment)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttachments() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileAttachmentContentType").value(hasItem(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentUrl").value(hasItem(DEFAULT_ATTACHMENT_URL)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));
    }
    
    @Test
    @Transactional
    public void getAttachment() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", attachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attachment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fileAttachmentContentType").value(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.fileAttachment").value(Base64Utils.encodeToString(DEFAULT_FILE_ATTACHMENT)))
            .andExpect(jsonPath("$.attachmentUrl").value(DEFAULT_ATTACHMENT_URL))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE));
    }


    @Test
    @Transactional
    public void getAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        Long id = attachment.getId();

        defaultAttachmentShouldBeFound("id.equals=" + id);
        defaultAttachmentShouldNotBeFound("id.notEquals=" + id);

        defaultAttachmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttachmentShouldNotBeFound("id.greaterThan=" + id);

        defaultAttachmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttachmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name equals to DEFAULT_NAME
        defaultAttachmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the attachmentList where name equals to UPDATED_NAME
        defaultAttachmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name not equals to DEFAULT_NAME
        defaultAttachmentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the attachmentList where name not equals to UPDATED_NAME
        defaultAttachmentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAttachmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the attachmentList where name equals to UPDATED_NAME
        defaultAttachmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name is not null
        defaultAttachmentShouldBeFound("name.specified=true");

        // Get all the attachmentList where name is null
        defaultAttachmentShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttachmentsByNameContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name contains DEFAULT_NAME
        defaultAttachmentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the attachmentList where name contains UPDATED_NAME
        defaultAttachmentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where name does not contain DEFAULT_NAME
        defaultAttachmentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the attachmentList where name does not contain UPDATED_NAME
        defaultAttachmentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl equals to DEFAULT_ATTACHMENT_URL
        defaultAttachmentShouldBeFound("attachmentUrl.equals=" + DEFAULT_ATTACHMENT_URL);

        // Get all the attachmentList where attachmentUrl equals to UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldNotBeFound("attachmentUrl.equals=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl not equals to DEFAULT_ATTACHMENT_URL
        defaultAttachmentShouldNotBeFound("attachmentUrl.notEquals=" + DEFAULT_ATTACHMENT_URL);

        // Get all the attachmentList where attachmentUrl not equals to UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldBeFound("attachmentUrl.notEquals=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl in DEFAULT_ATTACHMENT_URL or UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldBeFound("attachmentUrl.in=" + DEFAULT_ATTACHMENT_URL + "," + UPDATED_ATTACHMENT_URL);

        // Get all the attachmentList where attachmentUrl equals to UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldNotBeFound("attachmentUrl.in=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl is not null
        defaultAttachmentShouldBeFound("attachmentUrl.specified=true");

        // Get all the attachmentList where attachmentUrl is null
        defaultAttachmentShouldNotBeFound("attachmentUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl contains DEFAULT_ATTACHMENT_URL
        defaultAttachmentShouldBeFound("attachmentUrl.contains=" + DEFAULT_ATTACHMENT_URL);

        // Get all the attachmentList where attachmentUrl contains UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldNotBeFound("attachmentUrl.contains=" + UPDATED_ATTACHMENT_URL);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByAttachmentUrlNotContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where attachmentUrl does not contain DEFAULT_ATTACHMENT_URL
        defaultAttachmentShouldNotBeFound("attachmentUrl.doesNotContain=" + DEFAULT_ATTACHMENT_URL);

        // Get all the attachmentList where attachmentUrl does not contain UPDATED_ATTACHMENT_URL
        defaultAttachmentShouldBeFound("attachmentUrl.doesNotContain=" + UPDATED_ATTACHMENT_URL);
    }


    @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType equals to DEFAULT_MIME_TYPE
        defaultAttachmentShouldBeFound("mimeType.equals=" + DEFAULT_MIME_TYPE);

        // Get all the attachmentList where mimeType equals to UPDATED_MIME_TYPE
        defaultAttachmentShouldNotBeFound("mimeType.equals=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType not equals to DEFAULT_MIME_TYPE
        defaultAttachmentShouldNotBeFound("mimeType.notEquals=" + DEFAULT_MIME_TYPE);

        // Get all the attachmentList where mimeType not equals to UPDATED_MIME_TYPE
        defaultAttachmentShouldBeFound("mimeType.notEquals=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType in DEFAULT_MIME_TYPE or UPDATED_MIME_TYPE
        defaultAttachmentShouldBeFound("mimeType.in=" + DEFAULT_MIME_TYPE + "," + UPDATED_MIME_TYPE);

        // Get all the attachmentList where mimeType equals to UPDATED_MIME_TYPE
        defaultAttachmentShouldNotBeFound("mimeType.in=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType is not null
        defaultAttachmentShouldBeFound("mimeType.specified=true");

        // Get all the attachmentList where mimeType is null
        defaultAttachmentShouldNotBeFound("mimeType.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType contains DEFAULT_MIME_TYPE
        defaultAttachmentShouldBeFound("mimeType.contains=" + DEFAULT_MIME_TYPE);

        // Get all the attachmentList where mimeType contains UPDATED_MIME_TYPE
        defaultAttachmentShouldNotBeFound("mimeType.contains=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttachmentsByMimeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        attachmentRepository.saveAndFlush(attachment);

        // Get all the attachmentList where mimeType does not contain DEFAULT_MIME_TYPE
        defaultAttachmentShouldNotBeFound("mimeType.doesNotContain=" + DEFAULT_MIME_TYPE);

        // Get all the attachmentList where mimeType does not contain UPDATED_MIME_TYPE
        defaultAttachmentShouldBeFound("mimeType.doesNotContain=" + UPDATED_MIME_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttachmentShouldBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fileAttachmentContentType").value(hasItem(DEFAULT_FILE_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fileAttachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentUrl").value(hasItem(DEFAULT_ATTACHMENT_URL)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)));

        // Check, that the count call also returns 1
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttachmentShouldNotBeFound(String filter) throws Exception {
        restAttachmentMockMvc.perform(get("/api/attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttachmentMockMvc.perform(get("/api/attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttachment() throws Exception {
        // Get the attachment
        restAttachmentMockMvc.perform(get("/api/attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttachment() throws Exception {
        // Initialize the database
        attachmentService.save(attachment);

        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // Update the attachment
        Attachment updatedAttachment = attachmentRepository.findById(attachment.getId()).get();
        // Disconnect from session so that the updates on updatedAttachment are not directly saved in db
        em.detach(updatedAttachment);
        updatedAttachment
            .name(UPDATED_NAME)
            .fileAttachment(UPDATED_FILE_ATTACHMENT)
            .fileAttachmentContentType(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE)
            .attachmentUrl(UPDATED_ATTACHMENT_URL)
            .mimeType(UPDATED_MIME_TYPE);

        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttachment)))
            .andExpect(status().isOk());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);
        Attachment testAttachment = attachmentList.get(attachmentList.size() - 1);
        assertThat(testAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAttachment.getFileAttachment()).isEqualTo(UPDATED_FILE_ATTACHMENT);
        assertThat(testAttachment.getFileAttachmentContentType()).isEqualTo(UPDATED_FILE_ATTACHMENT_CONTENT_TYPE);
        assertThat(testAttachment.getAttachmentUrl()).isEqualTo(UPDATED_ATTACHMENT_URL);
        assertThat(testAttachment.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttachment() throws Exception {
        int databaseSizeBeforeUpdate = attachmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttachmentMockMvc.perform(put("/api/attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attachment)))
            .andExpect(status().isBadRequest());

        // Validate the Attachment in the database
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttachment() throws Exception {
        // Initialize the database
        attachmentService.save(attachment);

        int databaseSizeBeforeDelete = attachmentRepository.findAll().size();

        // Delete the attachment
        restAttachmentMockMvc.perform(delete("/api/attachments/{id}", attachment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attachment> attachmentList = attachmentRepository.findAll();
        assertThat(attachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
