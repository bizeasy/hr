package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.UomType;
import com.hr.repository.UomTypeRepository;

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
 * Integration tests for the {@link UomTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UomTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UomTypeRepository uomTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUomTypeMockMvc;

    private UomType uomType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UomType createEntity(EntityManager em) {
        UomType uomType = new UomType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return uomType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UomType createUpdatedEntity(EntityManager em) {
        UomType uomType = new UomType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return uomType;
    }

    @BeforeEach
    public void initTest() {
        uomType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUomType() throws Exception {
        int databaseSizeBeforeCreate = uomTypeRepository.findAll().size();
        // Create the UomType
        restUomTypeMockMvc.perform(post("/api/uom-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uomType)))
            .andExpect(status().isCreated());

        // Validate the UomType in the database
        List<UomType> uomTypeList = uomTypeRepository.findAll();
        assertThat(uomTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UomType testUomType = uomTypeList.get(uomTypeList.size() - 1);
        assertThat(testUomType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUomType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createUomTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uomTypeRepository.findAll().size();

        // Create the UomType with an existing ID
        uomType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUomTypeMockMvc.perform(post("/api/uom-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uomType)))
            .andExpect(status().isBadRequest());

        // Validate the UomType in the database
        List<UomType> uomTypeList = uomTypeRepository.findAll();
        assertThat(uomTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUomTypes() throws Exception {
        // Initialize the database
        uomTypeRepository.saveAndFlush(uomType);

        // Get all the uomTypeList
        restUomTypeMockMvc.perform(get("/api/uom-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uomType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getUomType() throws Exception {
        // Initialize the database
        uomTypeRepository.saveAndFlush(uomType);

        // Get the uomType
        restUomTypeMockMvc.perform(get("/api/uom-types/{id}", uomType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uomType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingUomType() throws Exception {
        // Get the uomType
        restUomTypeMockMvc.perform(get("/api/uom-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUomType() throws Exception {
        // Initialize the database
        uomTypeRepository.saveAndFlush(uomType);

        int databaseSizeBeforeUpdate = uomTypeRepository.findAll().size();

        // Update the uomType
        UomType updatedUomType = uomTypeRepository.findById(uomType.getId()).get();
        // Disconnect from session so that the updates on updatedUomType are not directly saved in db
        em.detach(updatedUomType);
        updatedUomType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restUomTypeMockMvc.perform(put("/api/uom-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUomType)))
            .andExpect(status().isOk());

        // Validate the UomType in the database
        List<UomType> uomTypeList = uomTypeRepository.findAll();
        assertThat(uomTypeList).hasSize(databaseSizeBeforeUpdate);
        UomType testUomType = uomTypeList.get(uomTypeList.size() - 1);
        assertThat(testUomType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUomType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUomType() throws Exception {
        int databaseSizeBeforeUpdate = uomTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUomTypeMockMvc.perform(put("/api/uom-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uomType)))
            .andExpect(status().isBadRequest());

        // Validate the UomType in the database
        List<UomType> uomTypeList = uomTypeRepository.findAll();
        assertThat(uomTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUomType() throws Exception {
        // Initialize the database
        uomTypeRepository.saveAndFlush(uomType);

        int databaseSizeBeforeDelete = uomTypeRepository.findAll().size();

        // Delete the uomType
        restUomTypeMockMvc.perform(delete("/api/uom-types/{id}", uomType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UomType> uomTypeList = uomTypeRepository.findAll();
        assertThat(uomTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
