package agendapd.model;

import java.io.Serializable;
import java.sql.Date;

public class UtenteBean implements Serializable, Cloneable {

	private static final long serialVersionUID = -850475236728288737L;
	private String user;
	private String pass;
	private int role;
	private String email;
	private String firstName;
	private String lastName;
	private String fiscalCode;
	private Date birthday;
	private String addressName;
	private String addressCity;
	private int addressCAP;

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public int getRole() {
		return role;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getAddressName() {
		return addressName;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public int getAddressCAP() {
		return addressCAP;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public void setAddressCAP(int addressCAP) {
		this.addressCAP = addressCAP;
	}

	public boolean isEmpty() {
		if (user == null)
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass())
			return false;
		UtenteBean oth = (UtenteBean) other;
		return this.user.equals(oth.user);
	}

	public UtenteBean clone() {
		try {
			return (UtenteBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "UtenteBean [user=" + user + ", pass=" + pass + ", role=" + role + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", fiscalCode=" + fiscalCode + ", birthday=" + birthday
				+ ", addressName=" + addressName + ", addressCity=" + addressCity + ", addressCAP=" + addressCAP + "]";
	}
}
