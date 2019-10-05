package br.com.daniel.nicaragua.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.daniel.nicaragua.modelos.Examen;
import br.com.daniel.nicaragua.modelos.Laboratorio;
import br.com.daniel.nicaragua.modelos.ResponseModel;
import br.com.daniel.nicaragua.modelos.Status;
import br.com.daniel.nicaragua.repositorio.ExamenRepositorio;
import br.com.daniel.nicaragua.repositorio.LaboratorioRepositorio;
import io.helidon.common.http.Http;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing.Rules;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class ExamenService implements Service {

    private final String ENDPOINT_EXAMEN= "/examenes";

    private LaboratorioRepositorio laboratorioRepositorio;
    private ExamenRepositorio examenRepositorio;
    
    public ExamenService(LaboratorioRepositorio laboratorioRepositorio, ExamenRepositorio examenRepositorio) {
         this.laboratorioRepositorio = laboratorioRepositorio; 
         this.examenRepositorio = examenRepositorio; 
    }

    @Override
    public void update(Rules rules) {
        rules.get(ENDPOINT_EXAMEN, this::getExamenes)
             .get(ENDPOINT_EXAMEN + "/{nombre}", this::getExamen);
        rules.post(ENDPOINT_EXAMEN, Handler.create(Examen.class, (req, res, exame) -> registerExamen(res, exame)));
        rules.put(ENDPOINT_EXAMEN, Handler.create(Examen.class, (req, res, exame) -> actualizacionExamen(res, exame)));
        rules.delete(ENDPOINT_EXAMEN + "/{id}", this::eliminarExamen);
    }

    private void getExamenes(ServerRequest request, ServerResponse response) {
        response.send(examenRepositorio.findAll()
                                      .stream()
                                      .filter(examen -> examen.getStatus().equals(Status.ATIVO))
                                      .collect(Collectors.toList()));
    }

    private void registerExamen(ServerResponse response, Examen examen) {

        List<Laboratorio> laboratorios = new ArrayList<>();

        examen.getLaboratorios().forEach(laboratorio -> {
            Laboratorio lab = laboratorioRepositorio.findBy(laboratorio.getId());
            laboratorios.add(lab);
        });

        examen.setLaboratorios(laboratorios);

        examenRepositorio.save(examen);
        response.send(new ResponseModel(response.status().code(), "Examen guardado con éxito !"));
    }

    private void actualizacionExamen(ServerResponse response, Examen examen) {

        examenRepositorio.save(examen);
        response.send(new ResponseModel(response.status().code(), "Examen actualizado con éxito !"));
    }

    private void eliminarExamen(ServerRequest request, ServerResponse response) {

        try {
            Examen examen = this.examenRepositorio.findBy(Long.parseLong(request.path().param("id")));
            if (examen != null) {
                examen.setStatus(Status.INATIVO);
                this.examenRepositorio.save(examen);
                response.send(new ResponseModel(response.status().code(), "Examen Eliminado exitosamente !"));
            } else {
                response.send(new ResponseModel(response.status().code(), "Examen no existe !"));
            }
        } catch (NumberFormatException e) {
            
            response.status(Http.Status.BAD_REQUEST_400);
            response.send(new ResponseModel(response.status().code(), e.getMessage()));
        }
    }

    private void getExamen(ServerRequest request, ServerResponse response) {
        Optional<Examen> examen= this.examenRepositorio.findByNombre(request.path().param("nombre"));
        if (examen.isPresent()) {
            response.send(examen);
        } else {
            response.status(Http.Status.NOT_FOUND_404);
            response.send(new ResponseModel(response.status().code(), "Examen no existe !"));
        }
    }
}