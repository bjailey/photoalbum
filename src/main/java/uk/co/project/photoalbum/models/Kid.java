package uk.co.project.photoalbum.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "kids")
public class Kid {

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "kid_id", referencedColumnName = "id")
	private List<Picture> pictures = new ArrayList<>();
	
	public void addPicture(Picture picture) {
		pictures.add(picture);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kid other = (Kid) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(gender, other.gender)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(pictures, other.pictures);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, gender, id, name, pictures);
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getGender() {
		return gender;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<Picture> getPicture(String pictureId) {
		Picture picture = null;
		for (Picture element : pictures) {
			if (element.getId().equals(pictureId)) {
				picture = element;
				break;
			}
		}
		return Optional.ofNullable(picture);
	}
	
	public List<Picture> getPictures() {
		return pictures;
	}
	
	public void removePicture(String pictureId) {
		Iterator<Picture> iterator = pictures.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getId().equals(pictureId)) {
				iterator.remove();
				break;
			}
		}
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
}