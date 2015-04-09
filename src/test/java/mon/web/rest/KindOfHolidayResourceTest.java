package mon.web.rest;

import mon.Application;
import mon.domain.KindOfHoliday;
import mon.repository.KindOfHolidayRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KindOfHolidayResource REST controller.
 *
 * @see KindOfHolidayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class KindOfHolidayResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private KindOfHolidayRepository kindOfHolidayRepository;

    private MockMvc restKindOfHolidayMockMvc;

    private KindOfHoliday kindOfHoliday;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KindOfHolidayResource kindOfHolidayResource = new KindOfHolidayResource();
        ReflectionTestUtils.setField(kindOfHolidayResource, "kindOfHolidayRepository", kindOfHolidayRepository);
        this.restKindOfHolidayMockMvc = MockMvcBuilders.standaloneSetup(kindOfHolidayResource).build();
    }

    @Before
    public void initTest() {
        kindOfHoliday = new KindOfHoliday();
        kindOfHoliday.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createKindOfHoliday() throws Exception {
        // Validate the database is empty
        assertThat(kindOfHolidayRepository.findAll()).hasSize(0);

        // Create the KindOfHoliday
        restKindOfHolidayMockMvc.perform(post("/api/kindOfHolidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kindOfHoliday)))
                .andExpect(status().isCreated());

        // Validate the KindOfHoliday in the database
        List<KindOfHoliday> kindOfHolidays = kindOfHolidayRepository.findAll();
        assertThat(kindOfHolidays).hasSize(1);
        KindOfHoliday testKindOfHoliday = kindOfHolidays.iterator().next();
        assertThat(testKindOfHoliday.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllKindOfHolidays() throws Exception {
        // Initialize the database
        kindOfHolidayRepository.saveAndFlush(kindOfHoliday);

        // Get all the kindOfHolidays
        restKindOfHolidayMockMvc.perform(get("/api/kindOfHolidays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(kindOfHoliday.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getKindOfHoliday() throws Exception {
        // Initialize the database
        kindOfHolidayRepository.saveAndFlush(kindOfHoliday);

        // Get the kindOfHoliday
        restKindOfHolidayMockMvc.perform(get("/api/kindOfHolidays/{id}", kindOfHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kindOfHoliday.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKindOfHoliday() throws Exception {
        // Get the kindOfHoliday
        restKindOfHolidayMockMvc.perform(get("/api/kindOfHolidays/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKindOfHoliday() throws Exception {
        // Initialize the database
        kindOfHolidayRepository.saveAndFlush(kindOfHoliday);

        // Update the kindOfHoliday
        kindOfHoliday.setName(UPDATED_NAME);
        restKindOfHolidayMockMvc.perform(put("/api/kindOfHolidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kindOfHoliday)))
                .andExpect(status().isOk());

        // Validate the KindOfHoliday in the database
        List<KindOfHoliday> kindOfHolidays = kindOfHolidayRepository.findAll();
        assertThat(kindOfHolidays).hasSize(1);
        KindOfHoliday testKindOfHoliday = kindOfHolidays.iterator().next();
        assertThat(testKindOfHoliday.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteKindOfHoliday() throws Exception {
        // Initialize the database
        kindOfHolidayRepository.saveAndFlush(kindOfHoliday);

        // Get the kindOfHoliday
        restKindOfHolidayMockMvc.perform(delete("/api/kindOfHolidays/{id}", kindOfHoliday.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KindOfHoliday> kindOfHolidays = kindOfHolidayRepository.findAll();
        assertThat(kindOfHolidays).hasSize(0);
    }
}
