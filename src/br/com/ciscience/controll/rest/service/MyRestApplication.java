package br.com.ciscience.controll.rest.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import br.com.ciscience.controll.rest.service.impl.AdministratorRestService;
import br.com.ciscience.controll.rest.service.impl.LevelRestService;
import br.com.ciscience.controll.rest.service.impl.QuestionRestService;
import br.com.ciscience.controll.rest.service.impl.StudentRestService;

public class MyRestApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();

	public MyRestApplication() {

		// Serviços REST
		singletons.add(new AdministratorRestService());
		singletons.add(new StudentRestService() );
		singletons.add(new QuestionRestService());
		singletons.add(new LevelRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}