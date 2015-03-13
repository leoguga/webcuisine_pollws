/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webcuisine.service;

import com.webcuisine.entity.Poll;
import com.webcuisine.session.PollFacade;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.xml.ws.WebServiceException;
import sun.java2d.d3d.D3DRenderQueue;

/**
 * REST Web Service
 *
 * @author Leopoldo
 */
@Path("pollws")
public class PollWsResource {

    @Context
    private UriInfo context;
    
    @EJB
    private PollFacade pollFacade;

    /**
     * Creates a new instance of PollWsResource
     */
    public PollWsResource() {
    }

    /**
     * Retrieves representation of an instance of com.webcuisine.service.PollWsResource
     * @param id
     * @param site
     * @param name
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml(@DefaultValue("0") @QueryParam("id") int id, 
                         @DefaultValue("") @QueryParam("site") String site, 
                         @DefaultValue("") @QueryParam("name") String name) {
        
        Poll poll;
        String xmldata = "";
        
        if (id != 0) {
            if ((poll = pollFacade.getPoll(id)) != null) {
                xmldata = poll.getXmldata();
            }
        } else if (!site.isEmpty() && !name.isEmpty()) {
            if ((poll = pollFacade.getPoll(site, name)) != null) {
                xmldata = poll.getXmldata();
            }
        }
        
        if (!xmldata.isEmpty()) {
            return xmldata;
        } else {
            return "<data></data>";
        }
        
    }
    
    private static Map<String, String> parseQuery(String url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = url.split("&");
        
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            
            if (!query_pairs.containsKey(key)) {
                String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                query_pairs.put(key, value);
            }
        }
        
        return query_pairs;
    }

    /**
     * PUT method for updating or creating an instance of PollWsResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
        
        int id = 0;
        String site, name, xmldata;
        
        try {
            Map<String, String> params = parseQuery(content);
            
            String sId = params.get("id");
            if (sId != null && !sId.isEmpty()) {
                id = Integer.parseInt(sId);
            }
            site = params.get("site");
            name = params.get("name");
            xmldata = params.get("xmldata");
            
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
        
        if (xmldata != null && !xmldata.isEmpty()) {
            if (id != 0) {
                pollFacade.updatePoll(id, xmldata);
            } else if (site != null && name != null && !site.isEmpty() && !name.isEmpty()) {
                if (pollFacade.getPoll(site, name) != null) {
                    pollFacade.updatePoll(site, name, xmldata);
                } else {
                    pollFacade.createPoll(site, name, xmldata);
                }
            }
        }
        
    }
}
