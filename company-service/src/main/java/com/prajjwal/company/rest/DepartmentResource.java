package com.prajjwal.company.rest;

import java.util.List;

import com.prajjwal.company.dto.DepartmentDto;
import com.prajjwal.company.entity.Department;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	@GET
	@Path("/{id}")
	public Uni<Response> getDepartment(@PathParam("id") Long id) {
		return Department.<Department>findById(id).onItem().ifNotNull()
				.transform(dept -> Response.ok(new DepartmentDto(dept.id, dept.getDepartmentName())).build()).onItem()
				.ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)::build);
	}

	@POST
	public Uni<Response> createDepartment(DepartmentDto dto) {
		Department department = new Department();
		department.setDepartmentName(dto.departmentName());
		return department.persistAndFlush().replaceWith(Response.status(Response.Status.CREATED)
				.entity(new DepartmentDto(department.id, department.getDepartmentName())).build());
	}

	@GET
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public Multi<DepartmentDto> streamDepartments() {
// They stopped supporting stream() and streaAll(0 as this requires connections to be opened. FOr slow client, it's not good. Below is what they say :
//		"Reactive developers may wonder why we can’t return a stream of fruits directly. It tends to be a bad idea when dealing with a database. Relational databases do not handle streaming well. It’s a problem of protocols not designed for this use case. So, to stream rows from the database, you need to keep a connection (and sometimes a transaction) open until all the rows are consumed. If you have slow consumers, you break the golden rule of databases: don’t hold connections for too long. Indeed, the number of connections is rather low, and having consumers keeping them for too long will dramatically reduce the concurrency of your application. So, when possible, use a Uni<List<T>> and load the content. If you have a large set of results, implement pagination."
// link for above documentation  : https://quarkus.io/guides/getting-started-reactive
		// Start a transaction that fetches all departments as Uni<List<Department>>
		Uni<List<Department>> uni = Panache.withTransaction(() -> Department.listAll());
		// Convert the list into a Multi for SSE streaming
		return uni.onItem().transformToMulti(departments -> Multi.createFrom().iterable(departments)).onItem()
				.transform(dept -> new DepartmentDto(dept.id, dept.getDepartmentName()));

	}

}
