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
	private JList<String> listaUsuarios;
	private JScrollPane scrollPane;


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
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listaUsuarios.getSelectedValue() != null) {
					ModificarUsuarioA modificarUsuario = new ModificarUsuarioA(listaUsuarios.getSelectedValue());
					dispose();
				}
			}
		});
		
		btnEditar.setBounds(323, 81, 105, 27);
		panel.add(btnEditar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseClicked(MouseEvent e) {
		    	GestorGeneral.getGestorGeneral().deleteUsuario(listaUsuarios.getSelectedValue());
		    	genPanel(panel);
		    }
		}); 	
		
		btnBorrar.setBounds(323, 120, 105, 27);
		panel.add(btnBorrar);
		
		
		
		JButton btnVolver= new JButton("Volver");
			btnVolver.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Catalogo();
				dispose();
			}
		});
		btnVolver.setBounds(323, 157, 105, 27);
		panel.add(btnVolver);
		
		
	
	
		
		genPanel(panel);
		setVisible(true);
	}

	private void genPanel(JPanel panel) {
		List<String> u = GestorGeneral.getGestorGeneral().mostrarUsuarios();
		
		listaUsuarios = new JList<>(u.toArray(new String[0]));
		listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane = new JScrollPane(listaUsuarios);
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