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
public class OpcionesAdmin extends JFrame implements Observer{

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
					OpcionesAdmin frame = new OpcionesAdmin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OpcionesAdmin() {
		
		GestorUsuarios.getGestorUsuarios().addObserver(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblOpcionesUsuario = new JLabel("Opciones de Administrador");
		lblOpcionesUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblOpcionesUsuario, BorderLayout.NORTH);
		
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Esbozo de método generado automáticamente
		
	}
}
