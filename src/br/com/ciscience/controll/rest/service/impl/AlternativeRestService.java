package br.com.ciscience.controll.rest.service.impl;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import br.com.ciscience.model.dao.impl.AlternativeDAO;
import br.com.ciscience.model.entity.impl.Alternative;
import br.com.ciscience.model.jpa.impl.JPAUtil;
import br.com.ciscience.util.Constants;
import br.com.ciscience.util.JSONUtil;
import br.com.ciscience.util.ResponseBuilderGenerator;

@Path("/alternative")
public class AlternativeRestService {
	
	private JPAUtil simpleEntityManager;
	private AlternativeDAO alternativeDAO;
	
	@Context
	private HttpServletRequest servletRequest;
	
	@GET
	@PermitAll
	public Response list() {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.alternativeDAO = new AlternativeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		try {

			this.simpleEntityManager.beginTransaction();

			List<Alternative> alternative = this.alternativeDAO.findAll();

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(alternative));

		} catch (Exception e) {
			
			this.simpleEntityManager.rollBack();
			
			e.printStackTrace();
			
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();

	}

	@GET
	@Path("/{id}")
	@PermitAll
	public Response getByID(@PathParam("id") String id) {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.alternativeDAO = new AlternativeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		try {

			this.simpleEntityManager.beginTransaction();

			responseBuilder = ResponseBuilderGenerator.createOKResponseJSON(responseBuilder,
					JSONUtil.objectToJSON(this.alternativeDAO.getById(Long.parseLong(id))));

		} catch (Exception e) {
			
			this.simpleEntityManager.rollBack();
			
			e.printStackTrace();
			
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
		
			this.simpleEntityManager.close();
		}
		return responseBuilder.build();
	}

	@POST
	@PermitAll
	public Response create(@FormParam("text") String text, @FormParam("answer") boolean answer) {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.alternativeDAO = new AlternativeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {
			
			Alternative alternative = new Alternative();
			
			alternative.setText(text);
			alternative.setAnswer(answer);
			alternative.setStatus(Constants.ACTIVE_ENTITY);
			

				if (alternative.validateEmptyFields()) {
					this.alternativeDAO.save(alternative);
					this.simpleEntityManager.commit();

					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
				} else {
					System.out.println("erro na validas�o dos campos");
					responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
				}

			

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();
	}

	@DELETE
	@Path("/{idAlternative}")
	@PermitAll
	public Response delete(@PathParam("idAlternative") Long idAlternative) {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.alternativeDAO = new AlternativeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {

			Alternative alternative = this.alternativeDAO.getById(idAlternative);

			if (alternative != null) {

				alternative.setStatus(!alternative.getStatus());
				
				this.simpleEntityManager.commit();

				responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();

	}

	@PUT
	@Path("/{id}")
	@PermitAll
	public Response update(@PathParam("id") Long id, @FormParam("text") String text, @FormParam("answer") boolean answer) {

		this.simpleEntityManager = new JPAUtil(Constants.PERSISTENCE_UNIT_NAME);
		this.alternativeDAO = new AlternativeDAO(this.simpleEntityManager.getEntityManager());
		ResponseBuilder responseBuilder = Response.noContent();

		this.simpleEntityManager.beginTransaction();

		try {

			Alternative alternative = this.alternativeDAO.getById(id);

			if (alternative != null) {
				alternative.setText(text);
				alternative.setAnswer(answer);

				if (alternative.validateEmptyFields()) {
					this.alternativeDAO.update(alternative);
					this.simpleEntityManager.commit();

					responseBuilder = ResponseBuilderGenerator.createOKResponseTextPlain(responseBuilder);

				} else {
					responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
				}
			} else {
				responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
			}

		} catch (Exception e) {
			this.simpleEntityManager.rollBack();
			e.printStackTrace();
			responseBuilder = ResponseBuilderGenerator.createErrorResponse(responseBuilder);
		} finally {
			this.simpleEntityManager.close();
		}

		return responseBuilder.build();

	}
}
