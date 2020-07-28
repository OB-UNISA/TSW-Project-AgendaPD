package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class SegretariaBeanDM implements Model<SegretariaBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;

	}

	@Override
	public SegretariaBean doRetrieveByKey(String... key) throws SQLException {
		SegretariaBean bean = new SegretariaBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM segretaria WHERE nomeUtenteDottore=?";
		if (key.length == 2) {
			sql = "SELECT * FROM segretaria WHERE nomeUtenteSegretaria=?";
		}
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setSecretaryUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setAccessPatients(rs.getBoolean(3));
				bean.setAccessMedicalRecords(rs.getBoolean(4));
				bean.setAccessPrescriptions(rs.getBoolean(5));
				bean.setAccessBookingVisits(rs.getBoolean(6));
				bean.setEditPrescriptions(rs.getBoolean(7));
				bean.setAddPatients(rs.getBoolean(8));
				bean.setDeletePatients(rs.getBoolean(9));
				bean.setAddNews(rs.getBoolean(10));
				bean.setDeleteNews(rs.getBoolean(11));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<SegretariaBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<SegretariaBean> collection = new ArrayList<SegretariaBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM segretaria ORDER BY ?";
		if (key.length == 2)
			if (key[0] != null && key[1] == null)
				sql = "SELECT * FROM paziente WHERE nomeUtenteSegretaria=? ORDER BY ?";
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
				SegretariaBean bean = new SegretariaBean();
				bean.setSecretaryUser(rs.getString(1));
				bean.setDoctorUser(rs.getString(2));
				bean.setAccessPatients(rs.getBoolean(3));
				bean.setAccessMedicalRecords(rs.getBoolean(4));
				bean.setAccessPrescriptions(rs.getBoolean(5));
				bean.setAccessBookingVisits(rs.getBoolean(6));
				bean.setEditPrescriptions(rs.getBoolean(7));
				bean.setAddPatients(rs.getBoolean(8));
				bean.setDeletePatients(rs.getBoolean(9));
				bean.setAddNews(rs.getBoolean(10));
				bean.setDeleteNews(rs.getBoolean(11));
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
	public void doSave(SegretariaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO segretaria(nomeUtenteSegretaria, nomeUtenteDottore, accessoPazienti, accessoCartelle, accessoRicette, "
				+ "accessoPrenotazioni, modificaRicette, aggiuntaPazienti, eliminazionePazienti, aggiuntaNews, eliminazioneNews) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getSecretaryUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setBoolean(3, bean.getAccessPatients());
			prep.setBoolean(4, bean.getAccessMedicalRecords());
			prep.setBoolean(5, bean.getAccessPrescriptions());
			prep.setBoolean(6, bean.getAccessBookingVisits());
			prep.setBoolean(7, bean.getEditPrescriptions());
			prep.setBoolean(8, bean.getAddPatients());
			prep.setBoolean(9, bean.getDeletePatients());
			prep.setBoolean(10, bean.getAddNews());
			prep.setBoolean(11, bean.getDeleteNews());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(SegretariaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE segretaria SET nomeUtenteSegretaria=?, nomeUtenteDottore=?, accessoPazienti=?, accessoCartelle=?, accessoRicette=?, "
				+ "accessoPrenotazioni=?, modificaRicette=?, aggiuntaPazienti=?, eliminazionePazienti=?, aggiuntaNews=?, eliminazioneNews=? "
				+ "WHERE nomeUtenteDottore=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getSecretaryUser());
			prep.setString(2, bean.getDoctorUser());
			prep.setBoolean(3, bean.getAccessPatients());
			prep.setBoolean(4, bean.getAccessMedicalRecords());
			prep.setBoolean(5, bean.getAccessPrescriptions());
			prep.setBoolean(6, bean.getAccessBookingVisits());
			prep.setBoolean(7, bean.getEditPrescriptions());
			prep.setBoolean(8, bean.getAddPatients());
			prep.setBoolean(9, bean.getDeletePatients());
			prep.setBoolean(10, bean.getAddNews());
			prep.setBoolean(11, bean.getDeleteNews());
			prep.setString(12, bean.getDoctorUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(SegretariaBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM segretaria WHERE nomeUtenteDottore=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getSecretaryUser());
			prep.setString(2, bean.getDoctorUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
