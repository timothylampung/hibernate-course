package com.infiniteskills.data;

import com.infiniteskills.data.entities.Attendance;
import com.infiniteskills.data.entities.StudentImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.infiniteskills.data.entities.UserImpl;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			Configuration configuration = new Configuration();
			configuration.addAnnotatedClass(UserImpl.class);
			configuration.addAnnotatedClass(StudentImpl.class);
			configuration.addAnnotatedClass(Attendance.class);
			return configuration
					.buildSessionFactory(new StandardServiceRegistryBuilder()
							.build());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("There was an error building the factory");
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
