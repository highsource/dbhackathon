package org.hisrc.dbodtc.dataaccess.impl;

import java.sql.Types;
import java.util.Date;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hisrc.dbodtc.dataaccess.DelayDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class DelayDAOImpl implements DelayDAO {

	private JdbcTemplate jdbcTemplate;

	@Inject
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public double getAverageDelay(String locationId, int lineId, Date start,
			Date end) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("locationId", locationId);
		namedParameters.addValue("lineId", lineId);
		namedParameters.addValue("start", start);
		namedParameters.addValue("end", end);

		try {
			final Double result = this.jdbcTemplate
					.queryForObject(
							"select SUM(MAX_VERSPAETUNG)/(?::date - ?::date)::decimal AVERAGE_VERSPAETUNG "
									+ "FROM ( "
									+ "select VERSPAETUNGEN.DS100, ZUGNR, BETRIEBSTAG, MAX(VERSPAETUNG) MAX_VERSPAETUNG "
									+ "FROM VERSPAETUNGEN, CODES "
									+ "WHERE "
									+ "CODES.IBNR = ? AND "
									+ "CODES.DS100 = VERSPAETUNGEN.DS100 AND "
									+ "ZUGNR = ? AND "
									+ "VERSPAETUNGEN.BETRIEBSTAG >= ? AND "
									+ "VERSPAETUNGEN.BETRIEBSTAG < ? "
									+ "GROUP BY VERSPAETUNGEN.DS100, ZUGNR, BETRIEBSTAG) AS V "
									+ "GROUP BY DS100, ZUGNR "
									+ "ORDER BY AVERAGE_VERSPAETUNG DESC",
							new Object[] { end, start,
									Integer.valueOf(locationId), lineId, start,
									end }, new int[] { Types.DATE, Types.DATE,
									Types.INTEGER, Types.INTEGER, Types.DATE,
									Types.DATE }, Double.class);
			return result == null ? 0 : result.doubleValue();
		} catch (DataAccessException daex) {
			return 0;
		}
	}

}
