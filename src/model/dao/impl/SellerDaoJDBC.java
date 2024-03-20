package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
					"SELECT seller.*,d.Name as DepName "
					+ "FROM seller INNER JOIN department d "
					+ "ON seller.DepartmentId = d.Id "
					+ "WHERE seller.Id = ?");
					
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
	public List<Seller> findAll() {
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = c.prepareStatement(
						"SELECT seller.*,d.Name as DepName "
						+ "FROM seller INNER JOIN department d "
						+ "ON seller.DepartmentId = d.Id "
						+ "Order by Name");
						
	
				rs = st.executeQuery();
				
				List<Seller> list = new ArrayList<>();
				Map<Integer, Department> map = new HashMap<>();
				
				while (rs.next()) {
					
					Department dept = map.get(rs.getInt("DepartmentID"));
					
					if (dept == null) {
						dept = initiateDep(rs);
						map.put(rs.getInt("DepartmentID"), dept);
					}
					
					Seller s = initiateSeller(rs, dept);
					list.add(s);	

				}
				return list;
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally {
				DB.closeStatement(st);
				DB.closeResultSet(rs);
			}
	}


	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = c.prepareStatement(
					"SELECT seller.*,d.Name as DepName "
					+ "FROM seller INNER JOIN department d "
					+ "ON seller.DepartmentId = d.Id "
					+ "WHERE DepartmentId = ? "
					+ "Order by Name");
					
			st.setInt(1, dep.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department dept = map.get(rs.getInt("DepartmentID"));
				
				if (dept == null) {
					dept = initiateDep(rs);
					map.put(rs.getInt("DepartmentID"), dept);
				}
				
				Seller s = initiateSeller(rs, dept);
				list.add(s);	

			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}
}

