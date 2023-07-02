package com.vsc.demo.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "attribute")
public class AttributeEntity {
	@Id
	@GeneratedValue(generator = "attribute_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "attribute_id_sequence", sequenceName = "attribute_id_sequence", allocationSize = 1)
	@Column(name = "id_attribute")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_class", nullable = false)
	@JsonIgnore
	private ClassEntity classEntity;
	@Column(name = "name")
	private String name;
	@Column(name = "id_type")
	private Long IdType;
	@Column(name = "visibility")
	private String visibility;

	public AttributeEntity() {
	}

	public AttributeEntity(String idUml, ClassEntity classEntity, String name, Long idType, String visibility) {
		this.idUml = idUml;
		this.classEntity = classEntity;
		this.name = name;
		this.IdType = idType;
		this.visibility = visibility;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdUml() {
		return idUml;
	}

	public void setIdUml(String idUml) {
		this.idUml = idUml;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdType() {
		return IdType;
	}

	public void setIdType(Long idType) {
		IdType = idType;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
}
