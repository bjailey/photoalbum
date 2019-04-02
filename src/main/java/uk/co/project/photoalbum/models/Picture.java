package uk.co.nbrown.photoalbum.models;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "pictures")
public class Picture {

	@Column(name = "file_name")
	private String fileName;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;
	
	@Column(name = "kid_id")
	private String kidId;
	
	@Column(name = "kid_age")
	private int kidAge;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "location")
	private String location;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Picture other = (Picture) obj;
		return Objects.equals(date, other.date) && Objects.equals(fileName, other.fileName)
				&& Objects.equals(id, other.id) && kidAge == other.kidAge && Objects.equals(kidId, other.kidId)
				&& Objects.equals(location, other.location);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, fileName, id, kidAge, kidId, location);
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getKidId() {
		return kidId;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getId() {
		return id;
	}
	
	public int getKidAge() {
		return kidAge;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setKidAge(int kidAge) {
		this.kidAge = kidAge;
	}
	
	public void setKidId(String kidId) {
		this.kidId = kidId;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
}