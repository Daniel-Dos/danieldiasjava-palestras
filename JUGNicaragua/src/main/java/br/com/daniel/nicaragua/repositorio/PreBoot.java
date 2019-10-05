package br.com.daniel.nicaragua.repositorio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.resourceloader.InjectableResource;

import br.com.daniel.nicaragua.modelos.Examen;
import br.com.daniel.nicaragua.modelos.Laboratorio;
import br.com.daniel.nicaragua.modelos.Status;
import br.com.daniel.nicaragua.modelos.TipoExamen;

public class PreBoot {
    
    @Inject
    private LaboratorioRepositorio laboratorioRepositorio;
    
    @Inject
    private ExamenRepositorio examenRepositorio;

    @Inject
    @InjectableResource(location = "laboratorio.txt")
    private InputStream inputLaboratorio;
    
    @Inject
    @InjectableResource(location = "examenes.txt")
    private InputStream inputExamen;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try (BufferedReader laboratoriosInput = new BufferedReader(new InputStreamReader(inputLaboratorio));
             BufferedReader examenesInput = new BufferedReader(new InputStreamReader(inputExamen))) {

            laboratoriosInput.lines().forEach(laboratorio -> {
                String[] output = laboratorio.split("-");
                this.laboratorioRepositorio.save(new Laboratorio(output[0], output[1], Status.valueOf(output[2])));
            });
          
            examenesInput.lines().forEach(examen -> {
                String[] output = examen.split("-");

                Laboratorio lab1 = this.laboratorioRepositorio.findBy(Long.parseLong(output[3]));
                Laboratorio lab2 = this.laboratorioRepositorio.findBy(Long.parseLong(output[4]));
                List<Laboratorio> laboratorios = Arrays.asList(lab1,lab2);

                this.examenRepositorio.save(new Examen(output[0], TipoExamen.valueOf(output[1]), Status.valueOf(output[2]),laboratorios));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}