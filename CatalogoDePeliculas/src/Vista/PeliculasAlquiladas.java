
package Vista;

import javax.swing.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Modelo.GestorAlquiler;
import Modelo.GestorGeneral;
import Modelo.Pelicula;
import Modelo.Usuario;

public class PeliculasAlquiladas extends JFrame {
    private JList<String> listaPeliculas;
    private DefaultListModel<String> modeloLista;
    private JSONArray peliculasAlquiladas;

    public PeliculasAlquiladas() {
        

        setTitle("Películas Alquiladas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Modelo para la lista
        modeloLista = new DefaultListModel<>();
        listaPeliculas = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaPeliculas);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar las películas alquiladas
     
        Boolean vacio=cargarPeliculasAlquiladas();
        
     // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Botón para seleccionar la película
        JButton btnSeleccionar = new JButton("Seleccionar Película");
        panelBotones.add(btnSeleccionar);

        // Botón para volver al catálogo inicial
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);
        
        
        // Acción al pulsar el botón
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = listaPeliculas.getSelectedIndex();
                if (seleccion != -1) {
                   
                	JSONObject peliculaSeleccionada = peliculasAlquiladas.getJSONObject(seleccion);
                    abrirPanelPeliculaAPuntuar(peliculaSeleccionada);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PeliculasAlquiladas.this,
                            "Por favor, selecciona una película.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
     // Acción al pulsar el botón "Volver"
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Catalogo catalogo = new Catalogo();
    			dispose();
            }
        });
        
        if (vacio==true) {
        	setVisible(true);
        	
        }
        else {
        new Catalogo();
    	dispose();}
    }
    
    

    // Método para cargar las películas alquiladas del usuario
    private boolean cargarPeliculasAlquiladas() {
    	Boolean vacio=false;
    	JSONObject jsonPeliculas = GestorGeneral.getGestorGeneral().getPeliculasAlquiladasPorUsuario();
    	peliculasAlquiladas = jsonPeliculas.getJSONArray("peliculas");
        
        if (peliculasAlquiladas.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No has alquilado ninguna película.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	//peliculasAlquiladas = jsonPeliculas.getJSONArray("peliculas");
            for (int i = 0; i < peliculasAlquiladas.length(); i++) {
            	JSONObject pelicula = peliculasAlquiladas.getJSONObject(i);
                modeloLista.addElement("Pelicula: "  + pelicula.getString("titulo") + " Fecha : "  + pelicula.getString("fecha") +"");
                vacio=true;
                
            }
            return vacio;
        }
        return vacio;
    }

    // Método para abrir el panel de PeliculasAPuntuar
    private void abrirPanelPeliculaAPuntuar(JSONObject json) {
        PeliculasAPuntuar panelPuntuar = new PeliculasAPuntuar(json);
        panelPuntuar.setVisible(true);

    }

    
}
