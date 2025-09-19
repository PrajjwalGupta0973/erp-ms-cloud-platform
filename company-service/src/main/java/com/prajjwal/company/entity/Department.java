package com.prajjwal.company.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Department extends PanacheEntity {

	@Column(name = "department_name")
	public String departmentName;
}
