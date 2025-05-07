/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import BCrypt.BCrypt;
import controlador.factory.HibernateUtil;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.dao.AddDAO;
import modelo.dao.AlbumsDAO;
import modelo.dao.UsersDAO;
import modelo.vo.Albums;
import modelo.vo.Users;
import org.hibernate.Session;
import vista.Principal;

/**
 *
 * @author acceso a datos
 */
public class controladorPrincipal {

    public static Session session;
    public static String imagePath;

    public static AddDAO addDAO;
    public static AlbumsDAO albDAO;
    public static UsersDAO useDAO;

    public static Principal ventana = new Principal();
    public static DefaultComboBoxModel<Users> modeloCombo = new DefaultComboBoxModel<>();
    public static DefaultComboBoxModel<Albums> modeloComboAlbums = new DefaultComboBoxModel<>();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCmbUser().setModel(modeloCombo);
        ventana.getCmbAlbums().setModel(modeloComboAlbums);
    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        addDAO = HibernateUtil.getAddDAO();
        albDAO = HibernateUtil.getAlbumsDAO();
        useDAO = HibernateUtil.getUsersDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void limpiarDatos() {
        ventana.getCmbUser().setSelectedIndex(0);
        ventana.getTxtNombre().setText("");
        ventana.getTxtContrasenha().setText("");
        ventana.getTxtEmail().setText("");
    }

    public static void anhadirUsuario() {
        if (ventana.getTxtNombre().getText().isEmpty() || ventana.getTxtEmail().getText().isEmpty() || ventana.getTxtContrasenha().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos por rellenar");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Users u = useDAO.buscarUsuario(session, ventana.getTxtEmail().getText());

            if (u != null) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese email");
                return;
            }

            String salt = BCrypt.gensalt(12); //Se establece el nivel de workload para la encriptacion
            String hashContrasenha = BCrypt.hashpw(ventana.getTxtContrasenha().getText(), salt); //Se encripta la contraseña con el workload establecido

            useDAO.insertarUsuario(session, ventana.getTxtNombre().getText(), ventana.getTxtEmail().getText(), hashContrasenha);

            JOptionPane.showMessageDialog(null, "Se ha insertado el usuario");
            HibernateUtil.commitTx(session);
            cargarCombo();
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatos();
        }
    }

    public static void modificarUsuario() {
        if (ventana.getTxtNombre().getText().isEmpty() || ventana.getTxtEmail().getText().isEmpty() || ventana.getTxtContrasenha().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Users u = (Users) modeloCombo.getSelectedItem();

            if (u == null) {
                JOptionPane.showMessageDialog(null, "Ese usuario no existe");
                return;
            }

            String salt = BCrypt.gensalt(12); //Se establece el nivel de workload para la encriptacion
            String hashContrasenha = BCrypt.hashpw(ventana.getTxtContrasenha().getText(), salt); //Se encripta la contraseña con el workload establecido

            useDAO.modificarUsuario(session, u, ventana.getTxtNombre().getText(), ventana.getTxtEmail().getText(), hashContrasenha);

            JOptionPane.showMessageDialog(null, "Se ha insertado el usuario");
            HibernateUtil.commitTx(session);
            cargarCombo();
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatos();
        }
    }

    public static void borrarUsuario() {
        try {
            HibernateUtil.beginTx(session);

            Users u = (Users) modeloCombo.getSelectedItem();

            addDAO.borrarAddUsuario(session, u);

            useDAO.borrarUsuario(session, u);

            JOptionPane.showMessageDialog(null, "Se ha borrado el usuario");
            HibernateUtil.commitTx(session);
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatos();
            cargarCombo();
        }
    }

    public static void cargarCombo() {
        try {
            HibernateUtil.beginTx(session);

            useDAO.cargarCombo(session, modeloCombo);

            HibernateUtil.commitTx(session);
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatos();
        }
    }

    public static void cargarUsuario() {
        try {
            Users u = (Users) modeloCombo.getSelectedItem();

            if (u == null) {
                return;
            }
            
            ventana.getTxtNombre().setText(u.getName());
            ventana.getTxtEmail().setText(u.getEmail());
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //--- ALBUMS ---
    public static void limpiarDatosAlbums() {
        ventana.getCmbAlbums().setSelectedIndex(1);
        ventana.getTxtAlbumNombre().setText("");
        ventana.getTxtAutor().setText("");
        ventana.getTxtGenero().setText("");
        ventana.getTxtFecha().setText("");
        ventana.getTxtImagen().setText("");
        ventana.getTxtImagen().setIcon(null);
    }

    public static void seleccionarImagen() {
        try {
            File file = null;
            JFileChooser fileChooser = ventana.getFileChooser();
            int seleccion = fileChooser.showOpenDialog(ventana);

            if (seleccion == fileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();

                if (file.isFile()) {
                    Path path = file.toPath();
                    String mimeType = Files.probeContentType(path);

                    if (mimeType.equals("image/png") || mimeType.equals("image/jpg") || mimeType.equals("image/jpeg")) {
                        file = fileChooser.getSelectedFile();

                        imagePath = file.getAbsolutePath();

                        String bytes = conseguirBytes(imagePath);
                        ventana.getTxtImagen().setIcon(conseguirImagen(bytes));
                    } else {
                        JOptionPane.showMessageDialog(null, "Ese archivo no es una imagen o el archivo de imagen no esta soportado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Se ha seleccionado un directorio en vez de un archivo");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cargarComboAlbums() {
        try {
            HibernateUtil.beginTx(session);

            albDAO.cargarCombo(session, modeloComboAlbums);

            HibernateUtil.commitTx(session);
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatosAlbums();
        }
    }

    public static void anhadirAlbum() {
        if (ventana.getTxtAlbumNombre().getText().isEmpty() || ventana.getTxtAutor().getText().isEmpty()
                || ventana.getTxtGenero().getText().isEmpty() || ventana.getTxtFecha().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos por rellenar");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Albums a = albDAO.buscarAlbum(session, ventana.getTxtAlbumNombre().getText());

            if (a != null) {
                JOptionPane.showMessageDialog(null, "Ya existe un album con ese nombre");
                return;
            }

            String encodeImagen = null;

            if (imagePath != null) {
                encodeImagen = conseguirBytes(imagePath);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate f = LocalDate.parse(ventana.getTxtFecha().getText(), formatter);
            Date fecha = (Date) java.sql.Date.valueOf(f);

            albDAO.insertarAlbum(session, ventana.getTxtAlbumNombre().getText(), ventana.getTxtAutor().getText(),
                    ventana.getTxtGenero().getText(), fecha, encodeImagen);

            JOptionPane.showMessageDialog(null, "Se ha insertado el album");
            HibernateUtil.commitTx(session);
            cargarComboAlbums();
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatosAlbums();
        }
    }

    public static void cargarAlbum() {
        try {
            Albums a = (Albums) modeloComboAlbums.getSelectedItem();
            
            if (a == null) {
                return;
            }

            Format formatter = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = formatter.format(a.getReleaseDate());

            ventana.getTxtAlbumNombre().setText(a.getName());
            ventana.getTxtAutor().setText(a.getAuthor());
            ventana.getTxtGenero().setText(a.getGenre());
            ventana.getTxtFecha().setText(fecha);

            if (a.getImage() == null) {
                ventana.getTxtImagen().setIcon(null);
                ventana.getTxtImagen().setText("");
            } else {
                ventana.getTxtImagen().setIcon(conseguirImagen(a.getImage()));
            }
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ImageIcon conseguirImagen(String imagen) {
        try {
            BufferedImage img = null;
            byte[] imageByte;
            imageByte = Base64.getDecoder().decode(imagen);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            img = ImageIO.read(bis);
            bis.close();

            Image resize = img.getScaledInstance(ventana.getTxtImagen().getWidth(), ventana.getTxtImagen().getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imagenAlbum = new ImageIcon(resize);

            return imagenAlbum;
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static String conseguirBytes(String path) {
        try {
            File imagen = new File(path);
            FileInputStream fis = new FileInputStream(imagen);
            byte[] bytes = new byte[(int) imagen.length()];
            fis.read(bytes);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void modificarAlbum() {
        if (ventana.getTxtAlbumNombre().getText().isEmpty() || ventana.getTxtAutor().getText().isEmpty()
                || ventana.getTxtGenero().getText().isEmpty() || ventana.getTxtFecha().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos por rellenar");
            return;
        }

        try {
            HibernateUtil.beginTx(session);

            Albums a = (Albums) modeloComboAlbums.getSelectedItem();
            String encodeImagen;

            if (imagePath == null) {
                encodeImagen = a.getImage();
            } else {
                encodeImagen = conseguirBytes(imagePath);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate f = LocalDate.parse(ventana.getTxtFecha().getText(), formatter);
            Date fecha = (Date) java.sql.Date.valueOf(f);

            albDAO.modificarAlbum(session, a, ventana.getTxtAlbumNombre().getText(), ventana.getTxtAutor().getText(),
                    ventana.getTxtGenero().getText(), fecha, encodeImagen);

            JOptionPane.showMessageDialog(null, "Se ha modificado el album");
            HibernateUtil.commitTx(session);
            cargarComboAlbums();
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatosAlbums();
        }
    }

    public static void borrarAlbum() {
        try {
            HibernateUtil.beginTx(session);

            Albums a = (Albums) modeloComboAlbums.getSelectedItem();

            addDAO.borrarAddAlbum(session, a);

            albDAO.borrarAlbum(session, a);

            JOptionPane.showMessageDialog(null, "Se ha borrado el album");
            HibernateUtil.commitTx(session);
        } catch (SQLException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            limpiarDatosAlbums();
            cargarComboAlbums();
        }
    }

}
