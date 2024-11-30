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

	        getContentPane().add(new JLabel("Puntuación: 10 max - 1 min"));
	        txtPuntuacion = new JTextField();
	        getContentPane().add(txtPuntuacion);

	        btnValorar = new JButton("Valorar");
	        getContentPane().add(btnValorar);
	        btnValorar.addActionListener(getControler());
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
	   
	    
	    public void update(Observable o, Object arg) {
			// TODO Esbozo de método generado automáticamente
			
		}


//CONTROLER -------------------------------------------------------------------------------

		private Controler getControler() {
			if (miControler == null) {
				miControler = new Controler();
				
			}
			return miControler;
		}
		

		private class Controler implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}}
			
		
}

       

