package Vista;
import java.awt.EventQueue;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

@SuppressWarnings("deprecation")
public class Registrarse extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textContraseña;
	private JTextField textNombre;

	/**
	 * Create the frame.
	 */
	public Registrarse() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInicioDeSesion = new JLabel("Registrarse");
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInicioDeSesion, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(87, 45, 58, 17);
		panel.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		textNombre = new JTextField();
		textNombre.setBounds(154, 45, 105, 17);
		panel.add(textNombre);
		textNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textNombre.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(96, 81, 49, 17);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUsuario);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(154, 81, 105, 17);
		textUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textUsuario.setColumns(10);
		panel.add(textUsuario);
		
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(74, 119, 71, 17);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblContraseña);
		
		textContraseña = new JPasswordField();
		textContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		textContraseña.setBounds(154, 119, 105, 17);
		panel.add(textContraseña);
		
		JButton buttonVolver = new JButton("Volver");
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InicioDeSesion inicioDeSesion = new InicioDeSesion();
				dispose();
			}
		});
		buttonVolver.setBounds(261, 158, 82, 27);
		panel.add(buttonVolver);
		
		JButton buttonConfirmar = new JButton("Confirmar");
		buttonConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(textNombre.getText());
				if (GestorGeneral.getGestorGeneral().addUsuario( textNombre.getText(), textUsuario.getText(), textContraseña.getPassword())) {
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
