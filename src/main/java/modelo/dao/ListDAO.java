/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.swing.JTextArea;
import modelo.vo.List;
import modelo.vo.Albums;
import modelo.vo.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Storm
 */
public class ListDAO {

    public void borrarAddAlbum(Session session, Albums a) throws Exception {
        List list;
        Query q = session.createQuery("FROM List l WHERE l.listPK.albumId = :albumId");
        q.setParameter("albumId", a.getId());

        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            list = (List) it.next();

            session.delete(list);
        }
    }

    public void borrarAddUsuario(Session session, Users u) throws Exception {
        List list;
        Query q = session.createQuery("FROM List l WHERE l.listPK.userId = :userId");
        q.setParameter("userId", u.getId());

        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            list = (List) it.next();

            session.delete(list);
        }
    }

    public void listarAlbums(Session session, Users u, String tipo, JTextArea txtListarArea) throws Exception {
        txtListarArea.setText("");
        List list;
        Query q = session.createQuery("FROM List l WHERE l.users.email = :email AND l.type = :tipo");
        q.setParameter("email", u.getEmail());
        q.setParameter("tipo", tipo);

        if (q.list().size() <= 0) {
            txtListarArea.setText("Este usuario no tiene ningun album en su lista de " + tipo);
            return;
        }

        Iterator it = q.list().iterator();

        while (it.hasNext()) {
            list = (List) it.next();

            if (tipo.equals("played")) {
                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = formatter.format(list.getDate());
                
                txtListarArea.append("ID Usuario: " + list.getListPK().getUserId() + ", ID Album: " + list.getListPK().getAlbumId()
                        + ", Review: " + list.getReview() + ", Rating: " + list.getRating() + ", Fecha: " + fecha + "\n");
            } else {
                txtListarArea.append("ID Usuario: " + list.getListPK().getUserId() + ", ID Album: " + list.getListPK().getAlbumId() + "\n");
            }
        }
    }
}
