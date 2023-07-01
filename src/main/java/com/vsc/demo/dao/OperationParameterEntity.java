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
	@SequenceGenerator(name = "op_parameter_id_sequence", sequenceName = "op_parameter_id_sequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "op_parameter_id_sequence")
	@Column(name = "id_parameter")
	private Long id;
	@Column(name = "id_uml")
	private String idUml;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_operation", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private OperationEntity operationEntity;
	@Column(name = "name")
	private String name;
	@Column(name = "id_type")
	private Long IdType;
	@Column(name = "value_default")
	private String valueDefault;
	
	public OperationParameterEntity() {	
	}
	
	public OperationParameterEntity(Long id, String idUml, OperationEntity operationEntity, String name, Long idType,
			String valueDefault) {
		super();
		this.id = id;
		this.idUml = idUml;
		this.operationEntity = operationEntity;
		this.name = name;
		IdType = idType;
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
		return operationEntity;
	}

	public void setOperationEntity(OperationEntity operationEntity) {
		this.operationEntity = operationEntity;
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
