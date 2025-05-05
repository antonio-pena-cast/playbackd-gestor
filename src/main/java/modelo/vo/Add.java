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
@Table(name = "add")
@NamedQueries({
    @NamedQuery(name = "Add.findAll", query = "SELECT a FROM Add a"),
    @NamedQuery(name = "Add.findByUserId", query = "SELECT a FROM Add a WHERE a.addPK.userId = :userId"),
    @NamedQuery(name = "Add.findByAlbumId", query = "SELECT a FROM Add a WHERE a.addPK.albumId = :albumId"),
    @NamedQuery(name = "Add.findByType", query = "SELECT a FROM Add a WHERE a.type = :type"),
    @NamedQuery(name = "Add.findByReview", query = "SELECT a FROM Add a WHERE a.review = :review"),
    @NamedQuery(name = "Add.findByRating", query = "SELECT a FROM Add a WHERE a.rating = :rating"),
    @NamedQuery(name = "Add.findByDate", query = "SELECT a FROM Add a WHERE a.date = :date"),
    @NamedQuery(name = "Add.findByCreatedAt", query = "SELECT a FROM Add a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "Add.findByUpdatedAt", query = "SELECT a FROM Add a WHERE a.updatedAt = :updatedAt")})
public class Add implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AddPK addPK;
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

    public Add() {
    }

    public Add(AddPK addPK) {
        this.addPK = addPK;
    }

    public Add(long userId, long albumId) {
        this.addPK = new AddPK(userId, albumId);
    }

    public AddPK getAddPK() {
        return addPK;
    }

    public void setAddPK(AddPK addPK) {
        this.addPK = addPK;
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
        hash += (addPK != null ? addPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Add)) {
            return false;
        }
        Add other = (Add) object;
        if ((this.addPK == null && other.addPK != null) || (this.addPK != null && !this.addPK.equals(other.addPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Add[ addPK=" + addPK + " ]";
    }

}
