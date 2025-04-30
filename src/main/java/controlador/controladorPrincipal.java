/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import org.hibernate.Session;
import vista.Principal;

/**
 *
 * @author acceso a datos
 */
public class controladorPrincipal {
 
 public static Session session; 
//declara los objetos DAO
    
 public static Principal ventana = new Principal();
  public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }

    public static void iniciaSession() {
        session=HibernateUtil.getSessionFactory().openSession();
        //crear los objetos DAO       
    }

    public static void cerrarSession() {
        session.close();       
    }
   
}
