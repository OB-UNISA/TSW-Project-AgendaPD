package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class StudioBeanDM implements Model<StudioBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;

	}

	@Override
	public StudioBean doRetrieveByKey(String... key) throws SQLException {
		StudioBean bean = new StudioBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM studio WHERE nomeUtenteDottore=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setDoctorUser(rs.getString(1));
				bean.setFreeSeats(rs.getInt(2));
				bean.setEnteredPatients(rs.getInt(3));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<StudioBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<StudioBean> collection = new ArrayList<StudioBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM studio ORDER BY ?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, order);
			rs = prep.executeQuery();

			while (rs.next()) {
				StudioBean bean = new StudioBean();
				bean.setDoctorUser(rs.getString(1));
				bean.setFreeSeats(rs.getInt(2));
				bean.setEnteredPatients(rs.getInt(3));
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
	public void doSave(StudioBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO studio(nomeUtenteDottore, numeroPostiLiberi, pazientiEntrati) values(?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setInt(2, bean.getFreeSeats());
			prep.setInt(3, bean.getEnteredPatients());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(StudioBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE studio SET nomeUtenteDottore=?, numeroPostiLiberi=?, pazientiEntrati=? WHERE nomeUtenteDottore=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setInt(2, bean.getFreeSeats());
			prep.setInt(3, bean.getEnteredPatients());
			prep.setString(4, bean.getDoctorUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(StudioBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM studio WHERE nomeUtenteDottore=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
