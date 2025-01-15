
package Vista;

import javax.swing.*;

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
    private Usuario usuarioActual;
    private JList<String> listaPeliculas;
    private DefaultListModel<String> modeloLista;
    private List<Pelicula> peliculasAlquiladas;

    public PeliculasAlquiladas() {
        this.usuarioActual = GestorGeneral.getGestorGeneral().obtenerUsuarioActual();
        this.peliculasAlquiladas = new ArrayList<Pelicula>();

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
     
        cargarPeliculasAlquiladas();

        // Botón para seleccionar la película
        JButton btnSeleccionar = new JButton("Seleccionar Película");
        add(btnSeleccionar, BorderLayout.SOUTH);

        // Acción al pulsar el botón
        btnSeleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = listaPeliculas.getSelectedIndex();
                if (seleccion != -1) {
                    Pelicula peliculaSeleccionada = peliculasAlquiladas.get(seleccion);
                    abrirPanelPeliculaAPuntuar(peliculaSeleccionada);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PeliculasAlquiladas.this,
                            "Por favor, selecciona una película.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        setVisible(true);
    }

    // Método para cargar las películas alquiladas del usuario
    private void cargarPeliculasAlquiladas() {
        this.peliculasAlquiladas = GestorGeneral.getGestorGeneral().getPeliculasAlquiladasPorUsuario(usuarioActual);
      
        ///pa probar cosos /////////////////////
        Pelicula peli = new Pelicula("hola","Manolo","1000 01 01");
        this.peliculasAlquiladas.add(peli);
        /////////////////////////////////

        if (peliculasAlquiladas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No has alquilado ninguna película.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Pelicula pelicula : peliculasAlquiladas) {
                modeloLista.addElement(pelicula.getTitulo() + " ) ");
            }
        }
    }

    // Método para abrir el panel de PeliculasAPuntuar
    private void abrirPanelPeliculaAPuntuar(Pelicula pelicula) {
    	String fechaa = "2000-01-01";
    	JSONObject json = new JSONObject();
        json.put("titulo", pelicula.getTitulo());
        json.put("fecha", fechaa); //////////// falla la fecha

    	
        PeliculasAPuntuar panelPuntuar = new PeliculasAPuntuar(json);
        panelPuntuar.setVisible(true);

    }

    
}
