package com.vsc.demo.dao;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "op_parameter")
public class OperationParameterEntity {
	@Id
	@GeneratedValue(generator = "op_parameter_id_sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "op_parameter_id_sequence", sequenceName = "op_parameter_id_sequence", allocationSize = 1)
	@Column(name = "id_parameter")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_operation", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private OperationEntity operation;
	@Column(name = "name")
	private String name;
	@Column(name = "id_type")
	private Long IdType;
	@Column(name = "value_default")
	private String valueDefault;

	public OperationParameterEntity() {
	}

	public OperationParameterEntity(String idUml, OperationEntity operation, String name, Long idType,
			String valueDefault) {
		this.idUml = idUml;
		this.operation = operation;
		this.name = name;
		this.IdType = idType;
		this.valueDefault = valueDefault;
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

	public OperationEntity getOperationEntity() {
		return operation;
	}

	public void setOperationEntity(OperationEntity operation) {
		this.operation = operation;
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

	public String getValueDefault() {
		return valueDefault;
	}

	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}
}
