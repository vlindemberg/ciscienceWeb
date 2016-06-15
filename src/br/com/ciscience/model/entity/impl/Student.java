package br.com.ciscience.model.entity.impl;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Student extends User {

	private List<Quiz> quiz;
	
	@OneToMany
	public List<Quiz> getQuiz() {
		return quiz;
	}

	public void setQuiz(List<Quiz> quiz) {
		this.quiz = quiz;
	}

	@Override
	public String toString() {
		return "Student [quiz=" + quiz + "]";
	}

}
