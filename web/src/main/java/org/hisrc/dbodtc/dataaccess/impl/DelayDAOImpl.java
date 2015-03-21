package org.hisrc.dbodtc.dataaccess.impl;

import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.hisrc.dbodtc.dataaccess.DelayDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class DelayDAOImpl implements DelayDAO {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Double getAverageDelay(String locationId, Integer lineId,
			Date start, Date end) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("locationId", locationId);
		namedParameters.addValue("lineId", lineId);
		namedParameters.addValue("start", start);
		namedParameters.addValue("end", end);

		return this.jdbcTemplate
				.queryForObject(
						"select SUM(MAX_VERSPAETUNG)/(?::date - ?::date)::decimal AVERAGE_VERSPAETUNG "
								+ "FROM ( "
								+ "select DS100, ZUGNR, BETRIEBSTAG, MAX(VERSPAETUNG) MAX_VERSPAETUNG from VERSPAETUNGEN WHERE "
								+ "DS100 = ? AND "
								+ "ZUGNR = ? AND "
								+ "VERSPAETUNGEN.BETRIEBSTAG >= ? AND "
								+ "VERSPAETUNGEN.BETRIEBSTAG < ? "
								+ "GROUP BY DS100, ZUGNR, BETRIEBSTAG) AS V "
								+ "GROUP BY DS100, ZUGNR "
								+ "ORDER BY AVERAGE_VERSPAETUNG DESC",
						new Object[] { end, start, locationId, lineId, start, end },
						new int[] { Types.DATE,
										Types.DATE, Types.VARCHAR, Types.INTEGER, Types.DATE,
								Types.DATE }, Double.class);
	}

}
