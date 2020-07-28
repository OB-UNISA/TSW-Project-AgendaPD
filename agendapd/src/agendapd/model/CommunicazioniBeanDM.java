package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.sql.DataSource;

public class CommunicazioniBeanDM implements Model<CommunicazioneBean, DataSource> {

	private DataSource ds;
	private static java.util.List<String> tableNames = new ArrayList<>();

	static {
		tableNames.add("dataOrarioCommunicazione");
	}

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;

	}

	@Override
	public CommunicazioneBean doRetrieveByKey(String... key) throws SQLException, IllegalArgumentException {
		CommunicazioneBean bean = new CommunicazioneBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM communicazione WHERE nomeUtenteDottore=? AND dataOrarioCommunicazione=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			prep.setString(2, key[1]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setDoctorUser(rs.getString(1));
				bean.setDescription(rs.getString(2));
				bean.setTimeDateNews(rs.getTimestamp(3));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<CommunicazioneBean> doRetrieveAll(String order, String... key)
			throws SQLException, IllegalArgumentException {
		Collection<CommunicazioneBean> collection = new ArrayList<CommunicazioneBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM communicazione ORDER BY %s DESC";
		if (key.length == 2)
			if (key[0] != null && key[1] == null)
				sql = "SELECT * FROM communicazione WHERE nomeUtenteDottore=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] != null)
				sql = "SELECT * FROM communicazione WHERE dataOrarioCommunicazione=? ORDER BY %s DESC";

		if (tableNames.contains(order))
			sql = String.format(sql, order);
		else
			sql = String.format(sql, "nomeUtenteDottore");

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			if (key.length == 2)
				if (key[0] != null && key[1] == null) {
					prep.setString(1, key[0]);
				} else if (key[0] == null && key[1] != null) {
					prep.setTimestamp(1, Timestamp.valueOf(key[1]), Calendar.getInstance());
				}
			rs = prep.executeQuery();

			while (rs.next()) {
				CommunicazioneBean bean = new CommunicazioneBean();
				bean.setDoctorUser(rs.getString(1));
				bean.setDescription(rs.getString(2));
				bean.setTimeDateNews(rs.getTimestamp(3));
				collection.add(bean);
			}

		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return collection;
	}

	@Override
	public void doSave(CommunicazioneBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO communicazione(nomeUtenteDottore, descrizione, dataOrarioCommunicazione) "
				+ "values(?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setString(2, bean.getDescription());
			prep.setTimestamp(3, bean.getTimeDateNews(), Calendar.getInstance());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(CommunicazioneBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE communicazione SET nomeUtenteDottore=?, descrizione=?, dataOrarioCommunicazione=? "
				+ "WHERE nomeUtenteDottore=? AND dataOrarioCommunicazione=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setString(2, bean.getDescription());
			prep.setTimestamp(3, bean.getTimeDateNews(), Calendar.getInstance());
			prep.setString(4, bean.getDoctorUser());
			prep.setTimestamp(5, bean.getTimeDateNews(), Calendar.getInstance());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(CommunicazioneBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM communicazione WHERE nomeUtenteDottore=? AND dataOrarioCommunicazione=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setTimestamp(2, bean.getTimeDateNews(), Calendar.getInstance());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
