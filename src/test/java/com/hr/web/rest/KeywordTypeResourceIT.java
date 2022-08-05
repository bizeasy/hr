package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.KeywordType;
import com.hr.repository.KeywordTypeRepository;

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
 * Integration tests for the {@link KeywordTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KeywordTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private KeywordTypeRepository keywordTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKeywordTypeMockMvc;

    private KeywordType keywordType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeywordType createEntity(EntityManager em) {
        KeywordType keywordType = new KeywordType()
            .name(DEFAULT_NAME);
        return keywordType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KeywordType createUpdatedEntity(EntityManager em) {
        KeywordType keywordType = new KeywordType()
            .name(UPDATED_NAME);
        return keywordType;
    }

    @BeforeEach
    public void initTest() {
        keywordType = createEntity(em);
    }

    @Test
    @Transactional
    public void createKeywordType() throws Exception {
        int databaseSizeBeforeCreate = keywordTypeRepository.findAll().size();
        // Create the KeywordType
        restKeywordTypeMockMvc.perform(post("/api/keyword-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keywordType)))
            .andExpect(status().isCreated());

        // Validate the KeywordType in the database
        List<KeywordType> keywordTypeList = keywordTypeRepository.findAll();
        assertThat(keywordTypeList).hasSize(databaseSizeBeforeCreate + 1);
        KeywordType testKeywordType = keywordTypeList.get(keywordTypeList.size() - 1);
        assertThat(testKeywordType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createKeywordTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = keywordTypeRepository.findAll().size();

        // Create the KeywordType with an existing ID
        keywordType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKeywordTypeMockMvc.perform(post("/api/keyword-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keywordType)))
            .andExpect(status().isBadRequest());

        // Validate the KeywordType in the database
        List<KeywordType> keywordTypeList = keywordTypeRepository.findAll();
        assertThat(keywordTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllKeywordTypes() throws Exception {
        // Initialize the database
        keywordTypeRepository.saveAndFlush(keywordType);

        // Get all the keywordTypeList
        restKeywordTypeMockMvc.perform(get("/api/keyword-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(keywordType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getKeywordType() throws Exception {
        // Initialize the database
        keywordTypeRepository.saveAndFlush(keywordType);

        // Get the keywordType
        restKeywordTypeMockMvc.perform(get("/api/keyword-types/{id}", keywordType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(keywordType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingKeywordType() throws Exception {
        // Get the keywordType
        restKeywordTypeMockMvc.perform(get("/api/keyword-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKeywordType() throws Exception {
        // Initialize the database
        keywordTypeRepository.saveAndFlush(keywordType);

        int databaseSizeBeforeUpdate = keywordTypeRepository.findAll().size();

        // Update the keywordType
        KeywordType updatedKeywordType = keywordTypeRepository.findById(keywordType.getId()).get();
        // Disconnect from session so that the updates on updatedKeywordType are not directly saved in db
        em.detach(updatedKeywordType);
        updatedKeywordType
            .name(UPDATED_NAME);

        restKeywordTypeMockMvc.perform(put("/api/keyword-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKeywordType)))
            .andExpect(status().isOk());

        // Validate the KeywordType in the database
        List<KeywordType> keywordTypeList = keywordTypeRepository.findAll();
        assertThat(keywordTypeList).hasSize(databaseSizeBeforeUpdate);
        KeywordType testKeywordType = keywordTypeList.get(keywordTypeList.size() - 1);
        assertThat(testKeywordType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingKeywordType() throws Exception {
        int databaseSizeBeforeUpdate = keywordTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKeywordTypeMockMvc.perform(put("/api/keyword-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(keywordType)))
            .andExpect(status().isBadRequest());

        // Validate the KeywordType in the database
        List<KeywordType> keywordTypeList = keywordTypeRepository.findAll();
        assertThat(keywordTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKeywordType() throws Exception {
        // Initialize the database
        keywordTypeRepository.saveAndFlush(keywordType);

        int databaseSizeBeforeDelete = keywordTypeRepository.findAll().size();

        // Delete the keywordType
        restKeywordTypeMockMvc.perform(delete("/api/keyword-types/{id}", keywordType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KeywordType> keywordTypeList = keywordTypeRepository.findAll();
        assertThat(keywordTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
