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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.BoxLayout; 



@SuppressWarnings("deprecation")
public class SolicitudesUsuarios extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SolicitudesUsuarios frame = new SolicitudesUsuarios();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SolicitudesUsuarios() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSolicitudesDeUsuarios = new JLabel("Solicitudes de Usuarios");
		lblSolicitudesDeUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSolicitudesDeUsuarios, BorderLayout.NORTH);
		
		GestorUsuarios gestUsuario = GestorUsuarios.getGestorUsuarios();
		gestUsuario.addObserver(this);
		List<String> listaUsuarios = gestUsuario.mostrarUsuariosNoAceptados();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		contentPane.add(panel, BorderLayout.CENTER);
		for (String usuario : listaUsuarios ) {
			
			
			JLabel lblUsuario = new JLabel(usuario);
			panel.add(lblUsuario);
			lblUsuario.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)  {
					gestUsuario.aceptarUsuario(usuario); 
					SolicitudesUsuarios frame = new SolicitudesUsuarios();
					dispose();
				}
			});			
			panel.revalidate();
	        panel.repaint();		
		}
		
		JPanel panel2 = new JPanel(); 
		contentPane.add(panel2, BorderLayout.EAST);
		
		JButton volver= new JButton("Volver");
		panel2.add(volver,BorderLayout.EAST);
		volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Catalogo catalogo = new Catalogo();
				dispose();
			}
		});
		
		setVisible(true);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		//for ( JLabel usuario : panel.getComponents()) 
			
		
	}
}
