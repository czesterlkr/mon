package mon.web.rest;

import mon.Application;
import mon.domain.StatusOfHoliday;
import mon.repository.StatusOfHolidayRepository;

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
 * Test class for the StatusOfHolidayResource REST controller.
 *
 * @see StatusOfHolidayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StatusOfHolidayResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private StatusOfHolidayRepository statusOfHolidayRepository;

    private MockMvc restStatusOfHolidayMockMvc;

    private StatusOfHoliday statusOfHoliday;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StatusOfHolidayResource statusOfHolidayResource = new StatusOfHolidayResource();
        ReflectionTestUtils.setField(statusOfHolidayResource, "statusOfHolidayRepository", statusOfHolidayRepository);
        this.restStatusOfHolidayMockMvc = MockMvcBuilders.standaloneSetup(statusOfHolidayResource).build();
    }

    @Before
    public void initTest() {
        statusOfHoliday = new StatusOfHoliday();
        statusOfHoliday.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStatusOfHoliday() throws Exception {
        // Validate the database is empty
        assertThat(statusOfHolidayRepository.findAll()).hasSize(0);

        // Create the StatusOfHoliday
        restStatusOfHolidayMockMvc.perform(post("/api/statusOfHolidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statusOfHoliday)))
                .andExpect(status().isCreated());

        // Validate the StatusOfHoliday in the database
        List<StatusOfHoliday> statusOfHolidays = statusOfHolidayRepository.findAll();
        assertThat(statusOfHolidays).hasSize(1);
        StatusOfHoliday testStatusOfHoliday = statusOfHolidays.iterator().next();
        assertThat(testStatusOfHoliday.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusOfHolidays() throws Exception {
        // Initialize the database
        statusOfHolidayRepository.saveAndFlush(statusOfHoliday);

        // Get all the statusOfHolidays
        restStatusOfHolidayMockMvc.perform(get("/api/statusOfHolidays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(statusOfHoliday.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getStatusOfHoliday() throws Exception {
        // Initialize the database
        statusOfHolidayRepository.saveAndFlush(statusOfHoliday);

        // Get the statusOfHoliday
        restStatusOfHolidayMockMvc.perform(get("/api/statusOfHolidays/{id}", statusOfHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(statusOfHoliday.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStatusOfHoliday() throws Exception {
        // Get the statusOfHoliday
        restStatusOfHolidayMockMvc.perform(get("/api/statusOfHolidays/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusOfHoliday() throws Exception {
        // Initialize the database
        statusOfHolidayRepository.saveAndFlush(statusOfHoliday);

        // Update the statusOfHoliday
        statusOfHoliday.setName(UPDATED_NAME);
        restStatusOfHolidayMockMvc.perform(put("/api/statusOfHolidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(statusOfHoliday)))
                .andExpect(status().isOk());

        // Validate the StatusOfHoliday in the database
        List<StatusOfHoliday> statusOfHolidays = statusOfHolidayRepository.findAll();
        assertThat(statusOfHolidays).hasSize(1);
        StatusOfHoliday testStatusOfHoliday = statusOfHolidays.iterator().next();
        assertThat(testStatusOfHoliday.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteStatusOfHoliday() throws Exception {
        // Initialize the database
        statusOfHolidayRepository.saveAndFlush(statusOfHoliday);

        // Get the statusOfHoliday
        restStatusOfHolidayMockMvc.perform(delete("/api/statusOfHolidays/{id}", statusOfHoliday.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StatusOfHoliday> statusOfHolidays = statusOfHolidayRepository.findAll();
        assertThat(statusOfHolidays).hasSize(0);
    }
}
