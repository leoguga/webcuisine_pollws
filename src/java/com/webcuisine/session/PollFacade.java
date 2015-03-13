/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webcuisine.session;

import com.webcuisine.entity.Poll;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Leopoldo
 */
@Stateless
public class PollFacade extends AbstractFacade<Poll> {
    @PersistenceContext(unitName = "PollAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PollFacade() {
        super(Poll.class);
    }
    
    public Poll getPoll(int id) {
        
        Query queryPollById = em.createNamedQuery("Poll.findById");
        queryPollById.setParameter("id", id);
        
        if (queryPollById.getResultList().size() != 1) {
            return null;
        }
        
        return (Poll) queryPollById.getSingleResult();
        
    }
    
    public Poll getPoll(String site, String name) {
        
        Query queryPollBySiteName = em.createNamedQuery("Poll.findBySiteName");
        queryPollBySiteName.setParameter("site", site);
        queryPollBySiteName.setParameter("name", name);
        
        if (queryPollBySiteName.getResultList().size() != 1) {
            return null;
        }
        
        return (Poll) queryPollBySiteName.getSingleResult();
        
    }
    
    public void createPoll(String site, String name, String xmldata) {
        
        if (this.getPoll(site, name) == null) {
            Poll poll = new Poll();
            poll.setSite(site);
            poll.setName(name);
            poll.setXmldata(xmldata);
            em.persist(poll);
        }
        
    }
    
    public void updatePoll(int id, String xmldata) {
        
        if (id != 0) {
            Poll tempPoll;
            if ((tempPoll = this.getPoll(id)) != null) {
                this.updatePoll(tempPoll.getSite(), tempPoll.getName(), xmldata);
            }
        }
        
    }
    
    public void updatePoll(String site, String name, String xmldata) {
        
        if (!site.isEmpty() && !name.isEmpty() && !xmldata.isEmpty()) {
            Poll poll;
            if ((poll = this.getPoll(site, name)) != null) {
                poll.setXmldata(xmldata);
                em.flush();
                em.refresh(poll);
            }
        }
        
    }
    
}
