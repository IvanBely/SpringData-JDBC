package com.example.SpringData_JDBC;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.SpringData_JDBC.data.DatabaseSeeder;

@SpringBootApplication
public class SpringDataJDBCApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJDBCApplication.class, args);
	}

	@Bean
	public CommandLineRunner setup(DatabaseSeeder databaseSeeder) {
		return (args) -> {
			databaseSeeder.createTables();
			databaseSeeder.insertData();
		};
	}
}
