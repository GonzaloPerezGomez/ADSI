package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Modelo.GestorAlquiler;
import Modelo.Pelicula;
import Modelo.Usuario;

public class PeliculasAlquiladas extends JFrame {
    private Usuario usuarioActual;
    private JList<String> listaPeliculas;
    private DefaultListModel<String> modeloLista;
    private List<Pelicula> peliculasAlquiladas;

    public PeliculasAlquiladas(Usuario usuario) {
        this.usuarioActual = usuario;

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
                } else {
                    JOptionPane.showMessageDialog(PeliculasAlquiladas.this,
                            "Por favor, selecciona una película.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // Método para cargar las películas alquiladas del usuario
    private void cargarPeliculasAlquiladas() {
        peliculasAlquiladas = GestorAlquiler.getGestorAlquiler().getPeliculasAlquiladasPorUsuario(usuarioActual);

        if (peliculasAlquiladas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No has alquilado ninguna película.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Pelicula pelicula : peliculasAlquiladas) {
                modeloLista.addElement(pelicula.getTitulo() + " (" + pelicula.getFechaAlquiler() + ")");
            }
        }
    }

    // Método para abrir el panel de PeliculasAPuntuar
    private void abrirPanelPeliculaAPuntuar(Pelicula pelicula) {
        PeliculasAPuntuar panelPuntuar = new PeliculasAPuntuar();
        panelPuntuar.setVisible(true);

        // Cargar los datos de la película en el panel de valoración
        panelPuntuar.txtTitulo.setText(pelicula.getTitulo());
        panelPuntuar.txtAño.setText(String.valueOf(pelicula.getAño()));
    }

    // Método principal para probar el JFrame
    public static void main(String[] args) {
        // Simular un usuario
        Usuario usuario = new Usuario(1, "UsuarioEjemplo");

        // Mostrar el JFrame
        ListaPeliculasAlquiladas frame = new ListaPeliculasAlquiladas(usuario);
        frame.setVisible(true);
    }
}
