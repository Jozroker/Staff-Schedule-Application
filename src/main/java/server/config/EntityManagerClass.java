package server.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.TimeZone;

public class EntityManagerClass {

     private static EntityManagerFactory emf;

     private EntityManagerClass() {
     }

     public static EntityManager getInstance() {
          if (emf == null) {
               TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
               emf = Persistence.createEntityManagerFactory("root");
          }
          return emf.createEntityManager();
     }
}
