/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hedwig.cloud.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.hedwig.cloud.dto.HedwigConstants;
import org.hedwig.cloud.dto.ProductDTO;

/**
 *
 * @author dgrfv
 */
public class ProductListClient {
    private WebTarget webTarget;
    private Client client;
    private static String CONNECTION_URL;

//    public ProductListClient() {
//        CONNECTION_URL = HedwigConstants.createConnectionUrl();
//    }
    public ProductListClient(String server,String serverPort) {
        CONNECTION_URL = HedwigConstants.createConnectionUrl(server,serverPort);
    }
    
    public List<ProductDTO> getProductList() {
       
       
        
        client = javax.ws.rs.client.ClientBuilder.newClient();
        
        webTarget = client.target(CONNECTION_URL).path("productlist");
         WebTarget resource = webTarget;
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        
        String productDTOListJSON;
        try {
            Response response = ib.get();
            if (response.getStatus() != 200) {

                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            productDTOListJSON = response.readEntity(String.class);
            TypeReference<List<ProductDTO>> mapType = new TypeReference<List<ProductDTO>>() {};
            List<ProductDTO> ProductDTOs;
            try {
                ProductDTOs = objectMapper.readValue(productDTOListJSON, mapType);
                return ProductDTOs;
            } catch (IOException ex) {
                Logger.getLogger(TenantListClient.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            //tenantDTOs = response.readEntity(new GenericType<List<TenantDTO>>() {
            //});
            
        } catch (ProcessingException pe) {
            return null;
        }
        //tenantDTOs = response.readEntity(List<TenantDTO>.class);

    }
}
