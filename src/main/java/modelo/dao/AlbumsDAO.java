/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Date;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import modelo.vo.Albums;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Storm
 */
public class AlbumsDAO {

    public void cargarCombo(Session session, DefaultComboBoxModel<Albums> modeloComboAlbums) throws Exception {
        modeloComboAlbums.removeAllElements();

        Query q = session.createNamedQuery("Albums.findAll", Albums.class);
        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            Albums a = (Albums) it.next();
            modeloComboAlbums.addElement(a);
        }
    }

    public Albums buscarAlbum(Session session, String nombre) throws Exception {
        Query q = session.createNamedQuery("Albums.findByName", Albums.class);
        q.setParameter("name", nombre);

        return (Albums) q.uniqueResult();
    }

    public void insertarAlbum(Session session, String nombre, String autor, String genero, Date fecha, String encodeImagen) throws Exception {
        Albums a;
        
        if (encodeImagen == null) {
            a = new Albums(nombre, autor, genero, fecha);
        } else {
            a = new Albums(nombre, autor, genero, fecha, encodeImagen);
        }
        
        session.save(a);
    }

    public void modificarAlbum(Session session, Albums a, String nombre, String autor, String genero, Date fecha, String encodeImagen) throws Exception {
        a.setName(nombre);
        a.setAuthor(autor);
        a.setGenre(genero);
        a.setReleaseDate(fecha);
        a.setImage(encodeImagen);
        session.update(a);
    }

    public void borrarAlbum(Session session, Albums a) throws Exception {
        session.delete(a);
    }

}
