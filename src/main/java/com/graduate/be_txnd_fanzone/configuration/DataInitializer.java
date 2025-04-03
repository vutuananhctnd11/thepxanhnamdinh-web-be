package com.graduate.be_txnd_fanzone.configuration;

import com.graduate.be_txnd_fanzone.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements CommandLineRunner {

    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String filePath = "src/main/resources/System.sql";
        String tableName = "role";

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            String[] sqlStatements = stream.reduce("", (accumulated, currentLine) -> accumulated + currentLine + "\n").split(";");

            String checkQuery = "SELECT COUNT(*) FROM `" + tableName + "`";
            int count = jdbcTemplate.queryForObject(checkQuery, Integer.class);

            if (count == 0) {
                for (String sql : sqlStatements) {
                    if (!sql.trim().isEmpty()) {
                        jdbcTemplate.execute(sql.trim());
                    }
                }
                log.info("=====Insert data successfully=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
