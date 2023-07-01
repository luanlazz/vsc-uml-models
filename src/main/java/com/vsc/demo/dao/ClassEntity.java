package com.vsc.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "class")
public class ClassEntity {
	@Id
	@GeneratedValue(generator = "class_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "class_id_sequence", sequenceName = "class_id_sequence", allocationSize = 1)
	@Column(name = "id_class")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_diagram", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private DiagramEntity diagramEntity;
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private List<AttributeEntity> attributes;
	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private List<OperationEntity> operations;

	public ClassEntity() {
	}

	public ClassEntity(String idUml, DiagramEntity diagramEntity, String name) {
		this.idUml = idUml;
		this.diagramEntity = diagramEntity;
		this.name = name;
		this.attributes = new ArrayList<AttributeEntity>();
		this.operations = new ArrayList<OperationEntity>();
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

	public DiagramEntity getDiagramEntity() {
		return diagramEntity;
	}

	public void setDiagramEntity(DiagramEntity diagramEntity) {
		this.diagramEntity = diagramEntity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(AttributeEntity attribute) {
		this.attributes.add(attribute);
	}

	public void addOperation(OperationEntity operation) {
		this.operations.add(operation);
	}
}
