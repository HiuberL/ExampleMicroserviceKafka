package com.microservice.entidadms.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;


@Configuration
@Getter
public class DataSourceManager  {


	@Value("${spring.database.jdbcUrl}")
	private String jdbcUrl;

	@Value("${spring.database.driverClassName}")
	private String driverClassName;

	@Value("${spring.database.connTimeout}")
	private int connTimeout;

	@Value("${spring.database.maxPoolSize}")
	private int maxPoolSize;

	@Value("${spring.database.minimunIdle}")
	private int minimunIdle;

	@Value("${spring.database.idleTimeout}")
	private int idleTimeout;

	@Value("${spring.database.maxLifeTime}")
	private int maxLifeTime;

	@Value("${spring.database.leakDetectionThreshold}")
	private int leakDetectionThreshold;

	@Value("${spring.database.user}")
	private String username;

	@Value("${spring.database.pass}")
	private String password;

	private HikariDataSource dataSourceObj;

	public Connection getConnection() throws SQLException {
		initDatasource();
		return dataSourceObj.getConnection();
	}
	
	public void initDatasource() {

		if (dataSourceObj != null && !dataSourceObj.isClosed()) {
			return;
		}

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(this.getJdbcUrl());
		hikariConfig.setUsername(this.getUsername());
		hikariConfig.setPassword(this.getPassword());
		hikariConfig.setDriverClassName(this.getDriverClassName());
		hikariConfig.setConnectionTimeout(this.getConnTimeout());
		hikariConfig.setAutoCommit(true);
		hikariConfig.setConnectionInitSql("SELECT 1");
		hikariConfig.setMaximumPoolSize(this.getMaxPoolSize());
		hikariConfig.setMinimumIdle(this.getMinimunIdle());
		hikariConfig.setMaxLifetime(this.getMaxLifeTime());
		hikariConfig.setIdleTimeout(this.getIdleTimeout());
		hikariConfig.setLeakDetectionThreshold(this.getLeakDetectionThreshold());
		dataSourceObj = new HikariDataSource(hikariConfig);

	}
}
