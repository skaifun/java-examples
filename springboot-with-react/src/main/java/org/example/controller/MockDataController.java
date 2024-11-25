package org.example.controller;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.StaffDTO;
import org.example.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MockDataController {

    private static final Logger log = LoggerFactory.getLogger(MockDataController.class);

    @GetMapping("/users")
    public List<UserDTO> fetchAllUsers() {
        return readDataFromCsv("mock_users.csv", UserDTO.class);
    }

    @GetMapping("/staffs")
    public List<StaffDTO> fetchAllStaff() {
        return readDataFromCsv("mock_staffs.csv", StaffDTO.class);
    }

    <T> List<T> readDataFromCsv(String csvFilename, Class<T> modelClass) {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        csvMapper.registerModule(new JavaTimeModule());
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        ClassPathResource resource = new ClassPathResource(csvFilename);
        try (MappingIterator<T> iterator = csvMapper
            .readerFor(modelClass)
            .with(schema)
            .readValues(resource.getInputStream())
        ) {
            return iterator.readAll();
        } catch (IOException e) {
            log.error("parse csv error", e);
            return List.of();
        }
    }
}
