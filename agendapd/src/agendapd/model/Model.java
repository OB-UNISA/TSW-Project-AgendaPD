package agendapd.model;

import java.sql.SQLException;
import java.util.Collection;

public interface Model<T, J> {
	
	public void setDB(J obj);
	
	public T doRetrieveByKey(String ...key) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order, String ...key) throws SQLException;
	
	public void doSave(T bean) throws SQLException;
	
	public void doUpdate(T bean) throws SQLException;
	
	public void doDelete(T bean) throws SQLException;
	
}
