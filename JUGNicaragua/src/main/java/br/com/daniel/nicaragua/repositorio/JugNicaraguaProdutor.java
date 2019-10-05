package br.com.daniel.nicaragua.repositorio;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JugNicaraguaProdutor {

    @Produces
    @ApplicationScoped
    public EntityManager createEntityManager() {
        EntityManagerFactory em = Persistence.createEntityManagerFactory("jug-nicaragua");
        return em.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager manager) {
        if (manager.isOpen()) {
            manager.close();
        }
    }
}