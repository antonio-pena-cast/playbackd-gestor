/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Storm
 */
@Entity
@Table(name = "list")
@NamedQueries({
    @NamedQuery(name = "List.findAll", query = "SELECT l FROM List l"),
    @NamedQuery(name = "List.findByUserId", query = "SELECT l FROM List l WHERE l.listPK.userId = :userId"),
    @NamedQuery(name = "List.findByAlbumId", query = "SELECT l FROM List l WHERE l.listPK.albumId = :albumId"),
    @NamedQuery(name = "List.findByType", query = "SELECT l FROM List l WHERE l.type = :type"),
    @NamedQuery(name = "List.findByReview", query = "SELECT l FROM List l WHERE l.review = :review"),
    @NamedQuery(name = "List.findByRating", query = "SELECT l FROM List l WHERE l.rating = :rating"),
    @NamedQuery(name = "List.findByDate", query = "SELECT l FROM List l WHERE l.date = :date"),
    @NamedQuery(name = "List.findByCreatedAt", query = "SELECT l FROM List l WHERE l.createdAt = :createdAt"),
    @NamedQuery(name = "List.findByUpdatedAt", query = "SELECT l FROM List l WHERE l.updatedAt = :updatedAt")})
public class List implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListPK listPK;
    @Column(name = "type")
    private String type;
    @Column(name = "review")
    private String review;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Double rating;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Albums albums;
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public List() {
    }

    public List(ListPK listPK) {
        this.listPK = listPK;
    }

    public List(long userId, long albumId) {
        this.listPK = new ListPK(userId, albumId);
    }

    public ListPK getListPK() {
        return listPK;
    }

    public void setListPK(ListPK listPK) {
        this.listPK = listPK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listPK != null ? listPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof List)) {
            return false;
        }
        List other = (List) object;
        if ((this.listPK == null && other.listPK != null) || (this.listPK != null && !this.listPK.equals(other.listPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.List[ listPK=" + listPK + " ]";
    }
    
}
