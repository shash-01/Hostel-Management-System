package com.example.demo.main;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.demo.model.User;
import com.example.demo.util.HibernateUtil;

public class Main {

	public static void main(String[] args) {
		
		User user = new User("UVTEST",29,"UVTEST@gmail.com","UVVIT123");
		// TODO Auto-generated method stub
		Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            System.out.println("Student saved successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

	}

}
