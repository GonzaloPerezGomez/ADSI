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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;


@SuppressWarnings("deprecation")
public class CuentasUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CuentasUsuarios frame = new CuentasUsuarios();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CuentasUsuarios() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCuentasDeUsuarios = new JLabel("Cuentas de Usuarios");
		lblCuentasDeUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCuentasDeUsuarios, BorderLayout.NORTH);
		GestorGeneral gestGeneral = GestorGeneral.getGestorGeneral();
		List<String> listaUsuarios = gestGeneral.mostrarUsuarios();
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		contentPane.add(panel, BorderLayout.CENTER);
		for (String usuario : listaUsuarios ) {
			JPanel subPanel = new JPanel();
			subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
			JLabel lblUsuario = new JLabel(usuario);
		    JButton btnEditar = new JButton("Editar");
		    JButton btnEliminar = new JButton("Eliminar");
		    
		    subPanel.add(lblUsuario);
		    subPanel.add(btnEditar);
		    subPanel.add(btnEliminar);
		    
		    
		    btnEditar.addActionListener(e -> {
		    	ModificarUsuarioA modificarUsuario = new ModificarUsuarioA(usuario);
				dispose();
		    });
		    
		    
		    
		    btnEliminar.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		gestGeneral.deleteUsuario(usuario);
		    		System.out.println("Eliminar " + usuario);
		    		CuentasUsuarios cuentasUsuarios = new CuentasUsuarios();
					dispose();
		    	}
		    	
		    });
		    
		    panel.add(subPanel);
		    
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

}
