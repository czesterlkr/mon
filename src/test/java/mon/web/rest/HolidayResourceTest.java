package mon.web.rest;

import mon.Application;
import mon.domain.Holiday;
import mon.repository.HolidayRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HolidayResource REST controller.
 *
 * @see HolidayResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HolidayResourceTest {


    private static final LocalDate DEFAULT_FROM_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_FROM_DATE = new LocalDate();

    private static final LocalDate DEFAULT_TO_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_TO_DATE = new LocalDate();

    private static final LocalDate DEFAULT_REQUEST_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = new LocalDate();
    private static final String DEFAULT_COMMENT = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENT = "UPDATED_TEXT";

    @Inject
    private HolidayRepository holidayRepository;

    private MockMvc restHolidayMockMvc;

    private Holiday holiday;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HolidayResource holidayResource = new HolidayResource();
        ReflectionTestUtils.setField(holidayResource, "holidayRepository", holidayRepository);
        this.restHolidayMockMvc = MockMvcBuilders.standaloneSetup(holidayResource).build();
    }

    @Before
    public void initTest() {
        holiday = new Holiday();
        holiday.setFromDate(DEFAULT_FROM_DATE);
        holiday.setToDate(DEFAULT_TO_DATE);
        holiday.setRequestDate(DEFAULT_REQUEST_DATE);
        holiday.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createHoliday() throws Exception {
        // Validate the database is empty
        assertThat(holidayRepository.findAll()).hasSize(0);

        // Create the Holiday
        restHolidayMockMvc.perform(post("/api/holidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(holiday)))
                .andExpect(status().isCreated());

        // Validate the Holiday in the database
        List<Holiday> holidays = holidayRepository.findAll();
        assertThat(holidays).hasSize(1);
        Holiday testHoliday = holidays.iterator().next();
        assertThat(testHoliday.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testHoliday.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testHoliday.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testHoliday.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllHolidays() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get all the holidays
        restHolidayMockMvc.perform(get("/api/holidays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(holiday.getId().intValue()))
                .andExpect(jsonPath("$.[0].fromDate").value(DEFAULT_FROM_DATE.toString()))
                .andExpect(jsonPath("$.[0].toDate").value(DEFAULT_TO_DATE.toString()))
                .andExpect(jsonPath("$.[0].requestDate").value(DEFAULT_REQUEST_DATE.toString()))
                .andExpect(jsonPath("$.[0].comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", holiday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(holiday.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHoliday() throws Exception {
        // Get the holiday
        restHolidayMockMvc.perform(get("/api/holidays/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Update the holiday
        holiday.setFromDate(UPDATED_FROM_DATE);
        holiday.setToDate(UPDATED_TO_DATE);
        holiday.setRequestDate(UPDATED_REQUEST_DATE);
        holiday.setComment(UPDATED_COMMENT);
        restHolidayMockMvc.perform(put("/api/holidays")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(holiday)))
                .andExpect(status().isOk());

        // Validate the Holiday in the database
        List<Holiday> holidays = holidayRepository.findAll();
        assertThat(holidays).hasSize(1);
        Holiday testHoliday = holidays.iterator().next();
        assertThat(testHoliday.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testHoliday.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testHoliday.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testHoliday.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteHoliday() throws Exception {
        // Initialize the database
        holidayRepository.saveAndFlush(holiday);

        // Get the holiday
        restHolidayMockMvc.perform(delete("/api/holidays/{id}", holiday.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Holiday> holidays = holidayRepository.findAll();
        assertThat(holidays).hasSize(0);
    }
}
