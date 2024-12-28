package Vista;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.GestorGeneral;
import Modelo.GestorUsuarios;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class InicioDeSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new InicioDeSesion();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InicioDeSesion() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInicioDeSesion = new JLabel("Inicio de Sesión");
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInicioDeSesion, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(96, 81, 49, 17);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUsuario);
		
		textField = new JTextField();
		textField.setBounds(154, 81, 105, 17);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		panel.add(textField);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(74, 119, 71, 17);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblContraseña);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(154, 119, 105, 17);
		panel.add(passwordField);
		
		JLabel lblNoRegistro = new JLabel("No tienes cuenta? Registrate");
		lblNoRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Registrarse frame = new Registrarse();
				dispose();
			}
		});
		contentPane.add(lblNoRegistro, BorderLayout.SOUTH);
		
		JButton buttonConfirmar = new JButton("Confirmar");
		buttonConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( GestorGeneral.getGestorGeneral().seInicia(textField.getText(), passwordField.getPassword())) {
					Catalogo frame = new Catalogo();
					dispose();
				};
				
				
			}
		});
		buttonConfirmar.setBounds(154, 158, 95, 27);
		panel.add(buttonConfirmar);
		
		setVisible(true);
	}

}
