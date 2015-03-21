package org.hisrc.dbodtc.dataaccess.impl;

import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hisrc.dbodtc.dataaccess.DelayDAO;
import org.hisrc.dbodts.dto.HistoricDelayMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import de.schildbach.pte.dto.HistoricDelay;

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
			final Double result = this.jdbcTemplate.queryForObject("select "
					+ "MAX(VERSPAETUNG) MAX_VERSPAETUNG "
					+ "FROM VERSPAETUNGEN, CODES " + "WHERE "
					+ "CODES.IBNR = ? AND "
					+ "CODES.DS100 = VERSPAETUNGEN.DS100 AND " + "ZUGNR = ? ",
					new Object[] { Integer.valueOf(locationId), lineId, },
					new int[] { Types.INTEGER, Types.INTEGER, }, Double.class);
			return result == null ? 0 : result.doubleValue();
		} catch (DataAccessException daex) {
			return 0;
		}
	}

	@Override
	public HistoricDelayMap getHistoricDelayMap(String locationId, int lineId,
			Date start, Date end) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("locationId", locationId);
		namedParameters.addValue("lineId", lineId);
		namedParameters.addValue("start", start);
		namedParameters.addValue("end", end);

		try {
			List<Map<String, Object>> list = this.jdbcTemplate.queryForList("select "
					+ "VERSPAETUNG, BETRIEBSTAG "
					+ "FROM VERSPAETUNGEN, CODES " + "WHERE "
					+ "CODES.IBNR = ? AND "
					+ "CODES.DS100 = VERSPAETUNGEN.DS100 AND " + "ZUGNR = ? ",
					new Object[] { Integer.valueOf(locationId), lineId, },
					new int[] { Types.INTEGER, Types.INTEGER, });

			return new HistoricDelayMap(list);
		} catch (DataAccessException daex) {
			return null;
		}
	}

}
