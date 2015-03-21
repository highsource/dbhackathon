package org.hisrc.dbodtc.dataaccess.impl;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DelayDAOImplTest {

	private BasicDataSource dataSource;
	private DelayDAOImpl delayDAO;

	@Before
	public void setUp() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/od");
		dataSource.setUsername("postgres");
		dataSource.setPassword("lP7CFpgQE4uJsnYgkWSW");

		delayDAO = new DelayDAOImpl();
		delayDAO.setDataSource(dataSource);
	}

	@Test
	public void returnsAverage() {
		Double averageDelay = delayDAO.getAverageDelay("8002553", 491, new Date(113,
				11, 13), new Date(114, 11, 12));
		Assert.assertEquals(3.19, averageDelay, .01);

	}

	@After
	public void tearDown() throws SQLException {
		dataSource.close();
	}
}
