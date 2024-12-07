package Vista;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Modelo.GestorUsuarios;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class ModificarUsuarioA extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JComboBox<String> textAdmin;
	private JTextField textNombre;

	/**
	 * Create the frame.
	 */
	public ModificarUsuarioA(String pUsuario) {
		
		JSONObject info = GestorUsuarios.getGestorUsuarios().obtenerInfoAdministrador(pUsuario);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblInicioDeSesion = new JLabel("Modificar informacion");
		lblInicioDeSesion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblInicioDeSesion, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(87, 45, 58, 17);
		panel.add(lblNombre);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		
		textNombre = new JTextField(info.getString("Nombre"));
		textNombre.setBounds(154, 45, 105, 17);
		textNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textNombre.setColumns(10);
		
		panel.add(textNombre);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(96, 81, 49, 17);
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUsuario);
		
		textUsuario = new JTextField(info.getString("NombreUsuario"));
		textUsuario.setBounds(154, 81, 105, 17);
		textUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		textUsuario.setColumns(10);
		panel.add(textUsuario);
		
		JLabel lblContraseña = new JLabel("Administrador:");
		lblContraseña.setBounds(74, 119, 71, 17);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblContraseña);
		
		textAdmin = new JComboBox<>(new String[]{"true", "false"});
		textAdmin.setSelectedItem(info.getString("Administrador"));
		textAdmin.setBounds(154, 119, 105, 17);
		panel.add(textAdmin);
		

		JButton buttonVolver = new JButton("Volver");
		buttonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CuentasUsuarios cuentaUsuario = new CuentasUsuarios();
				dispose();
			}
		});
		buttonVolver.setBounds(261, 158, 82, 27);
		panel.add(buttonVolver);
		
		JButton buttonConfirmar = new JButton("Confirmar");
		buttonConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GestorUsuarios.getGestorUsuarios().modificarUsuariosAdmin( textNombre.getText(), textUsuario.getText(),(String) textAdmin.getSelectedItem())) {
					CuentasUsuarios cuentaUsuario = new CuentasUsuarios();
					dispose();
				}
			}
		});
		panel.add(buttonConfirmar);
		
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Esbozo de método generado automáticamente
		
	}
}