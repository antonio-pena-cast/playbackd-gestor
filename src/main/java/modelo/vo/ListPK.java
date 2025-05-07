/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Storm
 */
@Embeddable
public class ListPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "album_id")
    private long albumId;

    public ListPK() {
    }

    public ListPK(long userId, long albumId) {
        this.userId = userId;
        this.albumId = albumId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) albumId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListPK)) {
            return false;
        }
        ListPK other = (ListPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.albumId != other.albumId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.ListPK[ userId=" + userId + ", albumId=" + albumId + " ]";
    }
    
}
