package Vista;

import java.awt.EventQueue;

import javax.swing.*;

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

public class PeliculasAPuntuar extends JFrame implements Observer{ 
    private JTextField txtTitulo, txtAño, txtComentario, txtPuntuacion;
    private JButton btnValorar;
   // private Controler miControler;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String tituloPelicula=null;
					String fechaPelicula= null;
					PeliculasAPuntuar frame = new PeliculasAPuntuar(tituloPelicula,fechaPelicula);
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
	public PeliculasAPuntuar(String tituloPelicula,String fechaPelicula) {
		
		GestorPuntuacion.getGestorPuntuacion().addObserver(this);
		
		 setTitle("Valorar Película");
	        setSize(473, 433);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        getContentPane().setLayout(new GridLayout(5, 2));

	        getContentPane().add(new JLabel("Título:"));
	        txtTitulo = new JTextField();
	        txtTitulo.setText(tituloPelicula); // Establecer el título recibido
	        txtTitulo.setEditable(false); // No editable
	        getContentPane().add(txtTitulo);
	        
	        
	        getContentPane().add(new JLabel("Año:"));
	        txtAño = new JTextField();
	        txtAño.setText(fechaPelicula); // Establecer el título recibido
	        txtAño.setEditable(false); // No editable
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
						Integer puntuacion = getPuntuacion();
						if (puntuacion < 1 || puntuacion > 5) {
							JOptionPane.showMessageDialog(PeliculasAPuntuar.this, "La puntuación debe estar entre 1 y 5", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
			
						// Crear/actualizar la puntuación en el gestor
						String comentario = getComentario();
						String titulo = getTitulo();
						String fecha = getAño();
						//Usuario usuario = GestorUsuarios.getUsuarioActual();
			
						GestorGeneral.getGestorGeneral().ValorarPelicula(titulo, fecha, comentario,puntuacion);
						//GestorPuntuacion.getGestorPuntuacion().ValorarPelicula(usuario, pelicula, puntuacion, comentario);
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

	    public String getAño() {
	    	return txtAño.getText();
	    }

	    public String getComentario() {
	        return txtComentario.getText();
	    }

	    public Integer getPuntuacion() {
	        return Integer.parseInt(txtPuntuacion.getText());
	    }
	   
	    
		@Override
		public void update(Observable o, Object arg) {
			// Suponemos que el arg es un objeto de tipo Pelicula (del modelo)
			if (arg instanceof Pelicula) {
				Pelicula pelicula = (Pelicula) arg;
				txtTitulo.setText(pelicula.getTitulo());
				txtAño.setText(String.valueOf(pelicula.getFecha()));
				
				// Verificar si la película ya tiene valoración previa
				//Puntua puntuacion = GestorPuntuacion.getGestorPuntuacion().getPuntuacionPorUsuarioYPelicula(usuario, pelicula);
				//if (puntuacion != null) {
					//txtComentario.setText(puntuacion.getComentario());
					//txtPuntuacion.setText(String.valueOf(puntuacion.getPuntuacion()));
//				} else {
//					txtComentario.setText("");
//					txtPuntuacion.setText("");
//				}
			}
		}
	}
       

