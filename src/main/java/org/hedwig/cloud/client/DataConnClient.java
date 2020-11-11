/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hedwig.cloud.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hedwig.cloud.dto.DataConnDTO;
import org.hedwig.cloud.response.HedwigResponseCode;

/**
 *
 * @author bhaduri
 */
public class DataConnClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = HedwigConstants.BASE_URL;

    public DataConnDTO getDataConnParams(DataConnDTO dataConnDTO) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataConnDTOJSON = null;
        String respDataConnDTOJSON;
        webTarget = client.target(BASE_URI).path("dataconn");
        WebTarget resource = webTarget;
        try {
            dataConnDTOJSON = objectMapper.writeValueAsString(dataConnDTO);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DataConnClient.class.getName()).log(Level.SEVERE, null, ex);
            dataConnDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return dataConnDTO;
        }
        Invocation.Builder ib = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON);
        Response response = ib.post(javax.ws.rs.client.Entity.entity(dataConnDTOJSON, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            Logger.getLogger(DataConnClient.class.getName()).log(Level.SEVERE, "Service connection response"+Integer.toString(response.getStatus()));
            dataConnDTO.setResponseCode(HedwigResponseCode.SERVICE_CONNECTION_FAILURE);
            return dataConnDTO;
        }
        respDataConnDTOJSON = response.readEntity(String.class);

        try {
            dataConnDTO = objectMapper.readValue(respDataConnDTOJSON, DataConnDTO.class);
        } catch (IOException ex) {
            Logger.getLogger(DataConnClient.class.getName()).log(Level.SEVERE, null, ex);
            dataConnDTO.setResponseCode(HedwigResponseCode.JSON_FORMAT_PROBLEM);
            return dataConnDTO;
        }
        return dataConnDTO;

    }
}
