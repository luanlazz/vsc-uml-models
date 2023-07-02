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
@Table(name = "operation")
public class OperationEntity {
	@Id
	@GeneratedValue(generator = "operation_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "operation_id_sequence", sequenceName = "operation_id_sequence", allocationSize = 1)
	@Column(name = "id_operation")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_class", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private ClassEntity classEntity;
	private Long idReturn;
	@Column(name = "name")
	private String name;
	@Column(name = "id_type")
	private Long IdType;
	@Column(name = "visibility")
	private String visibility;
	@Column(name = "is_class")
	private String isClass;
	@OneToMany(mappedBy = "operation", cascade = CascadeType.ALL)
	private List<OperationParameterEntity> parameters;

	public OperationEntity() {
	}

	public OperationEntity(String idUml, ClassEntity classEntity, Long idReturn, String name, Long idType,
			String visibility, String isClass) {
		this.idUml = idUml;
		this.classEntity = classEntity;
		this.idReturn = idReturn;
		this.name = name;
		this.IdType = idType;
		this.visibility = visibility;
		this.isClass = isClass;
		this.parameters = new ArrayList<OperationParameterEntity>();
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

	public ClassEntity getIdClass() {
		return classEntity;
	}

	public void setIdClass(ClassEntity classEntity) {
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

	public Long getIdReturn() {
		return idReturn;
	}

	public void setIdReturn(Long idReturn) {
		this.idReturn = idReturn;
	}

	public void addParameter(OperationParameterEntity parameter) {
		this.parameters.add(parameter);
	}

	public List<OperationParameterEntity> getParameters() {
		return parameters;
	}

	public void setParameters(List<OperationParameterEntity> parameters) {
		this.parameters = parameters;
	}
}
