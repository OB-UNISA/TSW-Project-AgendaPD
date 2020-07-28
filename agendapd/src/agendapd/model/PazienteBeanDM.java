package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class PazienteBeanDM implements Model<PazienteBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;
	}

	@Override
	public PazienteBean doRetrieveByKey(String... key) throws SQLException {
		PazienteBean bean = new PazienteBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM paziente WHERE nomeUtentePaziente=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setPatientUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setAccessMedicalRecords(rs.getBoolean(3));
			}
		} finally {
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<PazienteBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<PazienteBean> collection = new ArrayList<PazienteBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM paziente ORDER BY ?";
		if (key.length == 2)
			if (key[0] != null && key[1] == null)
				sql = "SELECT * FROM paziente WHERE nomeUtentePaziente=? ORDER BY ?";
			else if (key[0] == null && key[1] != null)
				sql = "SELECT * FROM paziente WHERE nomeUtenteDottore=? ORDER BY ?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			int ord = 1;
			if (key.length == 2)
				if (key[0] != null && key[1] == null) {
					prep.setString(1, key[0]);
					ord = 2;
				} else if (key[0] == null && key[1] != null) {
					prep.setString(1, key[1]);
					ord = 2;
				}
			prep.setString(ord, order);
			rs = prep.executeQuery();

			while (rs.next()) {
				PazienteBean bean = new PazienteBean();
				bean.setPatientUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setAccessMedicalRecords(rs.getBoolean(3));
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
	public void doSave(PazienteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO paziente(nomeUtentePaziente, nomeUtenteDottore, accessoCartelle) "
				+ "values(?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setBoolean(3, bean.getAccessMedicalRecords());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(PazienteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE paziente SET nomeUtentePaziente=?, nomeUtenteDottore=?, accessoCartelle=? "
				+ "WHERE nomeUtentePaziente=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setBoolean(3, bean.getAccessMedicalRecords().booleanValue());
			prep.setString(4, bean.getPatientUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(PazienteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM paziente WHERE nomeUtentePaziente=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
