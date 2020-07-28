package agendapd.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.sql.DataSource;

public class CartellaClinicaBeanDM implements Model<CartellaClinicaBean, DataSource> {

	private DataSource ds;

	private static java.util.List<String> tableNames = new ArrayList<>();

	static {
		tableNames.add("dataCartella");
		tableNames.add("tipologia");
		tableNames.add("luogo");
		tableNames.add("esito");
	}

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;

	}

	@Override
	public CartellaClinicaBean doRetrieveByKey(String... key) throws SQLException, IllegalArgumentException {
		CartellaClinicaBean bean = new CartellaClinicaBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM cartellaClinica WHERE nomeUtentePaziente=? AND dataCartella=? AND tipologia=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			prep.setDate(2, Date.valueOf(key[1]), Calendar.getInstance());
			prep.setString(3, key[2]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setPatientUser(rs.getString(1));
				bean.setMedicalRecordDate(rs.getDate(2));
				bean.setTypology(rs.getString(3));
				bean.setPlace(rs.getString(4));
				bean.setResult(rs.getString(5));
				bean.setMedicalRecord(rs.getBytes(6));

			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<CartellaClinicaBean> doRetrieveAll(String order, String... key)
			throws SQLException, IllegalArgumentException {
		Collection<CartellaClinicaBean> collection = new ArrayList<CartellaClinicaBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM cartellaClinica ORDER BY %s DESC";
		if (key.length == 3)
			if (key[0] != null && key[1] == null && key[2] == null)
				sql = "SELECT * FROM cartellaClinica WHERE nomeUtentePaziente=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] != null && key[2] == null)
				sql = "SELECT * FROM cartellaClinica WHERE dataCartella=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] == null && key[2] != null)
				sql = "SELECT * FROM cartellaClinica WHERE tipologia=? ORDER BY %s DESC";
			else if (key[0] != null && key[1] != null && key[2] == null)
				sql = "SELECT * FROM cartellaClinica WHERE nomeUtentePaziente=? AND dataCartella=? ORDER BY %s DESC";
			else if (key[0] != null && key[1] == null && key[2] != null)
				sql = "SELECT * FROM cartellaClinica WHERE nomeUtentePaziente=? AND tipologia=? ORDER BY %s DESC";
			else if (key[0] == null && key[1] != null && key[2] != null)
				sql = "SELECT * FROM cartellaClinica WHERE dataCartella=? AND tipologia=? ORDER BY %s DESC";

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
					prep.setDate(1, Date.valueOf(key[1]));
				} else if (key[0] == null && key[1] == null && key[2] != null) {
					prep.setString(1, key[2]);
				} else if (key[0] != null && key[1] != null && key[2] == null) {
					prep.setString(1, key[0]);
					prep.setDate(2, Date.valueOf(key[1]));
				} else if (key[0] != null && key[1] == null && key[2] != null) {
					prep.setString(1, key[0]);
					prep.setString(2, key[2]);
				} else if (key[0] == null && key[1] != null && key[2] != null) {
					prep.setString(1, key[1]);
					prep.setDate(2, Date.valueOf(key[2]));
				}
			rs = prep.executeQuery();

			while (rs.next()) {
				CartellaClinicaBean bean = new CartellaClinicaBean();
				bean.setPatientUser(rs.getString(1));
				bean.setMedicalRecordDate(rs.getDate(2));
				bean.setTypology(rs.getString(3));
				bean.setPlace(rs.getString(4));
				bean.setResult(rs.getString(5));
				bean.setMedicalRecord(rs.getBytes(6));
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
	public void doSave(CartellaClinicaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO cartellaClinica(nomeUtentePaziente, dataCartella, tipologia, luogo, esito, cartella) values(?, ?, ?, ?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setDate(2, bean.getMedicalRecordDate(), Calendar.getInstance());
			prep.setString(3, bean.getTypology());
			prep.setString(4, bean.getPlace());
			prep.setString(5, bean.getResult());
			prep.setBytes(6, bean.getMedicalRecord());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(CartellaClinicaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE cartellaClinica SET nomeUtentePaziente=?, dataCartella=?, tipologia=?, luogo=?, esito=?, cartella=? "
				+ "WHERE nomeUtentePaziente=? AND dataCartella=? AND tipologia=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setDate(2, bean.getMedicalRecordDate(), Calendar.getInstance());
			prep.setString(3, bean.getTypology());
			prep.setString(4, bean.getPlace());
			prep.setString(5, bean.getResult());
			prep.setBytes(6, bean.getMedicalRecord());
			prep.setString(7, bean.getPatientUser());
			prep.setDate(8, bean.getMedicalRecordDate(), Calendar.getInstance());
			prep.setString(9, bean.getTypology());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(CartellaClinicaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM cartellaClinica WHERE nomeUtentePaziente=? AND dataCartella=? AND tipologia=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getPatientUser());
			prep.setDate(2, bean.getMedicalRecordDate(), Calendar.getInstance());
			prep.setString(3, bean.getTypology());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
