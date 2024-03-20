package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	
	private Connection c;
	
	public SellerDaoJDBC(Connection c) {
		this.c = c;
	}
	
	
	@Override
	public void insert(Seller s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = c.prepareStatement(
					"SELECT seller s.*,d.Name as DepName\r\n"
					+ "FROM s INNER JOIN department d\r\n"
					+ "ON s.DepartmentId = d.Id\r\n"
					+ "WHERE s.Id = ?");
					
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = initiateDep(rs);
				Seller s = initiateSeller(rs, dep);
				
				return s;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}
	
	
	

	private Department initiateDep(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}


	private Seller initiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBaseSalary(rs.getDouble("BaseSalary"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setDepartment(dep);
		return s;
	}


	@Override
	public List<Seller> findall() {
		// TODO Auto-generated method stub
		return null;
	}

}