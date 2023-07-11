package com.ems.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

import com.ems.enums.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer documentId;
    private String number;
    
    private String type;
    private LocalDate dateOfIssue;
    private LocalDate dateOfExpiry;
    
    @Column(name = "file")
    @ElementCollection(targetClass = String.class)
    @JoinColumn(name = "document_id")
    private List<String> file;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		return Objects.equals(dateOfExpiry, other.dateOfExpiry) && Objects.equals(dateOfIssue, other.dateOfIssue)
				&& Objects.equals(file, other.file) && Objects.equals(number, other.number)
				&& Objects.equals(type, other.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfExpiry, dateOfIssue, file, number, type);
	}
	
    
}
