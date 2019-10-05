package br.com.daniel.nicaragua.repositorio;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import br.com.daniel.nicaragua.modelos.Examen;

@Repository
public interface ExamenRepositorio extends EntityRepository<Examen, Long> { 

    Optional<Examen> findByNombre(String nombre);
}
