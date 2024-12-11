package Vista;

import java.awt.EventQueue;

import javax.swing.*;

import Modelo.GestorPuntuacion;
import Modelo.GestorUsuarios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class PeliculasAPuntuar extends JFrame implements Observer{ 
    private JTextField txtTitulo, txtAño, txtComentario, txtPuntuacion;
    private JButton btnValorar;
    private Controler miControler;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PeliculasAPuntuar frame = new PeliculasAPuntuar();
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
	public PeliculasAPuntuar() {
		
		GestorPuntuacion.getGestorPuntuacion().addObserver(this);
		
		 setTitle("Valorar Película");
	        setSize(473, 433);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        getContentPane().setLayout(new GridLayout(5, 2));

	        getContentPane().add(new JLabel("Título:"));
	        txtTitulo = new JTextField();
	        getContentPane().add(txtTitulo);

	        getContentPane().add(new JLabel("Año:"));
	        txtAño = new JTextField();
	        getContentPane().add(txtAño);

	        getContentPane().add(new JLabel("Comentario:"));
	        txtComentario = new JTextField();
	        getContentPane().add(txtComentario);

	        getContentPane().add(new JLabel("Puntuación: 5 max - 1 min"));
	        txtPuntuacion = new JTextField();
	        getContentPane().add(txtPuntuacion);

	        btnValorar = new JButton("Valorar");
	        getContentPane().add(btnValorar);
	        btnValorar.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					try {
						// Validar los campos
						int puntuacion = getPuntuacion();
						if (puntuacion < 1 || puntuacion > 5) {
							JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "La puntuación debe estar entre 1 y 5", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
			
						// Crear/actualizar la puntuación en el gestor
						String comentario = getComentario();
						Pelicula pelicula = GestorPuntuacion.getGestorPuntuacion().getPeliculaActual();
						Usuario usuario = GestorUsuarios.getUsuarioActual();
			
						GestorPuntuacion.getGestorPuntuacion().guardarPuntuacion(usuario, pelicula, puntuacion, comentario);
						JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "¡Puntuación guardada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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

	    public int getAño() {
	        return Integer.parseInt(txtAño.getText());
	    }

	    public String getComentario() {
	        return txtComentario.getText();
	    }

	    public int getPuntuacion() {
	        return Integer.parseInt(txtPuntuacion.getText());
	    }
	   
	    
		@Override
		public void update(Observable o, Object arg) {
			// Suponemos que el arg es un objeto de tipo Pelicula (del modelo)
			if (arg instanceof Pelicula) {
				Pelicula pelicula = (Pelicula) arg;
				txtTitulo.setText(pelicula.getTitulo());
				txtAño.setText(String.valueOf(pelicula.getAño()));
				
				// Verificar si la película ya tiene valoración previa
				Puntuacion puntuacion = GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(GestorUsuarios.getUsuarioActual(), pelicula);
				if (puntuacion != null) {
					txtComentario.setText(puntuacion.getComentario());
					txtPuntuacion.setText(String.valueOf(puntuacion.getPuntuacion()));
				} else {
					txtComentario.setText("");
					txtPuntuacion.setText("");
				}
			}
		}
	}
	
		

		


       

