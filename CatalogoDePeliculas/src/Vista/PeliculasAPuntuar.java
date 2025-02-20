package Vista;

import java.awt.EventQueue;

import javax.swing.*;

import org.json.JSONObject;

import Modelo.GestorGeneral;
import Modelo.GestorPuntuacion;
import Modelo.GestorUsuarios;
import Modelo.Pelicula;
import Modelo.Puntua;
import Modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class PeliculasAPuntuar extends JFrame { 
    private JTextField txtTitulo, txtAño, txtComentario, txtPuntuacion;
    private JButton btnValorar;
   

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JSONObject jsonData= null;
					PeliculasAPuntuar frame = new PeliculasAPuntuar(jsonData);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PeliculasAPuntuar(JSONObject json) {
		
		
		///primero comprueba si este usuario ya tenia una puntuacion ya en esa pelicula
        JSONObject puntu= GestorGeneral.getGestorGeneral().revisarPuntuacionexistente(json);
		
		 setTitle("Valorar Película");
	        setSize(473, 433);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        getContentPane().setLayout(new GridLayout(5, 2));

	        getContentPane().add(new JLabel("Título:"));
	        txtTitulo = new JTextField();
	        txtTitulo.setText(json.getString("titulo")); // Establecer el título recibido
	        txtTitulo.setEditable(false); // No editable
	        getContentPane().add(txtTitulo);
	        
	        
	        getContentPane().add(new JLabel("Año:"));
	        txtAño = new JTextField();
	        txtAño.setText(json.getString("fecha")); // Establecer el título recibido
	        txtAño.setEditable(false); // No editable
	        getContentPane().add(txtAño);

	        getContentPane().add(new JLabel("Comentario:"));
	        txtComentario = new JTextField();
	        if (puntu != null && puntu.has("comentario")) {
	            txtComentario.setText(puntu.getString("comentario"));
	        }
	        getContentPane().add(txtComentario);

	        getContentPane().add(new JLabel("Puntuación: 5 max - 1 min"));
	        txtPuntuacion = new JTextField();
	        if (puntu != null && puntu.has("puntuacion")) {
	            txtPuntuacion.setText(Integer.toString(puntu.getInt("puntuacion")));
	        }
	        getContentPane().add(txtPuntuacion);

	        btnValorar = new JButton("Valorar");
	        getContentPane().add(btnValorar);
	        btnValorar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {
						// Validar los campos
						Integer puntuacion = getPuntuacion();
						if (puntuacion < 1 || puntuacion > 5) {
							JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "La puntuación debe estar entre 1 y 5", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
			
						// Crear/actualizar la puntuación en el gestor
						String comentario = getComentario();
						String titulo = getTitulo();
						String fecha = getAño();
						
						
						JSONObject peliculaData = new JSONObject();
						peliculaData.put("titulo", titulo);
						peliculaData.put("fecha", fecha);
						peliculaData.put("comentario", comentario);
						peliculaData.put("puntuacion", puntuacion);
						
						GestorGeneral.getGestorGeneral().ValorarPelicula(peliculaData);
						JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "¡Puntuación guardada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						 dispose();
						 new Catalogo();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "Debe ingresar una puntuación válida.", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "Ocurrió un error al guardar la puntuación: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
	    }

	    public String getTitulo() {
	        return txtTitulo.getText();
	    }

	    public String getAño() {
	    	return txtAño.getText();
	    }

	    public String getComentario() {
	        return txtComentario.getText();
	    }

	    public Integer getPuntuacion() {
	        return Integer.parseInt(txtPuntuacion.getText());
	    }
	   
	    
	
			
		}
	
       

