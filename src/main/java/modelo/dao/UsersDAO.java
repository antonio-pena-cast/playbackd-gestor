/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import modelo.vo.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Storm
 */
public class UsersDAO {

    public Users buscarUsuario(Session session, String email) throws Exception {
        Query q = session.createNamedQuery("Users.findByEmail", Users.class);
        q.setParameter("email", email);
        
        return (Users) q.uniqueResult();
    }

    public void insertarUsuario(Session session, String nombre, String email, String contrasenha) throws Exception {
        Users u = new Users(nombre, email, contrasenha);
        session.save(u);
    }

    public Users buscarUsuarioID(Session session, long userId) throws Exception {
        return session.get(Users.class, userId);
    }

    public void modificarUsuario(Session session, Users u, String nombre, String email, String contrasenha) throws Exception {
        u.setName(nombre);
        u.setEmail(email);
        u.setPassword(contrasenha);
        session.update(u);
    }

    public void cargarCombo(Session session, DefaultComboBoxModel<Users> modeloCombo) throws Exception {
        modeloCombo.removeAllElements();
        
        Query q = session.createNamedQuery("Users.findAll", Users.class);
        Iterator it = q.list().iterator();
        
        while (it.hasNext()) {
            Users u = (Users) it.next();
            modeloCombo.addElement(u);
        }
    }

}
