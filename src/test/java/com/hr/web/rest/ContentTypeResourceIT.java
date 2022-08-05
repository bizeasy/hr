package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ContentType;
import com.hr.repository.ContentTypeRepository;

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
 * Integration tests for the {@link ContentTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ContentTypeRepository contentTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentTypeMockMvc;

    private ContentType contentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentType createEntity(EntityManager em) {
        ContentType contentType = new ContentType()
            .name(DEFAULT_NAME);
        return contentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentType createUpdatedEntity(EntityManager em) {
        ContentType contentType = new ContentType()
            .name(UPDATED_NAME);
        return contentType;
    }

    @BeforeEach
    public void initTest() {
        contentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentType() throws Exception {
        int databaseSizeBeforeCreate = contentTypeRepository.findAll().size();
        // Create the ContentType
        restContentTypeMockMvc.perform(post("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isCreated());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ContentType testContentType = contentTypeList.get(contentTypeList.size() - 1);
        assertThat(testContentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createContentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentTypeRepository.findAll().size();

        // Create the ContentType with an existing ID
        contentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentTypeMockMvc.perform(post("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isBadRequest());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContentTypes() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        // Get all the contentTypeList
        restContentTypeMockMvc.perform(get("/api/content-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getContentType() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        // Get the contentType
        restContentTypeMockMvc.perform(get("/api/content-types/{id}", contentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingContentType() throws Exception {
        // Get the contentType
        restContentTypeMockMvc.perform(get("/api/content-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentType() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        int databaseSizeBeforeUpdate = contentTypeRepository.findAll().size();

        // Update the contentType
        ContentType updatedContentType = contentTypeRepository.findById(contentType.getId()).get();
        // Disconnect from session so that the updates on updatedContentType are not directly saved in db
        em.detach(updatedContentType);
        updatedContentType
            .name(UPDATED_NAME);

        restContentTypeMockMvc.perform(put("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentType)))
            .andExpect(status().isOk());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeUpdate);
        ContentType testContentType = contentTypeList.get(contentTypeList.size() - 1);
        assertThat(testContentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContentType() throws Exception {
        int databaseSizeBeforeUpdate = contentTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentTypeMockMvc.perform(put("/api/content-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentType)))
            .andExpect(status().isBadRequest());

        // Validate the ContentType in the database
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentType() throws Exception {
        // Initialize the database
        contentTypeRepository.saveAndFlush(contentType);

        int databaseSizeBeforeDelete = contentTypeRepository.findAll().size();

        // Delete the contentType
        restContentTypeMockMvc.perform(delete("/api/content-types/{id}", contentType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentType> contentTypeList = contentTypeRepository.findAll();
        assertThat(contentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
