package br.com.daniel.nicaragua.repositorio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.daniel.nicaragua.modelos.Examen;
import br.com.daniel.nicaragua.modelos.Laboratorio;
import br.com.daniel.nicaragua.modelos.Status;
import br.com.daniel.nicaragua.modelos.TipoExamen;
import br.com.daniel.nicaragua.repositorio.ExamenRepositorio;
import br.com.daniel.nicaragua.repositorio.LaboratorioRepositorio;

@RunWith(CdiTestRunner.class)
public class TestExamen {

    @Inject
    LaboratorioRepositorio laboratorioRepositorio;

    @Inject
    ExamenRepositorio examenRepositorio;

    @Test
    public void testeCadastrarExame() throws Exception {

        List<Laboratorio> laboratorios = new ArrayList<>();

        Laboratorio lab = laboratorioRepositorio.findBy(1L);
        laboratorios.add(lab);

        examenRepositorio.save(new Examen("ojos", TipoExamen.ANALISE, Status.ATIVO, laboratorios));

        Optional<Examen> examen = examenRepositorio.findByNombre("Olhos");
        assertEquals("Olhos", examen.get().getNombre());
    }

    @Test
    public void testeBuscaTodosExames() {
        assertEquals(4, examenRepositorio.findAll().size());
    }

    @Test
    public void testeBuscaUmExamePorNome() {
        Optional<Examen> lab1 = examenRepositorio.findByNombre("Olhos");
        assertEquals("Olhos", lab1.get().getNombre());
    }

    @Test
    public void testeAtualizaExame() {

        Optional<Examen> examen1 = examenRepositorio.findByNombre("RaioX");

        Examen examen2 = new Examen();

        examen2.setId(examen1.get().getId());
        examen2.setNombre("cardiaco");
        examen2.setTipo(examen1.get().getTipo());
        examen2.setStatus(Status.ATIVO);
        examen2.setLaboratorios(null);

        examenRepositorio.save(examen2);

        Optional<Examen> examen3 = examenRepositorio.findByNombre("cardiaco");
        assertEquals(examen2.getNombre(), examen3.get().getNombre());
    }

    @Test
    public void testeRemoveUmExame() {
        Optional<Examen> examen = examenRepositorio.findByNombre("cardiaco");
        examen.get().setStatus(Status.INATIVO);

        examenRepositorio.save(examen.get());

        Optional<Examen> examen2 = examenRepositorio.findByNombre("cardiaco");
        assertEquals(Status.INATIVO, examen2.get().getStatus());
    }
}