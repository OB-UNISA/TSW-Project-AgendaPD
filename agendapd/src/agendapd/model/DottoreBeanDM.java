package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class DottoreBeanDM implements Model<DottoreBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;
	}

	@Override
	public DottoreBean doRetrieveByKey(String... key) throws SQLException {
		DottoreBean bean = new DottoreBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM dottore WHERE nomeUtenteDottore=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setDoctorUser(rs.getString(1));
				bean.setBookingVisits(rs.getBoolean(2));
				bean.setRequestPrescriptions(rs.getBoolean(3));
				bean.setDoctorCode(rs.getString(4));
				bean.setVisitingHours(rs.getString(5));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<DottoreBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<DottoreBean> collection = new ArrayList<DottoreBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM dottore WHERE ORDER BY ?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, order);
			rs = prep.executeQuery();

			while (rs.next()) {
				DottoreBean bean = new DottoreBean();
				bean.setDoctorUser(rs.getString(1));
				bean.setBookingVisits(rs.getBoolean(2));
				bean.setRequestPrescriptions(rs.getBoolean(3));
				bean.setDoctorCode(rs.getString(4));
				bean.setVisitingHours(rs.getString(5));
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
	public void doSave(DottoreBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO dottore(nomeUtenteDottore, prenotazioneVisita, richiestaRicette, "
				+ "codiceDottore, orarioVisite) values(?, ?, ?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setBoolean(2, bean.getBookingVisits());
			prep.setBoolean(3, bean.getRequestPrescriptions());
			prep.setString(4, bean.getDoctorCode());
			prep.setString(5, bean.getVisitingHours());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(DottoreBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE dottore SET nomeUtenteDottore=?, prenotazioneVisita=?, richiestaRicette=?, "
				+ "codiceDottore=?, orarioVisite=? WHERE nomeUtenteDottore=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getDoctorUser());
			prep.setBoolean(2, bean.getBookingVisits());
			prep.setBoolean(3, bean.getRequestPrescriptions());
			prep.setString(4, bean.getDoctorCode());
			prep.setString(5, bean.getVisitingHours());
			prep.setString(6, bean.getDoctorUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(DottoreBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM dottore WHERE nomeUtenteDottore=?";

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
