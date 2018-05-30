package com.spring.l201.model;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employee2 {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "MID")
	private String mid;
	@Column(name = "EMP_NAME")
	private String employeeName;

	public Employee2() {

	}

	public Employee2(String mid, String employeeName) {
		this.mid = mid;
		this.employeeName = employeeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", mid=" + mid + ", empName=" + employeeName + "]";
	}
}
