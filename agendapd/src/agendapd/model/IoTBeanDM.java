package agendapd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

public class IoTBeanDM implements Model<IoTBean, DataSource> {

	private DataSource ds;

	@Override
	public void setDB(DataSource obj) {
		this.ds = obj;
	}

	@Override
	public IoTBean doRetrieveByKey(String... key) throws SQLException {
		IoTBean bean = new IoTBean();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM iot WHERE id=?";
		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, key[0]);
			rs = prep.executeQuery();

			if (rs.next()) {
				bean.setId(rs.getString(1));
				bean.setPass(rs.getString(2));
				bean.setPermissions(rs.getInt(3));
			}
		} finally {
			rs.close();
			prep.close();
			con.close();
		}

		return bean;
	}

	@Override
	public Collection<IoTBean> doRetrieveAll(String order, String... key) throws SQLException {
		Collection<IoTBean> collection = new ArrayList<IoTBean>();
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM iot ORDER BY ?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);
			prep.setString(1, order);
			rs = prep.executeQuery();

			while (rs.next()) {
				IoTBean bean = new IoTBean();
				bean.setId(rs.getString(1));
				bean.setPass(rs.getString(2));
				bean.setPermissions(rs.getInt(3));
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
	public void doSave(IoTBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "INSERT INTO iot(id, pass, permessi) values(?, ?, ?)";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getId());
			prep.setString(2, bean.getPass());
			prep.setInt(3, bean.getPermissions());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doUpdate(IoTBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "UPDATE iot SET id=?, pass=?, permessi=? WHERE id=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getId());
			prep.setString(2, bean.getPass());
			prep.setInt(3, bean.getPermissions());
			prep.setString(4, bean.getId());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

	@Override
	public void doDelete(IoTBean bean) throws SQLException {
		Connection con = null;
		PreparedStatement prep = null;
		String sql = "DELETE FROM iot WHERE id=?";

		try {
			con = ds.getConnection();
			prep = con.prepareStatement(sql);

			prep.setString(1, bean.getId());

			prep.executeUpdate();

		} finally {
			prep.close();
			con.close();
		}
	}

}
