package br.com.daniel.nicaragua.repositorio;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.daniel.nicaragua.modelos.Laboratorio;
import br.com.daniel.nicaragua.modelos.Status;
import br.com.daniel.nicaragua.repositorio.LaboratorioRepositorio;

@RunWith(CdiTestRunner.class)
public class TestLaboratorio {

    @Inject
    LaboratorioRepositorio laboratorioRepositorio;

    @Test
    public void testeCadastraLaboratorio() throws Exception {

        Laboratorio lab1 = new Laboratorio("Teste1", "Teste1", Status.ATIVO);
        laboratorioRepositorio.save(lab1);
        Optional<Laboratorio> lab2 = laboratorioRepositorio.findByNombre("Teste1");
        
        assertEquals(lab1.getNombre(), lab2.get().getNombre());
        assertEquals(lab1.getDireccion(), lab2.get().getDireccion());
        assertEquals(lab1.getStatus(), lab2.get().getStatus());
    }

    @Test
    public void testeBuscaTodosLaboratorios() {
        assertEquals(7, laboratorioRepositorio.findAll().size());
    }

    @Test
    public void testeBuscaUmLaboratorioPorNome() {
        Optional<Laboratorio> lab1 = laboratorioRepositorio.findByNombre("Clinica Sao Pedro");
        assertEquals("Clinica Sao Pedro",lab1.get().getNombre());
    }

    @Test
    public void testeAtualizaLaboratorio() {
        Optional<Laboratorio> lab1 = laboratorioRepositorio.findByNombre("Clinica Sao Pedro");
        
        Laboratorio lab2 = new Laboratorio();

        lab2.setId(lab1.get().getId());
        lab2.setNombre("Clinica sao jorge");
        lab2.setDireccion(lab1.get().getDireccion());
        lab2.setStatus(lab1.get().getStatus());
        
        laboratorioRepositorio.save(lab2);
        Optional<Laboratorio> lab3 = laboratorioRepositorio.findByNombre("Clinica sao jorge");
        assertEquals(lab2.getNombre(),lab3.get().getNombre());
    }

    @Test
    public void testeRemoveUmLaboratorio() {
        Optional<Laboratorio> lab1 = laboratorioRepositorio.findByNombre("Clinica sao jorge");
        lab1.get().setStatus(Status.INATIVO);
        
        laboratorioRepositorio.save(lab1.get());
        
        Optional<Laboratorio> lab2 = laboratorioRepositorio.findByNombre("Clinica sao jorge");
        assertEquals(Status.INATIVO,lab2.get().getStatus());
    }
}