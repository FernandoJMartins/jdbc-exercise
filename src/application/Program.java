package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		SellerDao sd = DaoFactory.createSellerDao();

		Seller seller = sd.findById(1);
		System.out.println(seller);

		
		System.out.println("----------TEST 2----------------");
		
		
		Department dept = new Department(2,null);
		List<Seller> list = sd.findByDepartment(dept);
		
		for (Seller s : list) {
			System.out.println(s);
		}
		System.out.println("----------TEST 3----------------");

		
		list = sd.findAll();
		
		for (Seller sl : list) {
			System.out.println(sl);
			
		}
		System.out.println("----------TEST 3----------------");
		
		Seller nSeller = new Seller(null, "Carlos", "cl@gmail.com", new Date(), 5000.0, dept);
		sd.insert(nSeller);
		System.out.println("Inserted! New id = " + nSeller.getId());
		
		
		System.out.println("----------TEST 4----------------");
		
		seller = sd.findById(1);
		seller.setName("Bruce Wayne");
		sd.update(seller);
		System.out.println("Updated! id = " + seller.getId());
		


		System.out.println("----------TEST 5----------------");
		System.out.println("Digit a number: ");
		int n = scan.nextInt();
		
		sd.deleteById(n);
		System.out.println("Deleted! id = " + n);
		
		scan.close();
	}
}
