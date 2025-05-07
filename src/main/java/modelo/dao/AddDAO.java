/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import modelo.vo.List;
import modelo.vo.Albums;
import modelo.vo.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author Storm
 */
public class AddDAO {

    public void borrarAddAlbum(Session session, Albums a) throws Exception {
        List list;
        Query q = session.createQuery("FROM List l WHERE l.listPK.albumId = :albumId");
        q.setParameter("albumId", a.getId());
        
        Iterator it = q.list().iterator();
        
        while (it.hasNext()){
            list = (List) it.next();
            
            session.delete(list);
        }
    }

    public void borrarAddUsuario(Session session, Users u) throws Exception {
        List add;
        Query q = session.createQuery("FROM List l WHERE l.listPK.userId = :userId");
        q.setParameter("userId", u.getId());
        
        Iterator it = q.list().iterator();
        
        while (it.hasNext()){
            add = (List) it.next();
            
            session.delete(add);
        }
    }
    
}
