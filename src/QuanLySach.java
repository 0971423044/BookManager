import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuanLySach {

	private ArrayList<Sach> list;

	public QuanLySach() { list = new ArrayList<>(); }

	public ArrayList<Sach> getList() { return list; }

	public Connection getConnection() {
		Connection conn = null;
		String strConn = "jdbc:mysql://localhost:3306/qlSach";
		String USER = "root";
		String PASSWORD = "abc123";
		try {
			conn = DriverManager.getConnection(strConn, USER, PASSWORD);
			System.out.println("success!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void readData() throws SQLException {
		list.clear();
		Statement st = null;
		Connection conn = getConnection();
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from Sach");
			while (res.next()) {
				Sach s = new Sach(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5),
						res.getInt(6), res.getDouble(7));
				list.add(s);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (st != null) st.close();
			if (conn != null) conn.close();
		}
	}

	public boolean themSach(Sach s1) throws SQLException {
		PreparedStatement st = null;
		Connection conn = getConnection();
		boolean state = false;
		try {
			String sql = "INSERT INTO Sach (maSach, tenSach, tacGia, namXB, nhaXB, soTrang, donGia) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)";
			st = conn.prepareCall(sql);
			st.setInt(1, s1.getMaSach());
			st.setString(2, s1.gettenSach());
			st.setString(3, s1.getTacGia());
			st.setInt(4, s1.getNamXB());
			st.setString(5, s1.getNhaXB());
			st.setInt(6, s1.getSoTrang());
			st.setDouble(7, s1.getDonGia());
			st.execute();
			state = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (st != null) st.close();
			if (conn != null) conn.close();
		}
		return state;
	}

	public boolean SuaSach(Sach s1, int index) throws SQLException {
		PreparedStatement st = null;
		Connection conn = getConnection();
		boolean state = false;
		try {
			String sql = "update Sach set tenSach = ?, tacGia = ?, namXB = ?, nhaXB = ?, soTrang = ?, donGia = ? where maSach = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, s1.gettenSach());
			st.setString(2, s1.getTacGia());
			st.setInt(3, s1.getNamXB());
			st.setString(4, s1.getNhaXB());
			st.setInt(5, s1.getSoTrang());
			st.setDouble(6, s1.getDonGia());
			st.setInt(7, s1.getMaSach());
			st.execute();
			state = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (st != null) st.close();
			if (conn != null) conn.close();
		}
		list.set(index, s1);
		return state;
	}

	public boolean xoaSach(int maSach) throws SQLException {
		String sql = "delete from Sach where maSach = " + maSach;
		boolean state = false;
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			state = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (pst != null) pst.close();
			if (conn != null) conn.close();
		}
		return state;
	}

	public Sach timkiem(int maSach) throws SQLException {
		String sql = "select * from Sach where maSach = " + maSach;
		Statement st = null;
		Sach s = null;
		Connection conn = getConnection();
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {
				s = new Sach(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5),
						res.getInt(6), res.getDouble(7));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (st != null) st.close();	
			if (conn != null) conn.close();
		}
		return s;
	}
}
