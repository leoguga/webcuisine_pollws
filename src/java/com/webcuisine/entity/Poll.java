/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webcuisine.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leopoldo
 */
@Entity
@Table(name = "poll")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poll.findAll", query = "SELECT p FROM Poll p"),
    @NamedQuery(name = "Poll.findById", query = "SELECT p FROM Poll p WHERE p.id = :id"),
    @NamedQuery(name = "Poll.findBySite", query = "SELECT p FROM Poll p WHERE p.site = :site"),
    @NamedQuery(name = "Poll.findByName", query = "SELECT p FROM Poll p WHERE p.name = :name"),
    @NamedQuery(name = "Poll.findBySiteName", query = "SELECT p FROM Poll p WHERE p.name = :name AND p.site = :site")})
public class Poll implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "xmldata")
    private String xmldata;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "site")
    private String site;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    public Poll() {
    }

    public Poll(Integer id) {
        this.id = id;
    }

    public Poll(Integer id, String xmldata, String site, String name) {
        this.id = id;
        this.xmldata = xmldata;
        this.site = site;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXmldata() {
        return xmldata;
    }

    public void setXmldata(String xmldata) {
        this.xmldata = xmldata;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poll)) {
            return false;
        }
        Poll other = (Poll) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webcuisine.servlet.Poll[ id=" + id + " ]";
    }
    
}
