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

public class RicettaBeanDM implements Model<RicettaBean, DataSource> {

	private DataSource ds;
	private static java.util.List<String> tableNames = new ArrayList<>();

	static {
		tableNames.add("dataOrarioRicetta");
	}

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;

	}

	@Override
	public RicettaBean doRetrieveByKey(String... key) throws SQLException, IllegalArgumentException {
		RicettaBean bean = new RicettaBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ricetta WHERE nomeUtentePaziente=? AND nomeUtenteDottore=? AND dataOrarioRicetta=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			prep.setString(2, key[1]);
			prep.setTimestamp(3, Timestamp.valueOf(key[2]), Calendar.getInstance());
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setPatientUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setTimeDatePrescription(rs.getTimestamp(3));
				bean.setPrescription(rs.getBytes(4));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<RicettaBean> doRetrieveAll(String order, String... key)
			throws SQLException, IllegalArgumentException {
		Collection<RicettaBean> collection = new ArrayList<RicettaBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM ricetta ORDER BY %s DESC";
		if (key.length == 3)
			if (key[0] != null && key[1] == null && key[2] == null)
				sql = "SELECT * FROM ricetta WHERE nomeUtentePaziente=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] != null && key[2] == null)
				sql = "SELECT * FROM ricetta WHERE nomeUtenteDottore=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] == null && key[2] != null)
				sql = "SELECT * FROM ricetta WHERE dataOrarioRicetta=? ORDER BY %s DESC";
			else if (key[0] != null && key[1] != null && key[2] == null)
				sql = "SELECT * FROM ricetta WHERE nomeUtentePaziente=? AND nomeUtenteDottore=? ORDER BY %s DESC";
			else if (key[0] != null && key[1] == null && key[2] != null)
				sql = "SELECT * FROM ricetta WHERE nomeUtentePaziente=? AND dataOrarioRicetta=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] != null && key[2] != null)
				sql = "SELECT * FROM ricetta WHERE nomeUtenteDottore=? AND dataOrarioRicetta=? ORDER BY %s DESC";

		if (tableNames.contains(order))
			sql = String.format(sql, order);
		else
			sql = String.format(sql, "nomeUtentePaziente");

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			if (key.length == 3)
				if (key[0] != null && key[1] == null && key[2] == null) {
					prep.setString(1, key[0]);
				} else if (key[0] == null && key[1] != null && key[2] == null) {
					prep.setString(1, key[1]);
				} else if (key[0] == null && key[1] == null && key[2] != null) {
					prep.setTimestamp(1, Timestamp.valueOf(key[2]), Calendar.getInstance());
				} else if (key[0] != null && key[1] != null && key[2] == null) {
					prep.setString(1, key[0]);
					prep.setString(2, key[1]);
				} else if (key[0] != null && key[1] == null && key[2] != null) {
					prep.setString(1, key[0]);
					prep.setTimestamp(2, Timestamp.valueOf(key[2]), Calendar.getInstance());
				} else if (key[0] == null && key[1] != null && key[2] != null) {
					prep.setString(1, key[1]);
					prep.setTimestamp(2, Timestamp.valueOf(key[2]), Calendar.getInstance());
				}
			rs = prep.executeQuery();

			while (rs.next()) {
				RicettaBean bean = new RicettaBean();
				bean.setPatientUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setTimeDatePrescription(rs.getTimestamp(3));
				bean.setPrescription(rs.getBytes(4));
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
	public void doSave(RicettaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO ricetta(nomeUtentePaziente, nomeUtenteDottore, dataOrarioRicetta, ricetta) values(?, ?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setTimestamp(3, bean.getTimeDatePrescription(), Calendar.getInstance());
			prep.setBytes(4, bean.getPrescription());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(RicettaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE ricetta SET nomeUtentePaziente=?, nomeUtenteDottore=?, dataOrarioRicetta=?, ricetta=? "
				+ "WHERE nomeUtentePaziente=? AND nomeUtenteDottore=? AND dataOrarioRicetta=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setTimestamp(3, bean.getTimeDatePrescription(), Calendar.getInstance());
			prep.setBytes(4, bean.getPrescription());
			prep.setString(5, bean.getPatientUser());
			prep.setString(6, bean.getDoctorUser());
			prep.setTimestamp(7, bean.getTimeDatePrescription(), Calendar.getInstance());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(RicettaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM ricetta WHERE nomeUtentePaziente=? AND nomeUtenteDottore=? AND dataOrarioRicetta=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setTimestamp(3, bean.getTimeDatePrescription(), Calendar.getInstance());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
