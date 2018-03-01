package com.mayaccom.web.rest;

import com.mayaccom.JMayaApp;

import com.mayaccom.domain.Host;
import com.mayaccom.repository.HostRepository;
import com.mayaccom.service.HostService;
import com.mayaccom.repository.search.HostSearchRepository;
import com.mayaccom.service.dto.HostDTO;
import com.mayaccom.service.mapper.HostMapper;
import com.mayaccom.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mayaccom.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HostResource REST controller.
 *
 * @see HostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JMayaApp.class)
public class HostResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_3 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private HostService hostService;

    @Autowired
    private HostSearchRepository hostSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHostMockMvc;

    private Host host;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HostResource hostResource = new HostResource(hostService);
        this.restHostMockMvc = MockMvcBuilders.standaloneSetup(hostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Host createEntity(EntityManager em) {
        Host host = new Host()
            .name(DEFAULT_NAME)
            .firstContactName(DEFAULT_FIRST_CONTACT_NAME)
            .lastContactName(DEFAULT_LAST_CONTACT_NAME)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .phone3(DEFAULT_PHONE_3)
            .email(DEFAULT_EMAIL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD);
        return host;
    }

    @Before
    public void initTest() {
        hostSearchRepository.deleteAll();
        host = createEntity(em);
    }

    @Test
    @Transactional
    public void createHost() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.toDto(host);
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate + 1);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHost.getFirstContactName()).isEqualTo(DEFAULT_FIRST_CONTACT_NAME);
        assertThat(testHost.getLastContactName()).isEqualTo(DEFAULT_LAST_CONTACT_NAME);
        assertThat(testHost.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHost.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testHost.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testHost.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testHost.getPhone3()).isEqualTo(DEFAULT_PHONE_3);
        assertThat(testHost.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testHost.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testHost.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the Host in Elasticsearch
        Host hostEs = hostSearchRepository.findOne(testHost.getId());
        assertThat(hostEs).isEqualToIgnoringGivenFields(testHost);
    }

    @Test
    @Transactional
    public void createHostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hostRepository.findAll().size();

        // Create the Host with an existing ID
        host.setId(1L);
        HostDTO hostDTO = hostMapper.toDto(host);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHostMockMvc.perform(post("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHosts() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get all the hostList
        restHostMockMvc.perform(get("/api/hosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstContactName").value(hasItem(DEFAULT_FIRST_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastContactName").value(hasItem(DEFAULT_LAST_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].phone3").value(hasItem(DEFAULT_PHONE_3.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);

        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(host.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.firstContactName").value(DEFAULT_FIRST_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.lastContactName").value(DEFAULT_LAST_CONTACT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.phone3").value(DEFAULT_PHONE_3.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHost() throws Exception {
        // Get the host
        restHostMockMvc.perform(get("/api/hosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Update the host
        Host updatedHost = hostRepository.findOne(host.getId());
        // Disconnect from session so that the updates on updatedHost are not directly saved in db
        em.detach(updatedHost);
        updatedHost
            .name(UPDATED_NAME)
            .firstContactName(UPDATED_FIRST_CONTACT_NAME)
            .lastContactName(UPDATED_LAST_CONTACT_NAME)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .phone3(UPDATED_PHONE_3)
            .email(UPDATED_EMAIL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);
        HostDTO hostDTO = hostMapper.toDto(updatedHost);

        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isOk());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate);
        Host testHost = hostList.get(hostList.size() - 1);
        assertThat(testHost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHost.getFirstContactName()).isEqualTo(UPDATED_FIRST_CONTACT_NAME);
        assertThat(testHost.getLastContactName()).isEqualTo(UPDATED_LAST_CONTACT_NAME);
        assertThat(testHost.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHost.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testHost.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testHost.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testHost.getPhone3()).isEqualTo(UPDATED_PHONE_3);
        assertThat(testHost.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHost.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testHost.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the Host in Elasticsearch
        Host hostEs = hostSearchRepository.findOne(testHost.getId());
        assertThat(hostEs).isEqualToIgnoringGivenFields(testHost);
    }

    @Test
    @Transactional
    public void updateNonExistingHost() throws Exception {
        int databaseSizeBeforeUpdate = hostRepository.findAll().size();

        // Create the Host
        HostDTO hostDTO = hostMapper.toDto(host);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHostMockMvc.perform(put("/api/hosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hostDTO)))
            .andExpect(status().isCreated());

        // Validate the Host in the database
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);
        int databaseSizeBeforeDelete = hostRepository.findAll().size();

        // Get the host
        restHostMockMvc.perform(delete("/api/hosts/{id}", host.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hostExistsInEs = hostSearchRepository.exists(host.getId());
        assertThat(hostExistsInEs).isFalse();

        // Validate the database is empty
        List<Host> hostList = hostRepository.findAll();
        assertThat(hostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHost() throws Exception {
        // Initialize the database
        hostRepository.saveAndFlush(host);
        hostSearchRepository.save(host);

        // Search the host
        restHostMockMvc.perform(get("/api/_search/hosts?query=id:" + host.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(host.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstContactName").value(hasItem(DEFAULT_FIRST_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastContactName").value(hasItem(DEFAULT_LAST_CONTACT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].phone3").value(hasItem(DEFAULT_PHONE_3.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Host.class);
        Host host1 = new Host();
        host1.setId(1L);
        Host host2 = new Host();
        host2.setId(host1.getId());
        assertThat(host1).isEqualTo(host2);
        host2.setId(2L);
        assertThat(host1).isNotEqualTo(host2);
        host1.setId(null);
        assertThat(host1).isNotEqualTo(host2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HostDTO.class);
        HostDTO hostDTO1 = new HostDTO();
        hostDTO1.setId(1L);
        HostDTO hostDTO2 = new HostDTO();
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
        hostDTO2.setId(hostDTO1.getId());
        assertThat(hostDTO1).isEqualTo(hostDTO2);
        hostDTO2.setId(2L);
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
        hostDTO1.setId(null);
        assertThat(hostDTO1).isNotEqualTo(hostDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hostMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hostMapper.fromId(null)).isNull();
    }
}
