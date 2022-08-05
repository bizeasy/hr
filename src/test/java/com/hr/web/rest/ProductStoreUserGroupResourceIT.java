package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductStoreUserGroup;
import com.hr.domain.UserGroup;
import com.hr.domain.User;
import com.hr.domain.Party;
import com.hr.domain.ProductStore;
import com.hr.repository.ProductStoreUserGroupRepository;
import com.hr.service.ProductStoreUserGroupService;
import com.hr.service.dto.ProductStoreUserGroupCriteria;
import com.hr.service.ProductStoreUserGroupQueryService;

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
 * Integration tests for the {@link ProductStoreUserGroupResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductStoreUserGroupResourceIT {

    @Autowired
    private ProductStoreUserGroupRepository productStoreUserGroupRepository;

    @Autowired
    private ProductStoreUserGroupService productStoreUserGroupService;

    @Autowired
    private ProductStoreUserGroupQueryService productStoreUserGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreUserGroupMockMvc;

    private ProductStoreUserGroup productStoreUserGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreUserGroup createEntity(EntityManager em) {
        ProductStoreUserGroup productStoreUserGroup = new ProductStoreUserGroup();
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreUserGroup.setProductStore(productStore);
        return productStoreUserGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreUserGroup createUpdatedEntity(EntityManager em) {
        ProductStoreUserGroup productStoreUserGroup = new ProductStoreUserGroup();
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createUpdatedEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreUserGroup.setProductStore(productStore);
        return productStoreUserGroup;
    }

    @BeforeEach
    public void initTest() {
        productStoreUserGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStoreUserGroup() throws Exception {
        int databaseSizeBeforeCreate = productStoreUserGroupRepository.findAll().size();
        // Create the ProductStoreUserGroup
        restProductStoreUserGroupMockMvc.perform(post("/api/product-store-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreUserGroup)))
            .andExpect(status().isCreated());

        // Validate the ProductStoreUserGroup in the database
        List<ProductStoreUserGroup> productStoreUserGroupList = productStoreUserGroupRepository.findAll();
        assertThat(productStoreUserGroupList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStoreUserGroup testProductStoreUserGroup = productStoreUserGroupList.get(productStoreUserGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createProductStoreUserGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStoreUserGroupRepository.findAll().size();

        // Create the ProductStoreUserGroup with an existing ID
        productStoreUserGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreUserGroupMockMvc.perform(post("/api/product-store-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreUserGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreUserGroup in the database
        List<ProductStoreUserGroup> productStoreUserGroupList = productStoreUserGroupRepository.findAll();
        assertThat(productStoreUserGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductStoreUserGroups() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);

        // Get all the productStoreUserGroupList
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreUserGroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProductStoreUserGroup() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);

        // Get the productStoreUserGroup
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups/{id}", productStoreUserGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStoreUserGroup.getId().intValue()));
    }


    @Test
    @Transactional
    public void getProductStoreUserGroupsByIdFiltering() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);

        Long id = productStoreUserGroup.getId();

        defaultProductStoreUserGroupShouldBeFound("id.equals=" + id);
        defaultProductStoreUserGroupShouldNotBeFound("id.notEquals=" + id);

        defaultProductStoreUserGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductStoreUserGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultProductStoreUserGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductStoreUserGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductStoreUserGroupsByUserGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        UserGroup userGroup = UserGroupResourceIT.createEntity(em);
        em.persist(userGroup);
        em.flush();
        productStoreUserGroup.setUserGroup(userGroup);
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        Long userGroupId = userGroup.getId();

        // Get all the productStoreUserGroupList where userGroup equals to userGroupId
        defaultProductStoreUserGroupShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the productStoreUserGroupList where userGroup equals to userGroupId + 1
        defaultProductStoreUserGroupShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoreUserGroupsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        productStoreUserGroup.setUser(user);
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        Long userId = user.getId();

        // Get all the productStoreUserGroupList where user equals to userId
        defaultProductStoreUserGroupShouldBeFound("userId.equals=" + userId);

        // Get all the productStoreUserGroupList where user equals to userId + 1
        defaultProductStoreUserGroupShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoreUserGroupsByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        productStoreUserGroup.setParty(party);
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        Long partyId = party.getId();

        // Get all the productStoreUserGroupList where party equals to partyId
        defaultProductStoreUserGroupShouldBeFound("partyId.equals=" + partyId);

        // Get all the productStoreUserGroupList where party equals to partyId + 1
        defaultProductStoreUserGroupShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoreUserGroupsByProductStoreIsEqualToSomething() throws Exception {
        // Get already existing entity
        ProductStore productStore = productStoreUserGroup.getProductStore();
        productStoreUserGroupRepository.saveAndFlush(productStoreUserGroup);
        Long productStoreId = productStore.getId();

        // Get all the productStoreUserGroupList where productStore equals to productStoreId
        defaultProductStoreUserGroupShouldBeFound("productStoreId.equals=" + productStoreId);

        // Get all the productStoreUserGroupList where productStore equals to productStoreId + 1
        defaultProductStoreUserGroupShouldNotBeFound("productStoreId.equals=" + (productStoreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductStoreUserGroupShouldBeFound(String filter) throws Exception {
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreUserGroup.getId().intValue())));

        // Check, that the count call also returns 1
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductStoreUserGroupShouldNotBeFound(String filter) throws Exception {
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductStoreUserGroup() throws Exception {
        // Get the productStoreUserGroup
        restProductStoreUserGroupMockMvc.perform(get("/api/product-store-user-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStoreUserGroup() throws Exception {
        // Initialize the database
        productStoreUserGroupService.save(productStoreUserGroup);

        int databaseSizeBeforeUpdate = productStoreUserGroupRepository.findAll().size();

        // Update the productStoreUserGroup
        ProductStoreUserGroup updatedProductStoreUserGroup = productStoreUserGroupRepository.findById(productStoreUserGroup.getId()).get();
        // Disconnect from session so that the updates on updatedProductStoreUserGroup are not directly saved in db
        em.detach(updatedProductStoreUserGroup);

        restProductStoreUserGroupMockMvc.perform(put("/api/product-store-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductStoreUserGroup)))
            .andExpect(status().isOk());

        // Validate the ProductStoreUserGroup in the database
        List<ProductStoreUserGroup> productStoreUserGroupList = productStoreUserGroupRepository.findAll();
        assertThat(productStoreUserGroupList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreUserGroup testProductStoreUserGroup = productStoreUserGroupList.get(productStoreUserGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStoreUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = productStoreUserGroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreUserGroupMockMvc.perform(put("/api/product-store-user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreUserGroup)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreUserGroup in the database
        List<ProductStoreUserGroup> productStoreUserGroupList = productStoreUserGroupRepository.findAll();
        assertThat(productStoreUserGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStoreUserGroup() throws Exception {
        // Initialize the database
        productStoreUserGroupService.save(productStoreUserGroup);

        int databaseSizeBeforeDelete = productStoreUserGroupRepository.findAll().size();

        // Delete the productStoreUserGroup
        restProductStoreUserGroupMockMvc.perform(delete("/api/product-store-user-groups/{id}", productStoreUserGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStoreUserGroup> productStoreUserGroupList = productStoreUserGroupRepository.findAll();
        assertThat(productStoreUserGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
