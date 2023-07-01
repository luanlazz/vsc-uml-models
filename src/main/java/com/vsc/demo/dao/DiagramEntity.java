package com.vsc.demo.dao;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagram")
public class DiagramEntity {
	@Id
	@SequenceGenerator(name = "diagram_id_sequence", sequenceName = "diagram_id_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diagram_id_sequence")
	@Column(name = "id_diagram")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private String type;
	@OneToMany(mappedBy = "diagramEntity", cascade = CascadeType.ALL)
	private List<ClassEntity> classes;

	public DiagramEntity() {
	}

	public DiagramEntity(Long id, String idUml, String name, String type) {
		super();
		this.id = id;
		this.idUml = idUml;
		this.name = name;
		this.type = type;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
