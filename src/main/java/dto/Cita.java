/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ANGHELO
 */
@Entity
@Table(name = "cita")
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByCodicita", query = "SELECT c FROM Cita c WHERE c.codicita = :codicita"),
    @NamedQuery(name = "Cita.findByDiacita", query = "SELECT c FROM Cita c WHERE c.diacita = :diacita"),
    @NamedQuery(name = "Cita.findByEmplcita", query = "SELECT c FROM Cita c WHERE c.emplcita = :emplcita"),
    @NamedQuery(name = "Cita.findByServcita", query = "SELECT c FROM Cita c WHERE c.servcita = :servcita")})
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codicita")
    private Integer codicita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "diacita")
    @Temporal(TemporalType.DATE)
    private Date diacita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "emplcita")
    private String emplcita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "servcita")
    private String servcita;
    @JoinColumn(name = "codiUsua", referencedColumnName = "codiUsua")
    @ManyToOne(optional = false)
    private Usuario codiUsua;

    public Cita() {
    }

    public Cita(Integer codicita) {
        this.codicita = codicita;
    }

    public Cita(Integer codicita, Date diacita, String emplcita, String servcita) {
        this.codicita = codicita;
        this.diacita = diacita;
        this.emplcita = emplcita;
        this.servcita = servcita;
    }

    public Integer getCodicita() {
        return codicita;
    }

    public void setCodicita(Integer codicita) {
        this.codicita = codicita;
    }

    public Date getDiacita() {
        return diacita;
    }

    public void setDiacita(Date diacita) {
        this.diacita = diacita;
    }

    public String getEmplcita() {
        return emplcita;
    }

    public void setEmplcita(String emplcita) {
        this.emplcita = emplcita;
    }

    public String getServcita() {
        return servcita;
    }

    public void setServcita(String servcita) {
        this.servcita = servcita;
    }

    public Usuario getCodiUsua() {
        return codiUsua;
    }

    public void setCodiUsua(Usuario codiUsua) {
        this.codiUsua = codiUsua;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codicita != null ? codicita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.codicita == null && other.codicita != null) || (this.codicita != null && !this.codicita.equals(other.codicita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Cita[ codicita=" + codicita + " ]";
    }
    
}
