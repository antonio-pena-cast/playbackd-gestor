/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Storm
 */
@Entity
@Table(name = "albums")
@NamedQueries({
    @NamedQuery(name = "Albums.findAll", query = "SELECT a FROM Albums a"),
    @NamedQuery(name = "Albums.findById", query = "SELECT a FROM Albums a WHERE a.id = :id"),
    @NamedQuery(name = "Albums.findByName", query = "SELECT a FROM Albums a WHERE a.name = :name"),
    @NamedQuery(name = "Albums.findByAuthor", query = "SELECT a FROM Albums a WHERE a.author = :author"),
    @NamedQuery(name = "Albums.findByGenre", query = "SELECT a FROM Albums a WHERE a.genre = :genre"),
    @NamedQuery(name = "Albums.findByReleaseDate", query = "SELECT a FROM Albums a WHERE a.releaseDate = :releaseDate"),
    @NamedQuery(name = "Albums.findByImage", query = "SELECT a FROM Albums a WHERE a.image = :image"),
    @NamedQuery(name = "Albums.findByCreatedAt", query = "SELECT a FROM Albums a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "Albums.findByUpdatedAt", query = "SELECT a FROM Albums a WHERE a.updatedAt = :updatedAt")})
public class Albums implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "author")
    private String author;
    @Basic(optional = false)
    @Column(name = "genre")
    private String genre;
    @Basic(optional = false)
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Basic(optional = false)
    @Column(name = "image")
    private String image;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "albums")
    private List<Add> addList;

    public Albums() {
    }

    public Albums(Long id) {
        this.id = id;
    }

    public Albums(Long id, String name, String author, String genre, Date releaseDate, String image) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Add> getAddList() {
        return addList;
    }

    public void setAddList(List<Add> addList) {
        this.addList = addList;
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
        if (!(object instanceof Albums)) {
            return false;
        }
        Albums other = (Albums) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Albums[ id=" + id + " ]";
    }
    
}
