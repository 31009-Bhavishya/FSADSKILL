package com.klu.skill2final1;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.klu.entity.Product;
import com.klu.util.HibernateUtil;

public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // INSERT
        Product p = new Product("Laptop", "Gaming Laptop", 75000, 5);
        session.save(p);

        // RETRIEVE
        Product product = session.get(Product.class, 1);
        if (product != null) {
            System.out.println("Product Name: " + product.getName());
        }

        // UPDATE
        if (product != null) {
            product.setPrice(70000);
            session.update(product);
        }

        // DELETE
        if (product != null) {
            session.delete(product);
        }

        tx.commit();
        session.close();

        System.out.println("CRUD Operations Completed Successfully!");
    }
}