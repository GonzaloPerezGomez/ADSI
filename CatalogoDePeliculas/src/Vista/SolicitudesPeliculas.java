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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
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
public class SolicitudesPeliculas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<Pelicula> listSolicitudesPeliculas;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new SolicitudesPeliculas();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SolicitudesPeliculas() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSolicitudesDePeliculas = new JLabel("Solicitudes de Peliculas");
		lblSolicitudesDePeliculas.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblSolicitudesDePeliculas, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
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
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listSolicitudesPeliculas.getSelectedValue() != null) {
					JSONObject info = new JSONObject(listSolicitudesPeliculas.getSelectedValue().toJSON());
					String titulo = info.getString("Titulo");
			        String director = info.getString("Director");
			        String fecha = info.getString("Fecha");
					try {
						GestorGeneral.getGestorGeneral().aceptarSolicitud(titulo, director, fecha);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					genPanel(panel);
				}
			}
		});
		btnAceptar.setBounds(323, 81, 105, 27);
		panel.add(btnAceptar);
		
		JButton btnRechazar = new JButton("Rechazar");
		btnRechazar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listSolicitudesPeliculas.getSelectedValue() != null) {
					JSONObject info = new JSONObject(listSolicitudesPeliculas.getSelectedValue().toJSON());
					String titulo = info.getString("Titulo");
			        String fecha = info.getString("Fecha");
					try {
						GestorGeneral.getGestorGeneral().rechazarSolicitud(titulo, fecha);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					genPanel(panel);
				}
			}
		});
		btnRechazar.setBounds(323, 120, 105, 27);
		panel.add(btnRechazar);
		
		genPanel(panel);
		
		setVisible(true);
	}
	
	private void genPanel(JPanel panel) {
		Pelicula[] p = GestorGeneral.getGestorGeneral().getSolicitudes();
		
		listSolicitudesPeliculas = new JList<Pelicula>(p);
		listSolicitudesPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listSolicitudesPeliculas);
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
