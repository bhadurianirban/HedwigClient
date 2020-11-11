/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hedwig.cloud.client;

import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hedwig.cloud.dto.TenantDTO;
/**
 *
 * @author bhaduri
 */
public class TenantListClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = HedwigConstants.BASE_URL;

    public TenantListClient() {
//        client = javax.ws.rs.client.ClientBuilder.newClient();
//        webTarget = client.target(BASE_URI).path("tenantlist");
    }

    public List<TenantDTO> getTenantList(int productId) {
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setProductId(productId);
       
        
        client = javax.ws.rs.client.ClientBuilder.newClient();
        
        webTarget = client.target(BASE_URI).path("tenantlist").queryParam("productID",Integer.toString(productId));
         WebTarget resource = webTarget;
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        //List<TenantDTO> tenantDTOs = null;
        String tenantDTOJSON;
        try {
            Response response = ib.get();
            if (response.getStatus() != 200) {

                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            tenantDTOJSON = response.readEntity(String.class);
            TypeReference<List<TenantDTO>> mapType = new TypeReference<List<TenantDTO>>() {};
            List<TenantDTO> tenantDTOs;
            try {
                tenantDTOs = objectMapper.readValue(tenantDTOJSON, mapType);
                return tenantDTOs;
            } catch (IOException ex) {
                Logger.getLogger(TenantListClient.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            //tenantDTOs = response.readEntity(new GenericType<List<TenantDTO>>() {
            //});
            //tenantDTOs = response.readEntity(new GenericType<List<TenantDTO>>() {
            //});
            //tenantDTOs = response.readEntity(new GenericType<List<TenantDTO>>() {
            //});
            //tenantDTOs = response.readEntity(new GenericType<List<TenantDTO>>() {
            //});
            
        } catch (ProcessingException pe) {
            return null;
        }
        //tenantDTOs = response.readEntity(List<TenantDTO>.class);

    }
}
