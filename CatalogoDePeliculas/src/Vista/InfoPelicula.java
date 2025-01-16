package Vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Modelo.GestorGeneral;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class InfoPelicula extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelValoraciones;

    public InfoPelicula(JSONObject json) {
    	
        setTitle("Detalles de la Película");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        // Panel superior con los datos de la película y el botón "Alquilar"
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout(10, 10));
        contentPane.add(panelSuperior, BorderLayout.NORTH);

        // Datos de la película
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(3, 1));
        
        JSONObject json2 = GestorGeneral.getGestorGeneral().infoPelicula(json);
        
        panelDatos.add(new JLabel("titulo: " + json2.getString("titulo")));
        panelDatos.add(new JLabel("Fecha: " + json2.getString("fecha")));
        panelDatos.add(new JLabel("Director: " + json2.getString("director")));
        panelSuperior.add(panelDatos, BorderLayout.CENTER);

        // Botón "Alquilar"
        JButton btnAlquilar = new JButton("Alquilar");
        btnAlquilar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	GestorGeneral.getGestorGeneral().alquilarPelicula(GestorGeneral.getGestorGeneral().obtenerUsuarioActual(), GestorGeneral.getGestorGeneral().buscarPelicula(json2.getString("titulo")));
               // realizarAlquiler(json.getString("titulo"),json.getString("fecha"));
            }
        });
        panelSuperior.add(btnAlquilar, BorderLayout.EAST);

        // Panel inferior con las valoraciones
        panelValoraciones = new JPanel();
        panelValoraciones.setLayout(new BoxLayout(panelValoraciones, BoxLayout.Y_AXIS));
        panelValoraciones.setBorder(new TitledBorder("Valoraciones"));

        // Añadir las valoraciones al panel
        if (json != null) {
        	
        	json=mostrarComentariosYPuntuaciones(json);
        	
            JSONArray comentarios = json.getJSONArray("comentarios");
            for (int i = 0; i < comentarios.length(); i++) {
                JSONObject comentario = comentarios.getJSONObject(i);
                String NombreUsuario = comentario.getString("nombreUsuario");
                int puntuacion = comentario.getInt("puntuacion");
                String textoComentario = comentario.getString("comentario");

                JLabel lblComentario = new JLabel("Usuario: " + NombreUsuario + " - Puntuación: " + puntuacion + " - Comenta lo siguiente: " + textoComentario);
                panelValoraciones.add(lblComentario);
            }
        }

        // Añadir el panel de valoraciones al scroll
        JScrollPane scrollPaneValoraciones = new JScrollPane(panelValoraciones);
        scrollPaneValoraciones.setPreferredSize(new Dimension(600, 400));
        contentPane.add(scrollPaneValoraciones, BorderLayout.CENTER);
        setVisible(true);
    }

//    // Método para realizar el alquiler
//    private void realizarAlquiler(Pelicula pelicula) {
//        try {
//            boolean exito = GestorAlquiler.getGestorAlquiler().alquilarPelicula(pelicula);
//            if (exito) {
//                JOptionPane.showMessageDialog(this, "Película alquilada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(this, "No se pudo alquilar la película.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error al realizar el alquiler.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    
    
    private JSONObject mostrarComentariosYPuntuaciones(JSONObject json) {
	    try {
	    	
	        JSONObject jsonData = GestorGeneral.getGestorGeneral().obtenerComentariosYPuntuaciones(json);
	        return jsonData;
	        // actualizarPanelComentarios(jsonData);
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al obtener los comentarios y puntuaciones", "Error", JOptionPane.ERROR_MESSAGE);
	        return null; 
	    }
	    
	}
    
    
}

