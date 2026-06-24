package com.example.demo.main;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.demo.model.User;
import com.example.demo.util.HibernateUtil;

public class UpdateRecordDemo {
	public static void main(String[] args) {
		//User user = new User("TAKSHILA",23,"TAKSHILA@gmail.com","VIT123");
		// TODO Auto-generated method stub
		Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Step 1: Retrieve existing record
            User user= session.get(User.class, 2);
            if (user != null) {
                // Step 2: Modify the object
                user.setAge(25);
                // Step 3: Save changes
                session.update(user);  // Optional (Hibernate tracks changes)

                System.out.println("User updated successfully!");
            } else {
                System.out.println("User Date not found!");
            }
            transaction.commit();
            System.out.println("Student saved successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

	}
}
