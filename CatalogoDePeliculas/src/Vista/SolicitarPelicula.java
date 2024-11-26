package Vista;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

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
public class SolicitarPelicula extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textBuscador;
	private JScrollPane scrollPane;
	private JList<String>listPeliculas;
	private JSONObject info;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SolicitarPelicula frame = new SolicitarPelicula();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SolicitarPelicula() {
		
		GestorUsuarios.getGestorUsuarios().addObserver(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSolicitarPelicula = new JLabel("Solicitar Peliculas");
		lblSolicitarPelicula.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSolicitarPelicula, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel, BorderLayout.CENTER);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(0, 12, 114, 21);
		panel.add(textBuscador);
		textBuscador.setColumns(10);
		
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setBounds(126, 12, 57, 21);
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				info = GestorPeliculas.getGestorPeliculas().solicitarAPI(textBuscador.getText());
				
				if(info != null) {
			        String titulo = info.getString("Title");
			        String director = info.getString("Director");
			        String fecha = info.getString("Released");
			        
			        String [] p = new String [1];
			        p[0] = "<html>Titulo: " + titulo + "<br>Director: " + director + "<br>Fecha: " + fecha + "<html>";
				
					listPeliculas = new JList<String>(p);
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
		panel.add(lblBuscar);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listPeliculas != null) {
					if(listPeliculas.getSelectedValue() != null) {
						if(info != null) {
					        String titulo = info.getString("Title");
					        String director = info.getString("Director");
					        String fecha = info.getString("Released");
					        GestorPeliculas.getGestorPeliculas().gestionarSolicitud(titulo, director, fecha);
						}
					}
				}
			}
		});
		btnAñadir.setBounds(323, 140, 105, 27);
		panel.add(btnAñadir);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Catalogo();
				dispose();
			}
		});
		btnVolver.setBounds(323, 234, 105, 27);
		panel.add(btnVolver);
		
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Esbozo de método generado automáticamente
		
	}
}
