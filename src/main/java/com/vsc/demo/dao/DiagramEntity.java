package com.vsc.demo.dao;

import java.util.ArrayList;
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
	@GeneratedValue(generator = "diagram_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "diagram_id_sequence", sequenceName = "diagram_id_sequence", allocationSize = 1)
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

	public DiagramEntity(String idUml, String name, String type) {
		this.idUml = idUml;
		this.name = name;
		this.type = type;
		this.classes = new ArrayList<ClassEntity>();
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

	public void addClass(ClassEntity classEntity) {
		this.classes.add(classEntity);
	}

	public boolean removeClass(ClassEntity classEntity) {
		return this.classes.remove(classEntity);
	}

	public List<ClassEntity> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassEntity> classes) {
		this.classes = classes;
	}
}
