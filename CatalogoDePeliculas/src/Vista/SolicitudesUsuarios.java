package Vista;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
public class SolicitudesUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<String>listaUsuarios;
	private String usuarioElegido;
	private JList<String> listaSolicitudesUsuarios;
	private JScrollPane scrollPane;

	//genera la vista de solicitudes de registro de los usuarios
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
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		contentPane.add(panel, BorderLayout.CENTER);
		
		
		JButton btnAñadir = new JButton("Aceptar");
		btnAñadir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listaSolicitudesUsuarios.getSelectedValue() != null) {// si hay algo seleccionado
					GestorGeneral.getGestorGeneral().aceptarUsuario(listaSolicitudesUsuarios.getSelectedValue()); // se acepta ek usuario Seleccionado
					genPanel(panel);// se actualiza el panel con los usuarios
				}
			}
		});
		btnAñadir.setBounds(323, 81, 105, 27);
		panel.add(btnAñadir);
		
		
		JButton btnRechazar = new JButton("Rechazar");
		btnRechazar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {// si hacen click en el boton 
				if(listaSolicitudesUsuarios.getSelectedValue() != null) {// si hay algo seleccionado  
					GestorGeneral.getGestorGeneral().deleteUsuario(listaSolicitudesUsuarios.getSelectedValue()); // se elimina el usuario que se ha seleccionado
					genPanel(panel);// se actualiza el panel con los usuarios
				}
			}
		});
		btnRechazar.setBounds(323, 120, 105, 27);
		panel.add(btnRechazar);
		
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {// si hacen click en el boton 
				new Catalogo();// se crea una nueva instancia de Catalogo
				dispose();// se elimina la instancia de SolicitudUsuario
			}
		});
		btnVolver.setBounds(323, 157, 105, 27);
		panel.add(btnVolver);
		
		
		genPanel(panel);//forma el panel de los usuarios
		setVisible(true);
	}
	
	
	private void genPanel(JPanel panel) {
		List<String> u = GestorGeneral.getGestorGeneral().mostrarUsuariosNoAceptados();// obtiene los nombres de usuarios de los usuarios no haceptados
		
		listaSolicitudesUsuarios = new JList<>(u.toArray(new String[0]));
		listaSolicitudesUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listaSolicitudesUsuarios);
		scrollPane.setBounds(0, 23, 320, 220);
		
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
