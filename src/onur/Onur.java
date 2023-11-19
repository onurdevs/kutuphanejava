package onur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Onur {
	
	private static JFrame frame;
    private static JFrame ikinciPencere;

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/onur";
		String username = "root";
		String password = "";
		
		List<String> resultList = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(url, username, password);
			
			String sql = "SELECT * FROM posts";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				//System.out.println("Adı: "+ resultSet.getString("title"));
				resultList.add(resultSet.getString("title"));
			}
			
			resultSet.close();
			preparedStatement.close();
			
			connection.close();
			
			for(String sonuc: resultList){
				System.out.println(sonuc);
			}
			
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		frame = new JFrame("Kütüphanem");
		JMenuBar menuBar = new JMenuBar();
		
		JMenu authors = new JMenu("Yazarlar");
		JMenu books = new JMenu("Kitaplar");
		JMenu account = new JMenu("Hesabım");
		JMenuItem authorList = new JMenuItem("Yazar Listesi");
		JMenuItem authorCreate = new JMenuItem("Yazar Oluştur");
		
		JMenuItem bookList = new JMenuItem("Kitap Listesi");
		
		JMenuItem accountSignOut = new JMenuItem("Çıkış Yap");
		
		authors.add(authorList);
		authors.add(authorCreate);
		
		books.add(bookList);
		
		account.add(accountSignOut);
		
		menuBar.add(authors);
		menuBar.add(books);
		menuBar.add(account);
		
		authorCreate.addActionListener(e -> {
			ikinciPencereyiGoster();
			frame.dispose();
		});
		
		frame.setJMenuBar(menuBar);
		
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	
	private static void ikinciPencereyiGoster() {
        ikinciPencere = new JFrame("İkinci Pencere");
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("Ad");
        model.addColumn("Soyad");
        
        model.addRow(new Object[] {"Onur", "Er"});
        model.addRow(new Object[] {"Ümit", "Çelebi"});
        
        JTable tablo = new JTable(model);
        JScrollPane tabloPaneli = new JScrollPane(tablo);
        
        ikinciPencere.getContentPane().add(tabloPaneli);
        
        
        ikinciPencere.setSize(400,300);
        ikinciPencere.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // İkinci pencerenin kapatılma olaylarını dinleyen WindowListener
        ikinciPencere.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(true); // Birinci pencereyi tekrar görüntüle
            }
        });

        ikinciPencere.setVisible(true);
    }

}
