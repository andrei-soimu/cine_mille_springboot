package com.andreisoimu.cine_mille.model.dao;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class DataSuperclass implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="creation_date")
	private LocalDateTime creationDate;
	
	@Column(name="update_date")
	private LocalDateTime updateDate;

	@PrePersist
	protected void onCreate() {
		creationDate = LocalDateTime.now();
		updateDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updateDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
}
