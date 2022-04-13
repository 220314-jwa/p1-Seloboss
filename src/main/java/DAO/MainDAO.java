package DAO;

import java.sql.SQLException;
import java.util.List;

public interface MainDAO <T> {
	
	public int create(T newObj);
	public T getById(int id);
	public List<T> getAll();
	public void update(T updateObj) throws SQLException;
	public void delete(T objToDelete) throws SQLException;
	
}
