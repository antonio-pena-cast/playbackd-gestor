/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import BCrypt.BCrypt;
import controlador.factory.HibernateUtil;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.dao.AddDAO;
import modelo.dao.AlbumsDAO;
import modelo.dao.UsersDAO;
import modelo.vo.Users;
import org.hibernate.Session;
import vista.Principal;

/**
 *
 * @author acceso a datos
 */
public class controladorPrincipal {

    public static Session session;

    public static AddDAO addDAO;
    public static AlbumsDAO albDAO;
    public static UsersDAO useDAO;

    public static Principal ventana = new Principal();
    public static DefaultComboBoxModel<Users> modeloCombo = new DefaultComboBoxModel<>();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.getCmbUser().setModel(modeloCombo);
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
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese id");
                return;
            }

            String salt = BCrypt.gensalt(12); //Se establece el nivel de workload para la encriptacion
            String hashContrasenha = BCrypt.hashpw(ventana.getTxtContrasenha().getText(), salt); //Se encripta la contraseña con el workload establecido

            useDAO.insertarUsuario(session, ventana.getTxtNombre().getText(), ventana.getTxtEmail().getText(), hashContrasenha);

            JOptionPane.showMessageDialog(null, "Se ha insertado el usuario");
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
            
            ventana.getTxtNombre().setText(u.getName());
            ventana.getTxtEmail().setText(u.getEmail());
        } catch (Exception ex) {
            HibernateUtil.rollbackTx(session);
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
