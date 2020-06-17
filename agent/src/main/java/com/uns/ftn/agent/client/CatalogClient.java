package com.uns.ftn.agent.client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import rs.ac.uns.ftn.catalog.GetCatalogRequest;
import rs.ac.uns.ftn.catalog.GetCatalogResponse;

public class CatalogClient extends WebServiceGatewaySupport {

    public GetCatalogResponse getCatalog() {
        GetCatalogRequest request = new GetCatalogRequest();

        GetCatalogResponse response = (GetCatalogResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);

        return response;
    }
}
