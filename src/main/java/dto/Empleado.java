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
import javax.persistence.Id;
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
@Table(name = "empleado")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByCodiempl", query = "SELECT e FROM Empleado e WHERE e.codiempl = :codiempl"),
    @NamedQuery(name = "Empleado.findByNombempl", query = "SELECT e FROM Empleado e WHERE e.nombempl = :nombempl"),
    @NamedQuery(name = "Empleado.findByFechnaciempl", query = "SELECT e FROM Empleado e WHERE e.fechnaciempl = :fechnaciempl"),
    @NamedQuery(name = "Empleado.findByNacionalidadempl", query = "SELECT e FROM Empleado e WHERE e.nacionalidadempl = :nacionalidadempl")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiempl")
    private Integer codiempl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombempl")
    private String nombempl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechnaciempl")
    @Temporal(TemporalType.DATE)
    private Date fechnaciempl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "Nacionalidadempl")
    private String nacionalidadempl;

    public Empleado() {
    }

    public Empleado(Integer codiempl) {
        this.codiempl = codiempl;
    }

    public Empleado(Integer codiempl, String nombempl, Date fechnaciempl, String nacionalidadempl) {
        this.codiempl = codiempl;
        this.nombempl = nombempl;
        this.fechnaciempl = fechnaciempl;
        this.nacionalidadempl = nacionalidadempl;
    }

    public Integer getCodiempl() {
        return codiempl;
    }

    public void setCodiempl(Integer codiempl) {
        this.codiempl = codiempl;
    }

    public String getNombempl() {
        return nombempl;
    }

    public void setNombempl(String nombempl) {
        this.nombempl = nombempl;
    }

    public Date getFechnaciempl() {
        return fechnaciempl;
    }

    public void setFechnaciempl(Date fechnaciempl) {
        this.fechnaciempl = fechnaciempl;
    }

    public String getNacionalidadempl() {
        return nacionalidadempl;
    }

    public void setNacionalidadempl(String nacionalidadempl) {
        this.nacionalidadempl = nacionalidadempl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiempl != null ? codiempl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.codiempl == null && other.codiempl != null) || (this.codiempl != null && !this.codiempl.equals(other.codiempl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Empleado[ codiempl=" + codiempl + " ]";
    }
    
}
