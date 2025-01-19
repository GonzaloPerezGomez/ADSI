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
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import org.json.JSONObject;

public class ModificarUsuario extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textContraseña;
	private JTextField textNombre;
	
	/**
	 * Create the frame.
	 */
	public ModificarUsuario(String pUsuario) {

	JSONObject info = GestorGeneral.getGestorGeneral().obtenerInfoUsuario();
	
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
	lblNombre.setFocusable(false);
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
	
	JLabel lblContraseña = new JLabel("Contraseña:");
	lblContraseña.setBounds(74, 119, 71, 17);
	lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
	panel.add(lblContraseña);
	
	textContraseña = new JPasswordField(info.getString("Contraseña"));
	textContraseña.setHorizontalAlignment(SwingConstants.CENTER);
	textContraseña.setBounds(154, 119, 105, 17);
	panel.add(textContraseña);
	

	JButton buttonVolver = new JButton("Volver");
	buttonVolver.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {//si se pulsa el boton
			Catalogo catalogo = new Catalogo();// se crea una nueva instancia de la clase Catalogo
			dispose();// se elimina la iia de la clase ModinstancficarUsuario 
		}
	});
	
	buttonVolver.setBounds(262, 161, 82, 27);
	panel.add(buttonVolver);
	

	
	JButton buttonCorfirmar = new JButton("Confirmar");
	buttonCorfirmar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {// si se pulsa el boton
			if (GestorGeneral.getGestorGeneral().modificarUsuariosUsuario( textNombre.getText(), textUsuario.getText(), new String(textContraseña.getPassword()))) {// si se modifican los parametros
				ModificarUsuario modificarUsuario = new ModificarUsuario(textUsuario.getText());// se crea una nueva instancia de la clase ModificarUsuario
				dispose();// se elimina la iia de la clase ModinstancficarUsuario antigua
			}
			
		}
	});
	buttonCorfirmar.setBounds(154, 161, 93, 27);
	panel.add(buttonCorfirmar);
	
	setVisible(true);
}

}
