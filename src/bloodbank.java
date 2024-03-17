import java.sql.*;
import java.util.Scanner;


public class bloodbank {
	
		
		   
		    public static void main(String[] args) {
		        try {
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/blood_bnk", "root", "root");

		            Scanner scanner = new Scanner(System.in);
		            while (true) {
		                System.out.println("\nBlood Bank Management System");
		                System.out.println("1. Add New Donor");
		                System.out.println("2. Search Donors");
		                System.out.println("3. Update Donor Details");
		                System.out.println("4. Delete Donor");
		                System.out.println("5. List All Donors");
		                System.out.println("6. Exit");
		                System.out.print("Enter your choice: ");
		                int choice = scanner.nextInt();
		                scanner.nextLine();

		                switch (choice) {
		                    case 1:
		                        addNewDonor(conn, scanner);
		                        break;
		                    case 2:
		                        searchDonors(conn, scanner);
		                        break;
		                    case 3:
		                        updateDonorDetails(conn, scanner);
		                        break;
		                    case 4:
		                        deleteDonor(conn, scanner);
		                        break;
		                    case 5:
		                        listAllDonors(conn);
		                        break;
		                    case 6:
		                        System.out.println("Exiting...");
		                        conn.close();
		                        return;
		                    default:
		                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }

		    private static void addNewDonor(Connection conn, Scanner scanner) throws SQLException {
		        System.out.println("\nAdding New Donor");
		        System.out.print("Enter name: ");
		        String name = scanner.nextLine();

		        System.out.print("Enter blood group: ");
		        String bloodGroup = scanner.nextLine();

		        System.out.print("Enter age: ");
		        int age = scanner.nextInt();
		        scanner.nextLine(); 

		        System.out.print("Enter gender: ");
		        String gender = scanner.nextLine();

		        System.out.print("Enter contact number: ");
		        String contactNumber = scanner.nextLine();

		        System.out.print("Enter email: ");
		        String email = scanner.nextLine();

		        System.out.print("Enter address: ");
		        String address = scanner.nextLine();

		        System.out.print("Enter last donation date (YYYY-MM-DD): ");
		        String lastDonationDate = scanner.nextLine();

		        String sql = "INSERT INTO donor (name, blood_group, age, gender, contact_number, email, address, last_donation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            pstmt.setString(1, name);
		            pstmt.setString(2, bloodGroup);
		            pstmt.setInt(3, age);
		            pstmt.setString(4, gender);
		            pstmt.setString(5, contactNumber);
		            pstmt.setString(6, email);
		            pstmt.setString(7, address);
		            pstmt.setDate(8, Date.valueOf(lastDonationDate));

		            int affectedRows = pstmt.executeUpdate();
		            if (affectedRows > 0) {
		                System.out.println("Donor added successfully!");
		            } else {
		                System.out.println("A problem occurred. The donor was not added.");
		            }
		        }
		    }

		    private static void searchDonors(Connection conn, Scanner scanner) throws SQLException {
		      
		    	System.out.println("\nSearch Donors");
		        System.out.println("1. Search by Name");
		        System.out.println("2. Search by Blood Group");
		        System.out.print("Enter your choice: ");
		        int choice = scanner.nextInt();
		        scanner.nextLine(); 

		        String sql = "";
		        String searchCriteria = "";
		        switch (choice) {
		            case 1:
		                System.out.print("Enter name to search: ");
		                searchCriteria = scanner.nextLine();
		                sql = "SELECT * FROM donor WHERE name LIKE ?";
		                break;
		            case 2:
		                System.out.print("Enter blood group to search: ");
		                searchCriteria = scanner.nextLine();
		                sql = "SELECT * FROM donor WHERE blood_group = ?";
		                break;
		            default:
		                System.out.println("Invalid choice.");
		                return;
		        }

		        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		            pstmt.setString(1, "%" + searchCriteria + "%");

		            try (ResultSet rs = pstmt.executeQuery()) {
		                boolean found = false;
		                while (rs.next()) {
		                    found = true;
		                    int id = rs.getInt("id");
		                    String name = rs.getString("name");
		                    String bloodGroup = rs.getString("blood_group");
		                    int age = rs.getInt("age");
		                    String gender = rs.getString("gender");
		                    String contactNumber = rs.getString("contact_number");
		                    String email = rs.getString("email");
		                    String address = rs.getString("address");
		                    Date lastDonationDate = rs.getDate("last_donation_date");

		                    System.out.println("ID: " + id + ", Name: " + name + ", Blood Group: " + bloodGroup +
		                                       ", Age: " + age + ", Gender: " + gender + ", Contact Number: " + contactNumber +
		                                       ", Email: " + email + ", Address: " + address +
		                                       ", Last Donation Date: " + lastDonationDate);
		                }
		                if (!found) {
		                    System.out.println("No donors found.");
		                }
		            }
		        }
		    	
		    }

		    private static void updateDonorDetails(Connection conn, Scanner scanner) throws SQLException {
		        
		    	 System.out.println("\nUpdate Donor Details");
		    	    System.out.print("Enter the ID of the donor whose details you want to update: ");
		    	    int donorId = scanner.nextInt();
		    	    scanner.nextLine(); 
		    	    if (!isDonorExists(conn, donorId)) {
		    	        System.out.println("Donor with ID " + donorId + " does not exist.");
		    	        return;
		    	    }

		    	    System.out.println("Enter new details:");

		    	    System.out.print("Name: ");
		    	    String name = scanner.nextLine();

		    	    System.out.print("Blood Group: ");
		    	    String bloodGroup = scanner.nextLine();

		    	    System.out.print("Age: ");
		    	    int age = scanner.nextInt();
		    	    scanner.nextLine(); 

		    	    System.out.print("Gender: ");
		    	    String gender = scanner.nextLine();

		    	    System.out.print("Contact Number: ");
		    	    String contactNumber = scanner.nextLine();

		    	    System.out.print("Email: ");
		    	    String email = scanner.nextLine();

		    	    System.out.print("Address: ");
		    	    String address = scanner.nextLine();

		    	    System.out.print("Last Donation Date (YYYY-MM-DD): ");
		    	    String lastDonationDate = scanner.nextLine();

		    	    String sql = "UPDATE donor SET name=?, blood_group=?, age=?, gender=?, contact_number=?, email=?, address=?, last_donation_date=? WHERE id=?";
		    	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	        pstmt.setString(1, name);
		    	        pstmt.setString(2, bloodGroup);
		    	        pstmt.setInt(3, age);
		    	        pstmt.setString(4, gender);
		    	        pstmt.setString(5, contactNumber);
		    	        pstmt.setString(6, email);
		    	        pstmt.setString(7, address);
		    	        pstmt.setDate(8, Date.valueOf(lastDonationDate));
		    	        pstmt.setInt(9, donorId);

		    	        int rowsUpdated = pstmt.executeUpdate();
		    	        if (rowsUpdated > 0) {
		    	            System.out.println("Donor details updated successfully!");
		    	        } else {
		    	            System.out.println("Failed to update donor details.");
		    	        }
		    	    }
		    	}

		    
		    	private static boolean isDonorExists(Connection conn, int donorId) throws SQLException {
		    	    String sql = "SELECT COUNT(*) FROM donor WHERE id=?";
		    	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	        pstmt.setInt(1, donorId);
		    	        try (ResultSet rs = pstmt.executeQuery()) {
		    	            rs.next();
		    	            int count = rs.getInt(1);
		    	            return count > 0;
		    	        }
		    	    }
		    	
		    }

		    private static void deleteDonor(Connection conn, Scanner scanner) throws SQLException {
		       
		    	
		    	 System.out.println("\nDelete Donor");
		    	    System.out.print("Enter the ID of the donor you want to delete: ");
		    	    int donorId = scanner.nextInt();
		    	    scanner.nextLine(); 
		    	    if (!isDonorExists(conn, donorId)) {
		    	        System.out.println("Donor with ID " + donorId + " does not exist.");
		    	        return;
		    	    }

		    	    String sql = "DELETE FROM donor WHERE id=?";
		    	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    	        pstmt.setInt(1, donorId);

		    	        int rowsDeleted = pstmt.executeUpdate();
		    	        if (rowsDeleted > 0) {
		    	            System.out.println("Donor deleted successfully!");
		    	        } else {
		    	            System.out.println("Failed to delete donor.");
		    	        }
		    	    }
		    	
		    }

		    private static void listAllDonors(Connection conn) throws SQLException {
		        System.out.println("\nList of All Donors:");
		        String sql = "SELECT * FROM donor";
		        try (Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {
		            while (rs.next()) {
		                int id = rs.getInt("id");
		                String name = rs.getString("name");
		                String bloodGroup = rs.getString("blood_group");
		                int age = rs.getInt("age");
		                String gender = rs.getString("gender");
		                String contactNumber = rs.getString("contact_number");
		                String email = rs.getString("email");
		                String address = rs.getString("address");
		                Date lastDonationDate = rs.getDate("last_donation_date");

		                System.out.println("ID: " + id + ", Name: " + name + ", Blood Group: " + bloodGroup +
		                                   ", Age: " + age + ", Gender: " + gender + ", Contact Number: " + contactNumber +
		                                   ", Email: " + email + ", Address: " + address +
		                                   ", Last Donation Date: " + lastDonationDate);
		            }
		        }
		        }
	}


