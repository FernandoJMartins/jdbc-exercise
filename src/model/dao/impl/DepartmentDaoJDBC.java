package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection c;
		
	public DepartmentDaoJDBC(Connection c) {
		this.c = c;
	}
	
	private Department initiateDep(ResultSet rs) throws SQLException{
		Department dep = new Department();
		dep.setId((rs.getInt("Id")));
		dep.setName(rs.getString("Name"));
		return dep;
	}
	
	
	
	
	@Override
	public void insert(Department d) {
		PreparedStatement st = null;
		try {
			st = c.prepareStatement("Insert into Department "
					+ "(Name) values (?) ", Statement.RETURN_GENERATED_KEYS);
					
					
			st.setString(1, d.getName());
		
			int rowsAffected = st.executeUpdate();
		
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					d.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}


	@Override
	public void update(Department d) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = c.prepareStatement("Select * from department d "
					+ "where d.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = initiateDep(rs);
				return dep;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
			DB.closeResultSet(rs);
		}
	}


	@Override
	public List<Department> findall() {
		// TODO Auto-generated method stub
		return null;
	}
}
