package com.vinay.jenkins.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "entities") // Corrected collection name format
public class Entity {
    @Id
    private String id;
    private String name;
    private String type;
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	public void setId(String id) {
		this.id = id;
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
