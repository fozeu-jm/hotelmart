package com.kaizerwebdesign.hotelapp.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Bean
	@ConfigurationProperties(prefix="security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
}
