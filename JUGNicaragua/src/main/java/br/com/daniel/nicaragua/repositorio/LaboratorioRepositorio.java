package br.com.daniel.nicaragua.repositorio;

import java.util.Optional;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import br.com.daniel.nicaragua.modelos.Laboratorio;

@Repository
public interface LaboratorioRepositorio extends EntityRepository<Laboratorio, Long> {
    
    Optional<Laboratorio> findByNombre(String nome);
}
