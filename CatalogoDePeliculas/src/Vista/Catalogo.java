package Vista;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Modelo.GestorGeneral;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@SuppressWarnings("deprecation")
public class Catalogo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textBuscador;
	private JScrollPane scrollPane;
	private ArrayList<Pelicula> info;
	private JList<Pelicula>listPeliculas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Catalogo();
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
				if (textBuscador.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Introduce un título");
				}
				else {
				ArrayList<Pelicula> p = GestorGeneral.getGestorGeneral().buscarPeliculas(textBuscador.getText());
				if (p.isEmpty()) {JOptionPane.showMessageDialog(null, "Película no encontrada");}
				listPeliculas = new JList<>(p.toArray(new Pelicula[0]));
				listPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				scrollPane = new JScrollPane(listPeliculas);
				scrollPane.setBounds(0, 55, 320, 220);
				
				for (Component comp : panel.getComponents()) {
		            if (comp instanceof JScrollPane) {
		                panel.remove(comp);
		            }
		        }

		        panel.add(scrollPane);
		        
		        panel.revalidate();
		        panel.repaint();
				}
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
				new SolicitarPelicula();
				dispose();
			}
		});
		panel.add(btnAñadirUnaPelicula);
		JMenuBar mB = genMenu(GestorGeneral.getGestorGeneral().getRolSesion());
		this.setJMenuBar(mB);
		setVisible(true);
	}
	
	private JMenuBar genMenu(boolean esAdmin) {
		JMenuBar mB = new JMenuBar();
		
		JMenu menu = new JMenu("Opciones");
		
		JMenuItem op1;
		JMenuItem op2;
		JMenuItem op3;
		JMenuItem op4;
		
		if(esAdmin) {
			op1 = new JMenuItem("Solicitudes de Usuario");
	        op2 = new JMenuItem("Solicitudes de Peliculas");
	        op3 = new JMenuItem("Cuentas de Usuarios");
	        op4 = new JMenuItem("Cerrar Sesion");
			
			op1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new SolicitudesUsuarios();
					dispose();
				}
			});
			
			op2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new SolicitudesPeliculas();
					dispose();
				}
			});
			
			op3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new CuentasUsuarios();
					dispose();
				}
			});
			
			op4.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					GestorGeneral.getGestorGeneral().cerrarSesion();
					new InicioDeSesion();
					dispose();
				}
			});
		}else {
			op1 = new JMenuItem("Modificar Datos");
	        op2 = new JMenuItem("Reseñas");
	        op3 = new JMenuItem("Historial");
	        op4 = new JMenuItem("Cerrar Sesion");
			
			op1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new ModificarUsuario(GestorGeneral.getGestorGeneral().obtenerUsuario());
					dispose();
				}
			});
			
			op2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new SolicitudesPeliculas();
					dispose();
				}
			});
			
			op3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new CuentasUsuarios();
					dispose();
				}
			});
			
			op4.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					GestorGeneral.getGestorGeneral().cerrarSesion();;
					new InicioDeSesion();
					dispose();
				}
			});
		}
		
		menu.add(op1);
        menu.add(op2);
        menu.add(op3);
        menu.add(op4);
		
		mB.add(menu);
		
		return mB;
	}
}
