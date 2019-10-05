package br.com.daniel.nicaragua.service;

import java.util.Optional;
import java.util.stream.Collectors;

import br.com.daniel.nicaragua.modelos.Laboratorio;
import br.com.daniel.nicaragua.modelos.ResponseModel;
import br.com.daniel.nicaragua.modelos.Status;
import br.com.daniel.nicaragua.repositorio.LaboratorioRepositorio;
import io.helidon.common.http.Http;
import io.helidon.webserver.Handler;
import io.helidon.webserver.HttpException;
import io.helidon.webserver.Routing.Rules;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class LaboratorioService implements Service {

    private final String ENDPOINT_LABORATORIO = "/laboratorios";

    private LaboratorioRepositorio laboratorioRepositorio;
    
    public LaboratorioService(LaboratorioRepositorio laboratorioRepositorio) {
         this.laboratorioRepositorio = laboratorioRepositorio;   
    }

    @Override
    public void update(Rules rules) {
        rules.get(ENDPOINT_LABORATORIO, this::getLaboratorios).get(ENDPOINT_LABORATORIO + "/{nombre}", this::getLaboratorio);
        rules.post(ENDPOINT_LABORATORIO, Handler.create(Laboratorio.class, (req, res, laboratorio) -> registrarseLaboratorio(res, laboratorio)));
        rules.put(ENDPOINT_LABORATORIO,  Handler.create(Laboratorio.class, (req, res, laboratorio) -> updateLaboratory(res, laboratorio)));
        rules.delete(ENDPOINT_LABORATORIO + "/{id}", this::deleteLaboratorio);
    }

    private void getLaboratorios(ServerRequest request, ServerResponse response) {
        response.send(laboratorioRepositorio.findAll()
                                            .stream()
                                             .filter(laboratorio -> laboratorio.getStatus().equals(Status.ATIVO))
                                             .collect(Collectors.toList()));
    }

    private void registrarseLaboratorio(ServerResponse response, Laboratorio lab) {
        laboratorioRepositorio.save(lab);
        response.send(new ResponseModel(response.status().code(), "Laboratorio guardado con éxito !"));
    }

    private void updateLaboratory(ServerResponse response, Laboratorio lab) {
        laboratorioRepositorio.save(lab);
        response.send(new ResponseModel(response.status().code(), "Laboratorio actualizado con éxito !"));
    }

    private void deleteLaboratorio(ServerRequest request, ServerResponse response) throws NumberFormatException {
        try {
            Laboratorio laboratorio = this.laboratorioRepositorio.findBy(Long.parseLong(request.path().param("id")));
            if (laboratorio != null) {
                laboratorio.setStatus(Status.INATIVO);
                this.laboratorioRepositorio.save(laboratorio);
                response.send(new ResponseModel(response.status().code(), "Laboratorio eliminado con éxito !"));
            } else {
                response.send(new ResponseModel(response.status().code(), "Laboratorio no existe !"));
            }
        } catch (NumberFormatException e) {
            response.send(new HttpException("Formato Invalido", Http.Status.INTERNAL_SERVER_ERROR_500));
        }
    }

    private void getLaboratorio(ServerRequest request, ServerResponse response) {

        Optional<Laboratorio> laboratorio = this.laboratorioRepositorio.findByNombre(request.path().param("nombre"));
        if (laboratorio.isPresent()) {
            response.send(laboratorio);
        } else {
            response.send(new ResponseModel(response.status().code(), "laboratorio no encontrado !"));
        }
    }
}