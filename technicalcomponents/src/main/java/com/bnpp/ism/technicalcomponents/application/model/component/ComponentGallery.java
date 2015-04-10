package com.bnpp.ism.technicalcomponents.application.model.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;

@Entity
public class ComponentGallery {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
    private Long version;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	@OneToMany
	List<StoredFile> images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<StoredFile> getImages() {
		return images;
	}

	public void setImages(List<StoredFile> images) {
		this.images = images;
	}
	
	public void addImage(StoredFile image) {
		if (getImages()==null) {
			this.images = new ArrayList<StoredFile>();
		}
		getImages().add(image);
	}
	
	public void removeImage(StoredFile image) {
		if (getImages()!=null)
			getImages().remove(image);
	}
}
