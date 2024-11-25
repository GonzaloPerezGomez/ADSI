package Vista;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.GestorPeliculas;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

@SuppressWarnings("deprecation")
public class Catalogo extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textBuscador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Catalogo frame = new Catalogo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Catalogo() {
		
		GestorUsuarios.getGestorUsuarios().addObserver(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCatalogo = new JLabel("Catalogo");
		lblCatalogo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCatalogo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(0, 12, 114, 21);
		panel.add(textBuscador);
		textBuscador.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setBounds(126, 12, 57, 21);
		panel.add(lblBuscar);
		
		JLabel btnAñadirUnaPelicula = new JLabel("Añadir pelicula");
		btnAñadirUnaPelicula.setHorizontalAlignment(SwingConstants.CENTER);
		btnAñadirUnaPelicula.setBounds(199, 12, 125, 21);
		btnAñadirUnaPelicula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SolicitarPelicula solicitarPelicula = new SolicitarPelicula();
				dispose();
			}
		});
		panel.add(btnAñadirUnaPelicula);
		
		JLabel lblUsuario = new JLabel("aaaa");
		lblUsuario.setBackground(new Color(0, 0, 0));
		lblUsuario.setBounds(388, 2, 40, 40);
		ImageIcon originalIcon = new ImageIcon(Catalogo.class.getResource("/Imagenes/user.png"));
		Image image = originalIcon.getImage();
		Image scaledImage = image.getScaledInstance(lblUsuario.getWidth(), lblUsuario.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		lblUsuario.setIcon(scaledIcon);
		lblUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(GestorUsuarios.getGestorUsuarios().getRolSesion()) {
					OpcionesAdmin opcionesAdmin = new OpcionesAdmin();
				}else {
					OpcionesUsuario opcionesUsuario = new OpcionesUsuario();
				}
				dispose();
			}
		});
		panel.add(lblUsuario);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Esbozo de método generado automáticamente
		
	}
}
