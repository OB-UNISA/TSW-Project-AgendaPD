package agendapd.model;

import java.io.Serializable;

public class IoTBean implements Serializable {

	private static final long serialVersionUID = 2283898750445756209L;
	private String id;
	private String pass;
	private int permissions;

	public String getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "IoTBean [id=" + id + ", pass=" + pass + ", permissions=" + permissions + "]";
	}
}