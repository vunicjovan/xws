package com.uns.ftn.agent.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.GetCatalogRequest;
import rs.ac.uns.ftn.catalog.GetCatalogResponse;

public class CatalogClient extends WebServiceGatewaySupport {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public GetCatalogResponse getCatalog() {
        logger.info("Getting catalog from microservices via soap");
        GetCatalogRequest request = new GetCatalogRequest();

        GetCatalogResponse response = (GetCatalogResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }
}
