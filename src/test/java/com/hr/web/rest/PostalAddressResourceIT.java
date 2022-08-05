package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PostalAddress;
import com.hr.domain.Geo;
import com.hr.domain.ContactMech;
import com.hr.domain.GeoPoint;
import com.hr.repository.PostalAddressRepository;
import com.hr.service.PostalAddressService;
import com.hr.service.dto.PostalAddressCriteria;
import com.hr.service.PostalAddressQueryService;

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
 * Integration tests for the {@link PostalAddressResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostalAddressResourceIT {

    private static final String DEFAULT_TO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    private static final String DEFAULT_CUSTOM_ADDRESS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ADDRESS_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_STR = "AAAAAAAAAA";
    private static final String UPDATED_STATE_STR = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_STR = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_STR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INDIAN_ADDRESS = false;
    private static final Boolean UPDATED_IS_INDIAN_ADDRESS = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    @Autowired
    private PostalAddressRepository postalAddressRepository;

    @Autowired
    private PostalAddressService postalAddressService;

    @Autowired
    private PostalAddressQueryService postalAddressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostalAddressMockMvc;

    private PostalAddress postalAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalAddress createEntity(EntityManager em) {
        PostalAddress postalAddress = new PostalAddress()
            .toName(DEFAULT_TO_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .city(DEFAULT_CITY)
            .landmark(DEFAULT_LANDMARK)
            .postalCode(DEFAULT_POSTAL_CODE)
            .isDefault(DEFAULT_IS_DEFAULT)
            .customAddressType(DEFAULT_CUSTOM_ADDRESS_TYPE)
            .stateStr(DEFAULT_STATE_STR)
            .countryStr(DEFAULT_COUNTRY_STR)
            .isIndianAddress(DEFAULT_IS_INDIAN_ADDRESS)
            .note(DEFAULT_NOTE)
            .directions(DEFAULT_DIRECTIONS);
        return postalAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostalAddress createUpdatedEntity(EntityManager em) {
        PostalAddress postalAddress = new PostalAddress()
            .toName(UPDATED_TO_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .landmark(UPDATED_LANDMARK)
            .postalCode(UPDATED_POSTAL_CODE)
            .isDefault(UPDATED_IS_DEFAULT)
            .customAddressType(UPDATED_CUSTOM_ADDRESS_TYPE)
            .stateStr(UPDATED_STATE_STR)
            .countryStr(UPDATED_COUNTRY_STR)
            .isIndianAddress(UPDATED_IS_INDIAN_ADDRESS)
            .note(UPDATED_NOTE)
            .directions(UPDATED_DIRECTIONS);
        return postalAddress;
    }

    @BeforeEach
    public void initTest() {
        postalAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostalAddress() throws Exception {
        int databaseSizeBeforeCreate = postalAddressRepository.findAll().size();
        // Create the PostalAddress
        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isCreated());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PostalAddress testPostalAddress = postalAddressList.get(postalAddressList.size() - 1);
        assertThat(testPostalAddress.getToName()).isEqualTo(DEFAULT_TO_NAME);
        assertThat(testPostalAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testPostalAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testPostalAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPostalAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testPostalAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPostalAddress.isIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
        assertThat(testPostalAddress.getCustomAddressType()).isEqualTo(DEFAULT_CUSTOM_ADDRESS_TYPE);
        assertThat(testPostalAddress.getStateStr()).isEqualTo(DEFAULT_STATE_STR);
        assertThat(testPostalAddress.getCountryStr()).isEqualTo(DEFAULT_COUNTRY_STR);
        assertThat(testPostalAddress.isIsIndianAddress()).isEqualTo(DEFAULT_IS_INDIAN_ADDRESS);
        assertThat(testPostalAddress.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testPostalAddress.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
    }

    @Test
    @Transactional
    public void createPostalAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postalAddressRepository.findAll().size();

        // Create the PostalAddress with an existing ID
        postalAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostalAddressMockMvc.perform(post("/api/postal-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostalAddresses() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList
        restPostalAddressMockMvc.perform(get("/api/postal-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].toName").value(hasItem(DEFAULT_TO_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].customAddressType").value(hasItem(DEFAULT_CUSTOM_ADDRESS_TYPE)))
            .andExpect(jsonPath("$.[*].stateStr").value(hasItem(DEFAULT_STATE_STR)))
            .andExpect(jsonPath("$.[*].countryStr").value(hasItem(DEFAULT_COUNTRY_STR)))
            .andExpect(jsonPath("$.[*].isIndianAddress").value(hasItem(DEFAULT_IS_INDIAN_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)));
    }
    
    @Test
    @Transactional
    public void getPostalAddress() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get the postalAddress
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/{id}", postalAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postalAddress.getId().intValue()))
            .andExpect(jsonPath("$.toName").value(DEFAULT_TO_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.customAddressType").value(DEFAULT_CUSTOM_ADDRESS_TYPE))
            .andExpect(jsonPath("$.stateStr").value(DEFAULT_STATE_STR))
            .andExpect(jsonPath("$.countryStr").value(DEFAULT_COUNTRY_STR))
            .andExpect(jsonPath("$.isIndianAddress").value(DEFAULT_IS_INDIAN_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS));
    }


    @Test
    @Transactional
    public void getPostalAddressesByIdFiltering() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        Long id = postalAddress.getId();

        defaultPostalAddressShouldBeFound("id.equals=" + id);
        defaultPostalAddressShouldNotBeFound("id.notEquals=" + id);

        defaultPostalAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostalAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultPostalAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostalAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByToNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName equals to DEFAULT_TO_NAME
        defaultPostalAddressShouldBeFound("toName.equals=" + DEFAULT_TO_NAME);

        // Get all the postalAddressList where toName equals to UPDATED_TO_NAME
        defaultPostalAddressShouldNotBeFound("toName.equals=" + UPDATED_TO_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByToNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName not equals to DEFAULT_TO_NAME
        defaultPostalAddressShouldNotBeFound("toName.notEquals=" + DEFAULT_TO_NAME);

        // Get all the postalAddressList where toName not equals to UPDATED_TO_NAME
        defaultPostalAddressShouldBeFound("toName.notEquals=" + UPDATED_TO_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByToNameIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName in DEFAULT_TO_NAME or UPDATED_TO_NAME
        defaultPostalAddressShouldBeFound("toName.in=" + DEFAULT_TO_NAME + "," + UPDATED_TO_NAME);

        // Get all the postalAddressList where toName equals to UPDATED_TO_NAME
        defaultPostalAddressShouldNotBeFound("toName.in=" + UPDATED_TO_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByToNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName is not null
        defaultPostalAddressShouldBeFound("toName.specified=true");

        // Get all the postalAddressList where toName is null
        defaultPostalAddressShouldNotBeFound("toName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByToNameContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName contains DEFAULT_TO_NAME
        defaultPostalAddressShouldBeFound("toName.contains=" + DEFAULT_TO_NAME);

        // Get all the postalAddressList where toName contains UPDATED_TO_NAME
        defaultPostalAddressShouldNotBeFound("toName.contains=" + UPDATED_TO_NAME);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByToNameNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where toName does not contain DEFAULT_TO_NAME
        defaultPostalAddressShouldNotBeFound("toName.doesNotContain=" + DEFAULT_TO_NAME);

        // Get all the postalAddressList where toName does not contain UPDATED_TO_NAME
        defaultPostalAddressShouldBeFound("toName.doesNotContain=" + UPDATED_TO_NAME);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 equals to DEFAULT_ADDRESS_1
        defaultPostalAddressShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the postalAddressList where address1 equals to UPDATED_ADDRESS_1
        defaultPostalAddressShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 not equals to DEFAULT_ADDRESS_1
        defaultPostalAddressShouldNotBeFound("address1.notEquals=" + DEFAULT_ADDRESS_1);

        // Get all the postalAddressList where address1 not equals to UPDATED_ADDRESS_1
        defaultPostalAddressShouldBeFound("address1.notEquals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultPostalAddressShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the postalAddressList where address1 equals to UPDATED_ADDRESS_1
        defaultPostalAddressShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 is not null
        defaultPostalAddressShouldBeFound("address1.specified=true");

        // Get all the postalAddressList where address1 is null
        defaultPostalAddressShouldNotBeFound("address1.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 contains DEFAULT_ADDRESS_1
        defaultPostalAddressShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the postalAddressList where address1 contains UPDATED_ADDRESS_1
        defaultPostalAddressShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address1 does not contain DEFAULT_ADDRESS_1
        defaultPostalAddressShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the postalAddressList where address1 does not contain UPDATED_ADDRESS_1
        defaultPostalAddressShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 equals to DEFAULT_ADDRESS_2
        defaultPostalAddressShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the postalAddressList where address2 equals to UPDATED_ADDRESS_2
        defaultPostalAddressShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 not equals to DEFAULT_ADDRESS_2
        defaultPostalAddressShouldNotBeFound("address2.notEquals=" + DEFAULT_ADDRESS_2);

        // Get all the postalAddressList where address2 not equals to UPDATED_ADDRESS_2
        defaultPostalAddressShouldBeFound("address2.notEquals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultPostalAddressShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the postalAddressList where address2 equals to UPDATED_ADDRESS_2
        defaultPostalAddressShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 is not null
        defaultPostalAddressShouldBeFound("address2.specified=true");

        // Get all the postalAddressList where address2 is null
        defaultPostalAddressShouldNotBeFound("address2.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 contains DEFAULT_ADDRESS_2
        defaultPostalAddressShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the postalAddressList where address2 contains UPDATED_ADDRESS_2
        defaultPostalAddressShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where address2 does not contain DEFAULT_ADDRESS_2
        defaultPostalAddressShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the postalAddressList where address2 does not contain UPDATED_ADDRESS_2
        defaultPostalAddressShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city equals to DEFAULT_CITY
        defaultPostalAddressShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the postalAddressList where city equals to UPDATED_CITY
        defaultPostalAddressShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city not equals to DEFAULT_CITY
        defaultPostalAddressShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the postalAddressList where city not equals to UPDATED_CITY
        defaultPostalAddressShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city in DEFAULT_CITY or UPDATED_CITY
        defaultPostalAddressShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the postalAddressList where city equals to UPDATED_CITY
        defaultPostalAddressShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city is not null
        defaultPostalAddressShouldBeFound("city.specified=true");

        // Get all the postalAddressList where city is null
        defaultPostalAddressShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city contains DEFAULT_CITY
        defaultPostalAddressShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the postalAddressList where city contains UPDATED_CITY
        defaultPostalAddressShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where city does not contain DEFAULT_CITY
        defaultPostalAddressShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the postalAddressList where city does not contain UPDATED_CITY
        defaultPostalAddressShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark equals to DEFAULT_LANDMARK
        defaultPostalAddressShouldBeFound("landmark.equals=" + DEFAULT_LANDMARK);

        // Get all the postalAddressList where landmark equals to UPDATED_LANDMARK
        defaultPostalAddressShouldNotBeFound("landmark.equals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark not equals to DEFAULT_LANDMARK
        defaultPostalAddressShouldNotBeFound("landmark.notEquals=" + DEFAULT_LANDMARK);

        // Get all the postalAddressList where landmark not equals to UPDATED_LANDMARK
        defaultPostalAddressShouldBeFound("landmark.notEquals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark in DEFAULT_LANDMARK or UPDATED_LANDMARK
        defaultPostalAddressShouldBeFound("landmark.in=" + DEFAULT_LANDMARK + "," + UPDATED_LANDMARK);

        // Get all the postalAddressList where landmark equals to UPDATED_LANDMARK
        defaultPostalAddressShouldNotBeFound("landmark.in=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark is not null
        defaultPostalAddressShouldBeFound("landmark.specified=true");

        // Get all the postalAddressList where landmark is null
        defaultPostalAddressShouldNotBeFound("landmark.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark contains DEFAULT_LANDMARK
        defaultPostalAddressShouldBeFound("landmark.contains=" + DEFAULT_LANDMARK);

        // Get all the postalAddressList where landmark contains UPDATED_LANDMARK
        defaultPostalAddressShouldNotBeFound("landmark.contains=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByLandmarkNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where landmark does not contain DEFAULT_LANDMARK
        defaultPostalAddressShouldNotBeFound("landmark.doesNotContain=" + DEFAULT_LANDMARK);

        // Get all the postalAddressList where landmark does not contain UPDATED_LANDMARK
        defaultPostalAddressShouldBeFound("landmark.doesNotContain=" + UPDATED_LANDMARK);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultPostalAddressShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the postalAddressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPostalAddressShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultPostalAddressShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the postalAddressList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultPostalAddressShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultPostalAddressShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the postalAddressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultPostalAddressShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode is not null
        defaultPostalAddressShouldBeFound("postalCode.specified=true");

        // Get all the postalAddressList where postalCode is null
        defaultPostalAddressShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode contains DEFAULT_POSTAL_CODE
        defaultPostalAddressShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the postalAddressList where postalCode contains UPDATED_POSTAL_CODE
        defaultPostalAddressShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultPostalAddressShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the postalAddressList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultPostalAddressShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByIsDefaultIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isDefault equals to DEFAULT_IS_DEFAULT
        defaultPostalAddressShouldBeFound("isDefault.equals=" + DEFAULT_IS_DEFAULT);

        // Get all the postalAddressList where isDefault equals to UPDATED_IS_DEFAULT
        defaultPostalAddressShouldNotBeFound("isDefault.equals=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsDefaultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isDefault not equals to DEFAULT_IS_DEFAULT
        defaultPostalAddressShouldNotBeFound("isDefault.notEquals=" + DEFAULT_IS_DEFAULT);

        // Get all the postalAddressList where isDefault not equals to UPDATED_IS_DEFAULT
        defaultPostalAddressShouldBeFound("isDefault.notEquals=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsDefaultIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isDefault in DEFAULT_IS_DEFAULT or UPDATED_IS_DEFAULT
        defaultPostalAddressShouldBeFound("isDefault.in=" + DEFAULT_IS_DEFAULT + "," + UPDATED_IS_DEFAULT);

        // Get all the postalAddressList where isDefault equals to UPDATED_IS_DEFAULT
        defaultPostalAddressShouldNotBeFound("isDefault.in=" + UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsDefaultIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isDefault is not null
        defaultPostalAddressShouldBeFound("isDefault.specified=true");

        // Get all the postalAddressList where isDefault is null
        defaultPostalAddressShouldNotBeFound("isDefault.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType equals to DEFAULT_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldBeFound("customAddressType.equals=" + DEFAULT_CUSTOM_ADDRESS_TYPE);

        // Get all the postalAddressList where customAddressType equals to UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldNotBeFound("customAddressType.equals=" + UPDATED_CUSTOM_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType not equals to DEFAULT_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldNotBeFound("customAddressType.notEquals=" + DEFAULT_CUSTOM_ADDRESS_TYPE);

        // Get all the postalAddressList where customAddressType not equals to UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldBeFound("customAddressType.notEquals=" + UPDATED_CUSTOM_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType in DEFAULT_CUSTOM_ADDRESS_TYPE or UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldBeFound("customAddressType.in=" + DEFAULT_CUSTOM_ADDRESS_TYPE + "," + UPDATED_CUSTOM_ADDRESS_TYPE);

        // Get all the postalAddressList where customAddressType equals to UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldNotBeFound("customAddressType.in=" + UPDATED_CUSTOM_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType is not null
        defaultPostalAddressShouldBeFound("customAddressType.specified=true");

        // Get all the postalAddressList where customAddressType is null
        defaultPostalAddressShouldNotBeFound("customAddressType.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType contains DEFAULT_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldBeFound("customAddressType.contains=" + DEFAULT_CUSTOM_ADDRESS_TYPE);

        // Get all the postalAddressList where customAddressType contains UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldNotBeFound("customAddressType.contains=" + UPDATED_CUSTOM_ADDRESS_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCustomAddressTypeNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where customAddressType does not contain DEFAULT_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldNotBeFound("customAddressType.doesNotContain=" + DEFAULT_CUSTOM_ADDRESS_TYPE);

        // Get all the postalAddressList where customAddressType does not contain UPDATED_CUSTOM_ADDRESS_TYPE
        defaultPostalAddressShouldBeFound("customAddressType.doesNotContain=" + UPDATED_CUSTOM_ADDRESS_TYPE);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByStateStrIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr equals to DEFAULT_STATE_STR
        defaultPostalAddressShouldBeFound("stateStr.equals=" + DEFAULT_STATE_STR);

        // Get all the postalAddressList where stateStr equals to UPDATED_STATE_STR
        defaultPostalAddressShouldNotBeFound("stateStr.equals=" + UPDATED_STATE_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByStateStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr not equals to DEFAULT_STATE_STR
        defaultPostalAddressShouldNotBeFound("stateStr.notEquals=" + DEFAULT_STATE_STR);

        // Get all the postalAddressList where stateStr not equals to UPDATED_STATE_STR
        defaultPostalAddressShouldBeFound("stateStr.notEquals=" + UPDATED_STATE_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByStateStrIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr in DEFAULT_STATE_STR or UPDATED_STATE_STR
        defaultPostalAddressShouldBeFound("stateStr.in=" + DEFAULT_STATE_STR + "," + UPDATED_STATE_STR);

        // Get all the postalAddressList where stateStr equals to UPDATED_STATE_STR
        defaultPostalAddressShouldNotBeFound("stateStr.in=" + UPDATED_STATE_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByStateStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr is not null
        defaultPostalAddressShouldBeFound("stateStr.specified=true");

        // Get all the postalAddressList where stateStr is null
        defaultPostalAddressShouldNotBeFound("stateStr.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByStateStrContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr contains DEFAULT_STATE_STR
        defaultPostalAddressShouldBeFound("stateStr.contains=" + DEFAULT_STATE_STR);

        // Get all the postalAddressList where stateStr contains UPDATED_STATE_STR
        defaultPostalAddressShouldNotBeFound("stateStr.contains=" + UPDATED_STATE_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByStateStrNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where stateStr does not contain DEFAULT_STATE_STR
        defaultPostalAddressShouldNotBeFound("stateStr.doesNotContain=" + DEFAULT_STATE_STR);

        // Get all the postalAddressList where stateStr does not contain UPDATED_STATE_STR
        defaultPostalAddressShouldBeFound("stateStr.doesNotContain=" + UPDATED_STATE_STR);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr equals to DEFAULT_COUNTRY_STR
        defaultPostalAddressShouldBeFound("countryStr.equals=" + DEFAULT_COUNTRY_STR);

        // Get all the postalAddressList where countryStr equals to UPDATED_COUNTRY_STR
        defaultPostalAddressShouldNotBeFound("countryStr.equals=" + UPDATED_COUNTRY_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr not equals to DEFAULT_COUNTRY_STR
        defaultPostalAddressShouldNotBeFound("countryStr.notEquals=" + DEFAULT_COUNTRY_STR);

        // Get all the postalAddressList where countryStr not equals to UPDATED_COUNTRY_STR
        defaultPostalAddressShouldBeFound("countryStr.notEquals=" + UPDATED_COUNTRY_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr in DEFAULT_COUNTRY_STR or UPDATED_COUNTRY_STR
        defaultPostalAddressShouldBeFound("countryStr.in=" + DEFAULT_COUNTRY_STR + "," + UPDATED_COUNTRY_STR);

        // Get all the postalAddressList where countryStr equals to UPDATED_COUNTRY_STR
        defaultPostalAddressShouldNotBeFound("countryStr.in=" + UPDATED_COUNTRY_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr is not null
        defaultPostalAddressShouldBeFound("countryStr.specified=true");

        // Get all the postalAddressList where countryStr is null
        defaultPostalAddressShouldNotBeFound("countryStr.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr contains DEFAULT_COUNTRY_STR
        defaultPostalAddressShouldBeFound("countryStr.contains=" + DEFAULT_COUNTRY_STR);

        // Get all the postalAddressList where countryStr contains UPDATED_COUNTRY_STR
        defaultPostalAddressShouldNotBeFound("countryStr.contains=" + UPDATED_COUNTRY_STR);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByCountryStrNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where countryStr does not contain DEFAULT_COUNTRY_STR
        defaultPostalAddressShouldNotBeFound("countryStr.doesNotContain=" + DEFAULT_COUNTRY_STR);

        // Get all the postalAddressList where countryStr does not contain UPDATED_COUNTRY_STR
        defaultPostalAddressShouldBeFound("countryStr.doesNotContain=" + UPDATED_COUNTRY_STR);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByIsIndianAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isIndianAddress equals to DEFAULT_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldBeFound("isIndianAddress.equals=" + DEFAULT_IS_INDIAN_ADDRESS);

        // Get all the postalAddressList where isIndianAddress equals to UPDATED_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldNotBeFound("isIndianAddress.equals=" + UPDATED_IS_INDIAN_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsIndianAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isIndianAddress not equals to DEFAULT_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldNotBeFound("isIndianAddress.notEquals=" + DEFAULT_IS_INDIAN_ADDRESS);

        // Get all the postalAddressList where isIndianAddress not equals to UPDATED_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldBeFound("isIndianAddress.notEquals=" + UPDATED_IS_INDIAN_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsIndianAddressIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isIndianAddress in DEFAULT_IS_INDIAN_ADDRESS or UPDATED_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldBeFound("isIndianAddress.in=" + DEFAULT_IS_INDIAN_ADDRESS + "," + UPDATED_IS_INDIAN_ADDRESS);

        // Get all the postalAddressList where isIndianAddress equals to UPDATED_IS_INDIAN_ADDRESS
        defaultPostalAddressShouldNotBeFound("isIndianAddress.in=" + UPDATED_IS_INDIAN_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByIsIndianAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where isIndianAddress is not null
        defaultPostalAddressShouldBeFound("isIndianAddress.specified=true");

        // Get all the postalAddressList where isIndianAddress is null
        defaultPostalAddressShouldNotBeFound("isIndianAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note equals to DEFAULT_NOTE
        defaultPostalAddressShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the postalAddressList where note equals to UPDATED_NOTE
        defaultPostalAddressShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note not equals to DEFAULT_NOTE
        defaultPostalAddressShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the postalAddressList where note not equals to UPDATED_NOTE
        defaultPostalAddressShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultPostalAddressShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the postalAddressList where note equals to UPDATED_NOTE
        defaultPostalAddressShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note is not null
        defaultPostalAddressShouldBeFound("note.specified=true");

        // Get all the postalAddressList where note is null
        defaultPostalAddressShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByNoteContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note contains DEFAULT_NOTE
        defaultPostalAddressShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the postalAddressList where note contains UPDATED_NOTE
        defaultPostalAddressShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where note does not contain DEFAULT_NOTE
        defaultPostalAddressShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the postalAddressList where note does not contain UPDATED_NOTE
        defaultPostalAddressShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions equals to DEFAULT_DIRECTIONS
        defaultPostalAddressShouldBeFound("directions.equals=" + DEFAULT_DIRECTIONS);

        // Get all the postalAddressList where directions equals to UPDATED_DIRECTIONS
        defaultPostalAddressShouldNotBeFound("directions.equals=" + UPDATED_DIRECTIONS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions not equals to DEFAULT_DIRECTIONS
        defaultPostalAddressShouldNotBeFound("directions.notEquals=" + DEFAULT_DIRECTIONS);

        // Get all the postalAddressList where directions not equals to UPDATED_DIRECTIONS
        defaultPostalAddressShouldBeFound("directions.notEquals=" + UPDATED_DIRECTIONS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsIsInShouldWork() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions in DEFAULT_DIRECTIONS or UPDATED_DIRECTIONS
        defaultPostalAddressShouldBeFound("directions.in=" + DEFAULT_DIRECTIONS + "," + UPDATED_DIRECTIONS);

        // Get all the postalAddressList where directions equals to UPDATED_DIRECTIONS
        defaultPostalAddressShouldNotBeFound("directions.in=" + UPDATED_DIRECTIONS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions is not null
        defaultPostalAddressShouldBeFound("directions.specified=true");

        // Get all the postalAddressList where directions is null
        defaultPostalAddressShouldNotBeFound("directions.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions contains DEFAULT_DIRECTIONS
        defaultPostalAddressShouldBeFound("directions.contains=" + DEFAULT_DIRECTIONS);

        // Get all the postalAddressList where directions contains UPDATED_DIRECTIONS
        defaultPostalAddressShouldNotBeFound("directions.contains=" + UPDATED_DIRECTIONS);
    }

    @Test
    @Transactional
    public void getAllPostalAddressesByDirectionsNotContainsSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);

        // Get all the postalAddressList where directions does not contain DEFAULT_DIRECTIONS
        defaultPostalAddressShouldNotBeFound("directions.doesNotContain=" + DEFAULT_DIRECTIONS);

        // Get all the postalAddressList where directions does not contain UPDATED_DIRECTIONS
        defaultPostalAddressShouldBeFound("directions.doesNotContain=" + UPDATED_DIRECTIONS);
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);
        Geo state = GeoResourceIT.createEntity(em);
        em.persist(state);
        em.flush();
        postalAddress.setState(state);
        postalAddressRepository.saveAndFlush(postalAddress);
        Long stateId = state.getId();

        // Get all the postalAddressList where state equals to stateId
        defaultPostalAddressShouldBeFound("stateId.equals=" + stateId);

        // Get all the postalAddressList where state equals to stateId + 1
        defaultPostalAddressShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);
        Geo pincode = GeoResourceIT.createEntity(em);
        em.persist(pincode);
        em.flush();
        postalAddress.setPincode(pincode);
        postalAddressRepository.saveAndFlush(postalAddress);
        Long pincodeId = pincode.getId();

        // Get all the postalAddressList where pincode equals to pincodeId
        defaultPostalAddressShouldBeFound("pincodeId.equals=" + pincodeId);

        // Get all the postalAddressList where pincode equals to pincodeId + 1
        defaultPostalAddressShouldNotBeFound("pincodeId.equals=" + (pincodeId + 1));
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);
        Geo country = GeoResourceIT.createEntity(em);
        em.persist(country);
        em.flush();
        postalAddress.setCountry(country);
        postalAddressRepository.saveAndFlush(postalAddress);
        Long countryId = country.getId();

        // Get all the postalAddressList where country equals to countryId
        defaultPostalAddressShouldBeFound("countryId.equals=" + countryId);

        // Get all the postalAddressList where country equals to countryId + 1
        defaultPostalAddressShouldNotBeFound("countryId.equals=" + (countryId + 1));
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByContactMechIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);
        ContactMech contactMech = ContactMechResourceIT.createEntity(em);
        em.persist(contactMech);
        em.flush();
        postalAddress.setContactMech(contactMech);
        postalAddressRepository.saveAndFlush(postalAddress);
        Long contactMechId = contactMech.getId();

        // Get all the postalAddressList where contactMech equals to contactMechId
        defaultPostalAddressShouldBeFound("contactMechId.equals=" + contactMechId);

        // Get all the postalAddressList where contactMech equals to contactMechId + 1
        defaultPostalAddressShouldNotBeFound("contactMechId.equals=" + (contactMechId + 1));
    }


    @Test
    @Transactional
    public void getAllPostalAddressesByGeoPointIsEqualToSomething() throws Exception {
        // Initialize the database
        postalAddressRepository.saveAndFlush(postalAddress);
        GeoPoint geoPoint = GeoPointResourceIT.createEntity(em);
        em.persist(geoPoint);
        em.flush();
        postalAddress.setGeoPoint(geoPoint);
        postalAddressRepository.saveAndFlush(postalAddress);
        Long geoPointId = geoPoint.getId();

        // Get all the postalAddressList where geoPoint equals to geoPointId
        defaultPostalAddressShouldBeFound("geoPointId.equals=" + geoPointId);

        // Get all the postalAddressList where geoPoint equals to geoPointId + 1
        defaultPostalAddressShouldNotBeFound("geoPointId.equals=" + (geoPointId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostalAddressShouldBeFound(String filter) throws Exception {
        restPostalAddressMockMvc.perform(get("/api/postal-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postalAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].toName").value(hasItem(DEFAULT_TO_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].customAddressType").value(hasItem(DEFAULT_CUSTOM_ADDRESS_TYPE)))
            .andExpect(jsonPath("$.[*].stateStr").value(hasItem(DEFAULT_STATE_STR)))
            .andExpect(jsonPath("$.[*].countryStr").value(hasItem(DEFAULT_COUNTRY_STR)))
            .andExpect(jsonPath("$.[*].isIndianAddress").value(hasItem(DEFAULT_IS_INDIAN_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)));

        // Check, that the count call also returns 1
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostalAddressShouldNotBeFound(String filter) throws Exception {
        restPostalAddressMockMvc.perform(get("/api/postal-addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPostalAddress() throws Exception {
        // Get the postalAddress
        restPostalAddressMockMvc.perform(get("/api/postal-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostalAddress() throws Exception {
        // Initialize the database
        postalAddressService.save(postalAddress);

        int databaseSizeBeforeUpdate = postalAddressRepository.findAll().size();

        // Update the postalAddress
        PostalAddress updatedPostalAddress = postalAddressRepository.findById(postalAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPostalAddress are not directly saved in db
        em.detach(updatedPostalAddress);
        updatedPostalAddress
            .toName(UPDATED_TO_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .city(UPDATED_CITY)
            .landmark(UPDATED_LANDMARK)
            .postalCode(UPDATED_POSTAL_CODE)
            .isDefault(UPDATED_IS_DEFAULT)
            .customAddressType(UPDATED_CUSTOM_ADDRESS_TYPE)
            .stateStr(UPDATED_STATE_STR)
            .countryStr(UPDATED_COUNTRY_STR)
            .isIndianAddress(UPDATED_IS_INDIAN_ADDRESS)
            .note(UPDATED_NOTE)
            .directions(UPDATED_DIRECTIONS);

        restPostalAddressMockMvc.perform(put("/api/postal-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPostalAddress)))
            .andExpect(status().isOk());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeUpdate);
        PostalAddress testPostalAddress = postalAddressList.get(postalAddressList.size() - 1);
        assertThat(testPostalAddress.getToName()).isEqualTo(UPDATED_TO_NAME);
        assertThat(testPostalAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testPostalAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testPostalAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPostalAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testPostalAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPostalAddress.isIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
        assertThat(testPostalAddress.getCustomAddressType()).isEqualTo(UPDATED_CUSTOM_ADDRESS_TYPE);
        assertThat(testPostalAddress.getStateStr()).isEqualTo(UPDATED_STATE_STR);
        assertThat(testPostalAddress.getCountryStr()).isEqualTo(UPDATED_COUNTRY_STR);
        assertThat(testPostalAddress.isIsIndianAddress()).isEqualTo(UPDATED_IS_INDIAN_ADDRESS);
        assertThat(testPostalAddress.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testPostalAddress.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
    }

    @Test
    @Transactional
    public void updateNonExistingPostalAddress() throws Exception {
        int databaseSizeBeforeUpdate = postalAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostalAddressMockMvc.perform(put("/api/postal-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postalAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PostalAddress in the database
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostalAddress() throws Exception {
        // Initialize the database
        postalAddressService.save(postalAddress);

        int databaseSizeBeforeDelete = postalAddressRepository.findAll().size();

        // Delete the postalAddress
        restPostalAddressMockMvc.perform(delete("/api/postal-addresses/{id}", postalAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostalAddress> postalAddressList = postalAddressRepository.findAll();
        assertThat(postalAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
