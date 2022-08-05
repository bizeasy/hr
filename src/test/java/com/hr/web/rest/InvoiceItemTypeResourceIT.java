package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InvoiceItemType;
import com.hr.repository.InvoiceItemTypeRepository;

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
 * Integration tests for the {@link InvoiceItemTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceItemTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InvoiceItemTypeRepository invoiceItemTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceItemTypeMockMvc;

    private InvoiceItemType invoiceItemType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItemType createEntity(EntityManager em) {
        InvoiceItemType invoiceItemType = new InvoiceItemType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return invoiceItemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItemType createUpdatedEntity(EntityManager em) {
        InvoiceItemType invoiceItemType = new InvoiceItemType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return invoiceItemType;
    }

    @BeforeEach
    public void initTest() {
        invoiceItemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceItemType() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemTypeRepository.findAll().size();
        // Create the InvoiceItemType
        restInvoiceItemTypeMockMvc.perform(post("/api/invoice-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemType)))
            .andExpect(status().isCreated());

        // Validate the InvoiceItemType in the database
        List<InvoiceItemType> invoiceItemTypeList = invoiceItemTypeRepository.findAll();
        assertThat(invoiceItemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceItemType testInvoiceItemType = invoiceItemTypeList.get(invoiceItemTypeList.size() - 1);
        assertThat(testInvoiceItemType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvoiceItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInvoiceItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemTypeRepository.findAll().size();

        // Create the InvoiceItemType with an existing ID
        invoiceItemType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceItemTypeMockMvc.perform(post("/api/invoice-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemType)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItemType in the database
        List<InvoiceItemType> invoiceItemTypeList = invoiceItemTypeRepository.findAll();
        assertThat(invoiceItemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoiceItemTypes() throws Exception {
        // Initialize the database
        invoiceItemTypeRepository.saveAndFlush(invoiceItemType);

        // Get all the invoiceItemTypeList
        restInvoiceItemTypeMockMvc.perform(get("/api/invoice-item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInvoiceItemType() throws Exception {
        // Initialize the database
        invoiceItemTypeRepository.saveAndFlush(invoiceItemType);

        // Get the invoiceItemType
        restInvoiceItemTypeMockMvc.perform(get("/api/invoice-item-types/{id}", invoiceItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceItemType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingInvoiceItemType() throws Exception {
        // Get the invoiceItemType
        restInvoiceItemTypeMockMvc.perform(get("/api/invoice-item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceItemType() throws Exception {
        // Initialize the database
        invoiceItemTypeRepository.saveAndFlush(invoiceItemType);

        int databaseSizeBeforeUpdate = invoiceItemTypeRepository.findAll().size();

        // Update the invoiceItemType
        InvoiceItemType updatedInvoiceItemType = invoiceItemTypeRepository.findById(invoiceItemType.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceItemType are not directly saved in db
        em.detach(updatedInvoiceItemType);
        updatedInvoiceItemType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restInvoiceItemTypeMockMvc.perform(put("/api/invoice-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceItemType)))
            .andExpect(status().isOk());

        // Validate the InvoiceItemType in the database
        List<InvoiceItemType> invoiceItemTypeList = invoiceItemTypeRepository.findAll();
        assertThat(invoiceItemTypeList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItemType testInvoiceItemType = invoiceItemTypeList.get(invoiceItemTypeList.size() - 1);
        assertThat(testInvoiceItemType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvoiceItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceItemType() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceItemTypeMockMvc.perform(put("/api/invoice-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItemType)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItemType in the database
        List<InvoiceItemType> invoiceItemTypeList = invoiceItemTypeRepository.findAll();
        assertThat(invoiceItemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceItemType() throws Exception {
        // Initialize the database
        invoiceItemTypeRepository.saveAndFlush(invoiceItemType);

        int databaseSizeBeforeDelete = invoiceItemTypeRepository.findAll().size();

        // Delete the invoiceItemType
        restInvoiceItemTypeMockMvc.perform(delete("/api/invoice-item-types/{id}", invoiceItemType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceItemType> invoiceItemTypeList = invoiceItemTypeRepository.findAll();
        assertThat(invoiceItemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
