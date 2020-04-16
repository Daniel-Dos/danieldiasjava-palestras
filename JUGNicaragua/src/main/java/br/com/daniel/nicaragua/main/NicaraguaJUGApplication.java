package br.com.daniel.nicaragua.main;

import javax.enterprise.context.ApplicationScoped;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.apache.deltaspike.cdise.api.ContextControl;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import br.com.daniel.nicaragua.repositorio.ExamenRepositorio;
import br.com.daniel.nicaragua.repositorio.LaboratorioRepositorio;
import br.com.daniel.nicaragua.repositorio.PreBoot;
import br.com.daniel.nicaragua.service.ExamenService;
import br.com.daniel.nicaragua.service.LaboratorioService;
import io.helidon.media.jsonb.server.JsonBindingSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

public class NicaraguaJUGApplication {
    public static void main(String[] args) {

        CdiContainer cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();

        ContextControl contextControl = cdiContainer.getContextControl();
        contextControl.startContext(ApplicationScoped.class);

        // pre-boot
        BeanProvider.getContextualReference(PreBoot.class, false);

        LaboratorioRepositorio laboratorioRepositorio = BeanProvider.getContextualReference(LaboratorioRepositorio.class, false);

        ExamenRepositorio examenRepositorio = BeanProvider.getContextualReference(ExamenRepositorio.class, false);

        ServerConfiguration configuration = ServerConfiguration.builder().port(Integer.parseInt("8080")).build();

        WebServer.create(configuration,Routing.builder()
                 .register(JsonBindingSupport.create())
                 .register(new LaboratorioService(laboratorioRepositorio), new ExamenService(laboratorioRepositorio,examenRepositorio)))
                 .start();           
    }
}