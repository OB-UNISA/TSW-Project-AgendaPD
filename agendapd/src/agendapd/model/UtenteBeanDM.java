package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.sql.DataSource;

public class UtenteBeanDM implements Model<UtenteBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;
	}

	@Override
	public UtenteBean doRetrieveByKey(String... key) throws SQLException {
		UtenteBean bean = new UtenteBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM utente WHERE nomeUtente=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setUser(rs.getString(1));
				bean.setPass(rs.getString(2));
				bean.setRole(rs.getInt(3));
				bean.setEmail(rs.getString(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setFiscalCode(rs.getString(7));
				bean.setBirthday(rs.getDate(8));
				bean.setAddressName(rs.getString(9));
				bean.setAddressCity(rs.getString(10));
				bean.setAddressCAP(rs.getInt(11));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<UtenteBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<UtenteBean> collection = new ArrayList<UtenteBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM utente ORDER BY ?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, order);
			rs = prep.executeQuery();

			while (rs.next()) {
				UtenteBean bean = new UtenteBean();
				bean.setUser(rs.getString(1));
				bean.setPass(rs.getString(2));
				bean.setRole(rs.getInt(3));
				bean.setEmail(rs.getString(4));
				bean.setFirstName(rs.getString(5));
				bean.setLastName(rs.getString(6));
				bean.setFiscalCode(rs.getString(7));
				bean.setBirthday(rs.getDate(8));
				bean.setAddressName(rs.getString(9));
				bean.setAddressCity(rs.getString(10));
				bean.setAddressCAP(rs.getInt(11));
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
	public void doSave(UtenteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO utente(nomeUtente, pass, ruolo, email, nome, cognome, cf, dataDiNascita, via, citta, cap) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getUser());
			prep.setString(2, bean.getPass());
			prep.setInt(3, bean.getRole());
			prep.setString(4, bean.getEmail());
			prep.setString(5, bean.getFirstName());
			prep.setString(6, bean.getLastName());
			prep.setString(7, bean.getFiscalCode());
			prep.setDate(8, bean.getBirthday(), Calendar.getInstance());
			prep.setString(9, bean.getAddressName());
			prep.setString(10, bean.getAddressCity());
			prep.setInt(11, bean.getAddressCAP());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(UtenteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE utente SET nomeUtente=?, pass=?, ruolo=?, email=?, nome=?, cognome=?, cf=?, dataDiNascita=?, via=?, "
				+ "citta=?, cap=? WHERE nomeUtente=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getUser());
			prep.setString(2, bean.getPass());
			prep.setInt(3, bean.getRole());
			prep.setString(4, bean.getEmail());
			prep.setString(5, bean.getFirstName());
			prep.setString(6, bean.getLastName());
			prep.setString(7, bean.getFiscalCode());
			prep.setDate(8, bean.getBirthday(), Calendar.getInstance());
			prep.setString(9, bean.getAddressName());
			prep.setString(10, bean.getAddressCity());
			prep.setInt(11, bean.getAddressCAP());
			prep.setString(12, bean.getUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(UtenteBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM utente WHERE nomeUtente=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getUser());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}
}
