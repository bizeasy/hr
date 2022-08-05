package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderTerm;
import com.hr.repository.OrderTermRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderTermResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderTermResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TERM_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TERM_VALUE = new BigDecimal(2);

    private static final Integer DEFAULT_TERM_DAYS = 1;
    private static final Integer UPDATED_TERM_DAYS = 2;

    private static final String DEFAULT_TEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_VALUE = "BBBBBBBBBB";

    @Autowired
    private OrderTermRepository orderTermRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTermMockMvc;

    private OrderTerm orderTerm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTerm createEntity(EntityManager em) {
        OrderTerm orderTerm = new OrderTerm()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .termValue(DEFAULT_TERM_VALUE)
            .termDays(DEFAULT_TERM_DAYS)
            .textValue(DEFAULT_TEXT_VALUE);
        return orderTerm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTerm createUpdatedEntity(EntityManager em) {
        OrderTerm orderTerm = new OrderTerm()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .termValue(UPDATED_TERM_VALUE)
            .termDays(UPDATED_TERM_DAYS)
            .textValue(UPDATED_TEXT_VALUE);
        return orderTerm;
    }

    @BeforeEach
    public void initTest() {
        orderTerm = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTerm() throws Exception {
        int databaseSizeBeforeCreate = orderTermRepository.findAll().size();
        // Create the OrderTerm
        restOrderTermMockMvc.perform(post("/api/order-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTerm)))
            .andExpect(status().isCreated());

        // Validate the OrderTerm in the database
        List<OrderTerm> orderTermList = orderTermRepository.findAll();
        assertThat(orderTermList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTerm testOrderTerm = orderTermList.get(orderTermList.size() - 1);
        assertThat(testOrderTerm.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testOrderTerm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderTerm.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testOrderTerm.getTermValue()).isEqualTo(DEFAULT_TERM_VALUE);
        assertThat(testOrderTerm.getTermDays()).isEqualTo(DEFAULT_TERM_DAYS);
        assertThat(testOrderTerm.getTextValue()).isEqualTo(DEFAULT_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void createOrderTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTermRepository.findAll().size();

        // Create the OrderTerm with an existing ID
        orderTerm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTermMockMvc.perform(post("/api/order-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTerm)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTerm in the database
        List<OrderTerm> orderTermList = orderTermRepository.findAll();
        assertThat(orderTermList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderTerms() throws Exception {
        // Initialize the database
        orderTermRepository.saveAndFlush(orderTerm);

        // Get all the orderTermList
        restOrderTermMockMvc.perform(get("/api/order-terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].termValue").value(hasItem(DEFAULT_TERM_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].termDays").value(hasItem(DEFAULT_TERM_DAYS)))
            .andExpect(jsonPath("$.[*].textValue").value(hasItem(DEFAULT_TEXT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getOrderTerm() throws Exception {
        // Initialize the database
        orderTermRepository.saveAndFlush(orderTerm);

        // Get the orderTerm
        restOrderTermMockMvc.perform(get("/api/order-terms/{id}", orderTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTerm.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.termValue").value(DEFAULT_TERM_VALUE.intValue()))
            .andExpect(jsonPath("$.termDays").value(DEFAULT_TERM_DAYS))
            .andExpect(jsonPath("$.textValue").value(DEFAULT_TEXT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingOrderTerm() throws Exception {
        // Get the orderTerm
        restOrderTermMockMvc.perform(get("/api/order-terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTerm() throws Exception {
        // Initialize the database
        orderTermRepository.saveAndFlush(orderTerm);

        int databaseSizeBeforeUpdate = orderTermRepository.findAll().size();

        // Update the orderTerm
        OrderTerm updatedOrderTerm = orderTermRepository.findById(orderTerm.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTerm are not directly saved in db
        em.detach(updatedOrderTerm);
        updatedOrderTerm
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .termValue(UPDATED_TERM_VALUE)
            .termDays(UPDATED_TERM_DAYS)
            .textValue(UPDATED_TEXT_VALUE);

        restOrderTermMockMvc.perform(put("/api/order-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTerm)))
            .andExpect(status().isOk());

        // Validate the OrderTerm in the database
        List<OrderTerm> orderTermList = orderTermRepository.findAll();
        assertThat(orderTermList).hasSize(databaseSizeBeforeUpdate);
        OrderTerm testOrderTerm = orderTermList.get(orderTermList.size() - 1);
        assertThat(testOrderTerm.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testOrderTerm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderTerm.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testOrderTerm.getTermValue()).isEqualTo(UPDATED_TERM_VALUE);
        assertThat(testOrderTerm.getTermDays()).isEqualTo(UPDATED_TERM_DAYS);
        assertThat(testOrderTerm.getTextValue()).isEqualTo(UPDATED_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTerm() throws Exception {
        int databaseSizeBeforeUpdate = orderTermRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTermMockMvc.perform(put("/api/order-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTerm)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTerm in the database
        List<OrderTerm> orderTermList = orderTermRepository.findAll();
        assertThat(orderTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTerm() throws Exception {
        // Initialize the database
        orderTermRepository.saveAndFlush(orderTerm);

        int databaseSizeBeforeDelete = orderTermRepository.findAll().size();

        // Delete the orderTerm
        restOrderTermMockMvc.perform(delete("/api/order-terms/{id}", orderTerm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTerm> orderTermList = orderTermRepository.findAll();
        assertThat(orderTermList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
