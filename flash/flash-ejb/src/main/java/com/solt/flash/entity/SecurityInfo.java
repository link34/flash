package com.solt.flash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SecurityInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	public SecurityInfo() {
		creation = new Date();
		modification = new Date();
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modification;
    private String createUser;
    private String modUser;
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public Date getModification() {
		return modification;
	}
	public void setModification(Date modification) {
		this.modification = modification;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result + ((creation == null) ? 0 : creation.hashCode());
		result = prime * result + ((modUser == null) ? 0 : modUser.hashCode());
		result = prime * result + ((modification == null) ? 0 : modification.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityInfo other = (SecurityInfo) obj;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (creation == null) {
			if (other.creation != null)
				return false;
		} else if (!creation.equals(other.creation))
			return false;
		if (modUser == null) {
			if (other.modUser != null)
				return false;
		} else if (!modUser.equals(other.modUser))
			return false;
		if (modification == null) {
			if (other.modification != null)
				return false;
		} else if (!modification.equals(other.modification))
			return false;
		return true;
	}

}