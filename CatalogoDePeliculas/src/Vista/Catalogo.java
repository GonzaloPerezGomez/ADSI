package Vista;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

@SuppressWarnings("deprecation")
public class Catalogo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textBuscador;
	private JScrollPane scrollPane;
	private ArrayList<Pelicula> info;
	private JList<Pelicula>listPeliculas;
	private JPanel panelComentarios;

	public Catalogo() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCatalogo = new JLabel("Catalogo");
		lblCatalogo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCatalogo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textBuscador = new JTextField();
		textBuscador.setBounds(0, 12, 114, 21);
		panel.add(textBuscador);
		textBuscador.setColumns(10);
        
        
        
		JLabel lblBuscar = new JLabel("Buscar");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textBuscador.getText().trim().isEmpty()) { //si no se ha introducido nada o espacios
					JOptionPane.showMessageDialog(null, "Introduce un título");
				}
				else {
				JSONArray p = GestorGeneral.getGestorGeneral().buscarPeliculas(textBuscador.getText());
				if (p.isEmpty()) {JOptionPane.showMessageDialog(null, "Película no encontrada");}
				List<Pelicula> listaPeliculas = new ArrayList<>();
		        for (int i = 0; i < p.length(); i++) {
		            JSONObject peliculaJSON = p.getJSONObject(i); 
		            Pelicula pelicula = new Pelicula(peliculaJSON.getString("titulo"), peliculaJSON.getString("director"), peliculaJSON.getString("fecha")); 
		            listaPeliculas.add(pelicula);
		        }
		        listPeliculas = new JList<>(listaPeliculas.toArray(new Pelicula[listaPeliculas.size()]));
		        listPeliculas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
				scrollPane = new JScrollPane(listPeliculas);
				scrollPane.setBounds(0, 55, 320, 220);
				
				for (Component comp : panel.getComponents()) {
		            if (comp instanceof JScrollPane) {
		                panel.remove(comp);
		            }
		        }
				listPeliculas.addListSelectionListener(new ListSelectionListener(){
					@Override
					public void valueChanged(ListSelectionEvent e) {
						
						if(e.getValueIsAdjusting()) {
							int i = listPeliculas.getSelectedIndex();
							if(i!= -1) {
								JSONObject json = new JSONObject();	
						        json.put("titulo", listPeliculas.getModel().getElementAt(i).getTitulo());
						      
							
								
								new InfoPelicula(json);
								dispose();
								 
					             
							}
						}
					}
				});
		        panel.add(scrollPane);
		        
		        panel.revalidate();
		        panel.repaint();
				}
			}
			     
		});
		
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setBounds(126, 12, 57, 21);
		panel.add(lblBuscar);
		
		JLabel btnAñadirUnaPelicula = new JLabel("Añadir pelicula");
		btnAñadirUnaPelicula.setHorizontalAlignment(SwingConstants.CENTER);
		btnAñadirUnaPelicula.setBounds(199, 12, 125, 21);
		btnAñadirUnaPelicula.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SolicitarPelicula();
				dispose();
			}
		});
		panel.add(btnAñadirUnaPelicula);
		JMenuBar mB = genMenu(GestorGeneral.getGestorGeneral().getRolSesion());
		this.setJMenuBar(mB);
		setVisible(true);
		
		////////boton valorar ordenar
		 
		    JButton btnActualizar = new JButton("Ordenar por puntuacion");
		    btnActualizar.setBounds(320, 12, 200, 21);
		    panel.add(btnActualizar);
		    btnActualizar.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        actualizarCatalogo();
		        }
		    });
		
	}
	
	private JMenuBar genMenu(boolean esAdmin) {
		JMenuBar mB = new JMenuBar();
		
		JMenu menu = new JMenu("Opciones");
		
		JMenuItem op1;
		JMenuItem op2;
		JMenuItem op3;
		JMenuItem op4;
		JMenuItem op5;
		
		if(esAdmin) {
		op1 = new JMenuItem("Solicitudes de Usuarios");
	        op2 = new JMenuItem("Solicitudes de Peliculas");
	        op3 = new JMenuItem("Cuentas de Usuarios");
		op4 = new JMenuItem("Reseñas");
	        op5 = new JMenuItem("Cerrar Sesion");
			
			op1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new SolicitudesUsuarios();
					dispose();
				}
			});
			
			op2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new SolicitudesPeliculas();
					dispose();
				}
			});
			
			op3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new CuentasUsuarios();
					dispose();
				}
			});

			op4.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new PeliculasAlquiladas(); //para que pueda elegir la pelicula que ya alquilo para reseñar					
					dispose();
				}
			});
			
			op5.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					GestorGeneral.getGestorGeneral().cerrarSesion();
					new InicioDeSesion();
					dispose();
				}
			});
			menu.add(op1);
     			menu.add(op2);
        		menu.add(op3);
       			menu.add(op4);
			menu.add(op5);
		}else {
		op1 = new JMenuItem("Modificar Datos");
	        op2 = new JMenuItem("Reseñas");
	        op3 = new JMenuItem("Cerrar Sesion");
			
			op1.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new ModificarUsuario(GestorGeneral.getGestorGeneral().obtenerUsuario());
					dispose();
				}
			});
			
			op2.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					new PeliculasAlquiladas(); //para que pueda elegir la pelicula que ya alquilo para reseñar					
					dispose();
				}
			});
			
			op3.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					GestorGeneral.getGestorGeneral().cerrarSesion();;
					new InicioDeSesion();
					dispose();
				}
			});
			menu.add(op1);
			menu.add(op2);
			menu.add(op3);
		}
		
		mB.add(menu);
		
		return mB;
	}
	
	private void actualizarCatalogo() {   ///para boton de enseñar medias
	    try {
	        JSONObject jsonData = GestorGeneral.getGestorGeneral().obtenerPeliculasOrdenadasPorPuntuacionMedia();
	        if (jsonData == null || jsonData.isEmpty())  {
	        	JOptionPane.showMessageDialog(null,"No existen todavia valoraciones");
	        }
	        else {
	        // Actualizar la interfaz con la lista ordenada
	        actualizarListaPeliculas(jsonData);}
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al actualizar el catálogo", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void actualizarListaPeliculas(JSONObject jsonData) {   ///para boton de enseñar medias
	    // Limpia la lista actual de películas
	    contentPane.removeAll();

	    JLabel lblCatalogo = new JLabel("Catalogo");
	    lblCatalogo.setHorizontalAlignment(SwingConstants.CENTER);
	    contentPane.add(lblCatalogo, BorderLayout.NORTH);

	    JPanel panel = new JPanel();
	    contentPane.add(panel, BorderLayout.CENTER);
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    JSONArray peliculas = jsonData.getJSONArray("peliculas");
	    for (int i = 0; i < peliculas.length(); i++) {
	        JSONObject pelicula = peliculas.getJSONObject(i);
	        String titulo = pelicula.getString("titulo");
	        String fecha = pelicula.getString("fecha");
	        double puntuacionMedia = pelicula.getDouble("puntuacionMedia");

	        JLabel lblPelicula = new JLabel(titulo + " (" + fecha + ")     - Puntuación media: " + puntuacionMedia);
	        panel.add(lblPelicula);
	    }
	 // Botón "volver"
        JButton btnVolver = new JButton("Volver al Catalogo");
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new Catalogo();
               dispose();
            }});
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new FlowLayout(FlowLayout.LEFT));  // Alinea el botón a la izquierda dentro del panel
        panelBoton.add(btnVolver);
        contentPane.add(panelBoton, BorderLayout.SOUTH);

	    contentPane.revalidate();
	    contentPane.repaint();
	}
	

	
}
