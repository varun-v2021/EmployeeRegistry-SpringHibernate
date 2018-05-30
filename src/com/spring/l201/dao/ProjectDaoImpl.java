package com.spring.l201.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.spring.l201.model.Employee2;
import com.spring.l201.model.Project;

@Repository
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Project proj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(proj);
	}

	@Override
	public void persist(Project proj) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(proj);
	}

	@Override
	public List<Employee2> findEmpByProjId(int projId) {
		Session session = sessionFactory.getCurrentSession();
		List<Employee2> employees = session.createQuery("from Employee2 where project_id = :prj_id")
				.setInteger("prj_id", projId).list();
		return employees;
	}

	@Override
	public Employee2 findEmpByMid(Long id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Employee2.class, id);
	}

	public List<Project> findProjectByName(String pName) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Project where name = :pName");
		query.setParameter("pName", pName);
		return query.list();
	}

	@Override
	public Collection<String> getAllDistinctProjects() {
		Session session = sessionFactory.getCurrentSession();

		Criteria crit = session.createCriteria(Project.class);

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("projectName"));

		crit.setProjection(Projections.distinct(projList));

		return crit.list();

	}

	@Override
	public List<Employee2> getAllEmployees() {
		Session session = sessionFactory.getCurrentSession();
		List<Employee2> employees = session.createQuery("from Employee2").list();
		return employees;
	}

	@Override
	public void deleteEmp(Employee2 empObj) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(empObj);
	}

	@Override
	public List<Project> getAllProjects() {
		Session session = sessionFactory.getCurrentSession();
		List<Project> projects = session.createQuery("from Project").list();
		return projects;
	}

}
