
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;

public class frmSach extends JFrame implements ActionListener, MouseListener {
	private QuanLySach s;
	private JTable table;
	private JTextField txtMaSach, txtTenSach, txtTacGia, txtNamXB, txtNhaXB, txtSoTrang, txtDonGia, txtTim;
	private JButton btnThem, btnSua, btnXoa, btnXoaTrang, btnTim;
	private DefaultTableModel tableModel;
//	private static final String FILENAME = "D:\\data.txt";

	// Constructor
	public frmSach() throws SQLException {

		setTitle("Quan ly sach");
		setSize(800, 750);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel pnlNorth = new JPanel();
		add(pnlNorth, BorderLayout.NORTH);
		Border NorthBorder = BorderFactory.createLineBorder(Color.red);
		TitledBorder northTitleBorder = new TitledBorder(NorthBorder, "Thong tin sach");
		pnlNorth.setBorder(northTitleBorder);
		Box b = Box.createVerticalBox();
		Box b1, b2, b3, b4, b5, b6;
		JLabel lbMaSach, lbTheLoai, lbTacGia, lbNamXB, lbNhaXB, lbSoTrang, lbDonGia;
		b.add(b1 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b1.add(lbMaSach = new JLabel("Ma sach: "));
		b1.add(txtMaSach = new JTextField(10));

		b.add(b2 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b2.add(lbTheLoai = new JLabel("Ten sach: "));
		b2.add(txtTenSach = new JTextField(15));
		b2.add(lbTacGia = new JLabel("Tac gia: "));
		b2.add(txtTacGia = new JTextField(15));

		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(15));
		b3.add(lbNamXB = new JLabel("Nam xuat ban: "));
		b3.add(txtNamXB = new JTextField(10));
		b3.add(lbNhaXB = new JLabel("Nha xuat ban: "));
		b3.add(txtNhaXB = new JTextField(15));

		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(15));
		b4.add(lbSoTrang = new JLabel("So trang: "));
		b4.add(txtSoTrang = new JTextField(15));
		b4.add(lbDonGia = new JLabel("Don gia: "));
		b4.add(txtDonGia = new JTextField(15));

		lbMaSach.setPreferredSize(lbNamXB.getPreferredSize());
		lbTheLoai.setPreferredSize(lbNamXB.getPreferredSize());
		lbTacGia.setPreferredSize(lbNamXB.getPreferredSize());
		lbNamXB.setPreferredSize(lbNamXB.getPreferredSize());
		lbNhaXB.setPreferredSize(lbNamXB.getPreferredSize());
		lbSoTrang.setPreferredSize(lbNamXB.getPreferredSize());
		lbDonGia.setPreferredSize(lbNamXB.getPreferredSize());

		b.add(b6 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(20));
		String[] headers = "ID;The loai;Tac gia;Nam xuat ban;Nha xuat ban;So trang;Don gia".split(";");
		tableModel = new DefaultTableModel(headers, 0);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(table = new JTable(tableModel));
		table.setRowHeight(20);
		table.setAutoCreateRowSorter(true);

		JPanel pnS = new JPanel();
		add(pnS, BorderLayout.SOUTH);

		pnS.add(scroll);
		pnlNorth.add(b);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		add(split, BorderLayout.CENTER);
		JPanel pnlLeft, pnlRight;
		split.add(pnlLeft = new JPanel());
		split.add(pnlRight = new JPanel());
		pnlRight.add(new JLabel("Nhap ma sach can tim: "));
		pnlRight.add(txtTim = new JTextField(10));
		pnlRight.add(btnTim = new JButton("Tim"));
		pnlLeft.add(btnThem = new JButton("Them"));
		pnlLeft.add(btnSua = new JButton("Sua"));
		pnlLeft.add(btnXoa = new JButton("Xoa"));
		pnlLeft.add(btnXoaTrang = new JButton("Xoa trang"));
		LoadDatabase();
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnTim.addActionListener(this);
		table.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem))
			themActions();
		if (o.equals(btnSua))
			try {
				SuaAction();
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
		if (o.equals(btnXoa))
			try {
				XoaActions();
			} catch (HeadlessException e2) {
				e2.printStackTrace();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		if (o.equals(btnXoaTrang))
			xoaTrangActions();
		if (o.equals(btnTim))
			try {
				TimAction();
			} catch (NumberFormatException | SQLException e1) {
				e1.printStackTrace();
			}
	}

	private void xoaTrangActions() {
		txtMaSach.setText("");
		txtTenSach.setText("");
		txtTacGia.setText("");
		txtNamXB.setText("");
		txtNhaXB.setText("");
		txtSoTrang.setText("");
		txtDonGia.setText("");
		txtMaSach.requestFocus();
	}

	private void themActions() {
		try {
			int maSach = Integer.parseInt(txtMaSach.getText());
			String theloai = txtTenSach.getText();
			String tacGia = txtTacGia.getText();
			int namXB = Integer.parseInt(txtNamXB.getText());
			String nhaXB = txtNhaXB.getText();
			int soTrang = Integer.parseInt(txtSoTrang.getText());
			double donGia = Double.parseDouble(txtDonGia.getText());
			Sach s1 = new Sach(maSach, theloai, tacGia, namXB, nhaXB, soTrang, donGia);

			if (s.themSach(s1) == true) {
				String[] row = { Integer.toString(maSach), theloai, tacGia, Integer.toString(namXB), nhaXB,
						Integer.toString(soTrang), Double.toString(donGia) };
				tableModel.addRow(row);
				xoaTrangActions();
				JOptionPane.showMessageDialog(this, "Them thanh cong", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this, "Loi");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void SuaAction() throws SQLException {
		int row = table.getSelectedRow();

		try {
			int maSach = Integer.parseInt(txtMaSach.getText());
			String tenSach = txtTenSach.getText();
			String tacGia = txtTacGia.getText();
			int namXB = Integer.parseInt(txtNamXB.getText());
			String nhaXB = txtNhaXB.getText();
			int soTrang = Integer.parseInt(txtSoTrang.getText());
			double donGia = Double.parseDouble(txtDonGia.getText());
			Sach s1 = new Sach(maSach, tenSach, tacGia, namXB, nhaXB, soTrang, donGia);
			if (s.SuaSach(s1, row))
				JOptionPane.showMessageDialog(this, "Sua thanh cong", "Thong bao", JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Loi nhap du lieu");
		} finally {
			LoadDatabase();
		}
	}

	private void XoaActions() throws HeadlessException, SQLException {
		int row = table.getSelectedRow();
		if (row != -1) {
			int maSach = Integer.parseInt(txtMaSach.getText());
			int hoiNhac = JOptionPane.showConfirmDialog(this, "Ban co muon xoa", "Chu y", JOptionPane.YES_NO_OPTION);
			System.out.println(maSach);
			if (hoiNhac == JOptionPane.YES_OPTION) {
				if (s.xoaSach(maSach)) {
					tableModel.removeRow(row);
					JOptionPane.showMessageDialog(this, "Xoa thanh cong", "Thong bao", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void TimAction() throws NumberFormatException, SQLException {
		String id = txtTim.getText();
		Sach book = s.timkiem(Integer.parseInt(id));
		if (book != null)
			JOptionPane.showMessageDialog(this,
					book.getMaSach() + " " + book.gettenSach() + " " + book.getTacGia() + " " + book.getNhaXB() + " "
							+ book.getNamXB() + " " + book.getSoTrang() + " " + book.getDonGia(),
					"Tim thay", JOptionPane.PLAIN_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "Khong tim thay!", "Error", JOptionPane.ERROR_MESSAGE);
	}

	void LoadDatabase() throws SQLException {
		s = new QuanLySach();
		s.readData();
		tableModel.setRowCount(0);
		Object[] a = new Object[7];
		for (int i = 0; i < s.getList().size(); i++) {
			a[0] = s.getList().get(i).getMaSach();
			a[1] = s.getList().get(i).gettenSach();
			a[2] = s.getList().get(i).getTacGia();
			a[3] = s.getList().get(i).getNamXB();
			a[4] = s.getList().get(i).getNhaXB();
			a[5] = s.getList().get(i).getSoTrang();
			a[6] = s.getList().get(i).getDonGia();
			tableModel.addRow(a);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = table.getSelectedRow();
		txtMaSach.setText(table.getValueAt(row, 0).toString());
		txtTenSach.setText(table.getValueAt(row, 1).toString());
		txtTacGia.setText(table.getValueAt(row, 2).toString());
		txtNamXB.setText(table.getValueAt(row, 3).toString());
		txtNhaXB.setText(table.getValueAt(row, 4).toString());
		txtSoTrang.setText(table.getValueAt(row, 5).toString());
		txtDonGia.setText(table.getValueAt(row, 6).toString());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
