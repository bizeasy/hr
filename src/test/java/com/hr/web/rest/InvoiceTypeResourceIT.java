package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InvoiceType;
import com.hr.repository.InvoiceTypeRepository;

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
 * Integration tests for the {@link InvoiceTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InvoiceTypeRepository invoiceTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceTypeMockMvc;

    private InvoiceType invoiceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceType createEntity(EntityManager em) {
        InvoiceType invoiceType = new InvoiceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return invoiceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceType createUpdatedEntity(EntityManager em) {
        InvoiceType invoiceType = new InvoiceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return invoiceType;
    }

    @BeforeEach
    public void initTest() {
        invoiceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceType() throws Exception {
        int databaseSizeBeforeCreate = invoiceTypeRepository.findAll().size();
        // Create the InvoiceType
        restInvoiceTypeMockMvc.perform(post("/api/invoice-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceType)))
            .andExpect(status().isCreated());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypeList = invoiceTypeRepository.findAll();
        assertThat(invoiceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceType testInvoiceType = invoiceTypeList.get(invoiceTypeList.size() - 1);
        assertThat(testInvoiceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvoiceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInvoiceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceTypeRepository.findAll().size();

        // Create the InvoiceType with an existing ID
        invoiceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceTypeMockMvc.perform(post("/api/invoice-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceType)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypeList = invoiceTypeRepository.findAll();
        assertThat(invoiceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoiceTypes() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Get all the invoiceTypeList
        restInvoiceTypeMockMvc.perform(get("/api/invoice-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        // Get the invoiceType
        restInvoiceTypeMockMvc.perform(get("/api/invoice-types/{id}", invoiceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingInvoiceType() throws Exception {
        // Get the invoiceType
        restInvoiceTypeMockMvc.perform(get("/api/invoice-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        int databaseSizeBeforeUpdate = invoiceTypeRepository.findAll().size();

        // Update the invoiceType
        InvoiceType updatedInvoiceType = invoiceTypeRepository.findById(invoiceType.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceType are not directly saved in db
        em.detach(updatedInvoiceType);
        updatedInvoiceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restInvoiceTypeMockMvc.perform(put("/api/invoice-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceType)))
            .andExpect(status().isOk());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypeList = invoiceTypeRepository.findAll();
        assertThat(invoiceTypeList).hasSize(databaseSizeBeforeUpdate);
        InvoiceType testInvoiceType = invoiceTypeList.get(invoiceTypeList.size() - 1);
        assertThat(testInvoiceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvoiceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceType() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceTypeMockMvc.perform(put("/api/invoice-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceType)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceType in the database
        List<InvoiceType> invoiceTypeList = invoiceTypeRepository.findAll();
        assertThat(invoiceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceType() throws Exception {
        // Initialize the database
        invoiceTypeRepository.saveAndFlush(invoiceType);

        int databaseSizeBeforeDelete = invoiceTypeRepository.findAll().size();

        // Delete the invoiceType
        restInvoiceTypeMockMvc.perform(delete("/api/invoice-types/{id}", invoiceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceType> invoiceTypeList = invoiceTypeRepository.findAll();
        assertThat(invoiceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
