package com.klu.skill3final1;
import com.klu.util.HibernateUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.klu.entity.Product;

public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Insert Sample Data
        session.save(new Product("Phone", "Electronics", 20000, 10));
        session.save(new Product("Laptop", "Electronics", 80000, 5));
        session.save(new Product("Tablet", "Electronics", 30000, 8));
        session.save(new Product("Chair", "Furniture", 5000, 20));
        session.save(new Product("Pen", "Stationery", 20, 100));

        tx.commit();

        // ================= SORTING =================

        System.out.println("===== SORT BY PRICE ASC =====");
        Query<Product> ascQuery = session.createQuery(
                "FROM Product ORDER BY price ASC", Product.class);

        ascQuery.getResultList().forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice()));

        System.out.println("===== SORT BY PRICE DESC =====");
        Query<Product> descQuery = session.createQuery(
                "FROM Product ORDER BY price DESC", Product.class);

        descQuery.getResultList().forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice()));

        System.out.println("===== SORT BY QUANTITY (Highest First) =====");
        Query<Product> qtyQuery = session.createQuery(
                "FROM Product ORDER BY quantity DESC", Product.class);

        qtyQuery.getResultList().forEach(p ->
                System.out.println(p.getName() + " - " + p.getQuantity()));

        // ================= PAGINATION =================

        System.out.println("===== PAGINATION (First 3 Products) =====");
        Query<Product> pageQuery = session.createQuery(
                "FROM Product", Product.class);

        pageQuery.setFirstResult(0);
        pageQuery.setMaxResults(3);

        pageQuery.getResultList().forEach(p ->
                System.out.println(p.getName()));

        // ================= AGGREGATES =================

        System.out.println("===== TOTAL COUNT =====");
        Query<Long> countQuery = session.createQuery(
                "SELECT COUNT(p) FROM Product p", Long.class);

        System.out.println("Total Products: " + countQuery.getSingleResult());

        System.out.println("===== MIN & MAX PRICE =====");

        Query<Double> minQuery = session.createQuery(
                "SELECT MIN(p.price) FROM Product p", Double.class);

        Query<Double> maxQuery = session.createQuery(
                "SELECT MAX(p.price) FROM Product p", Double.class);

        System.out.println("Min Price: " + minQuery.getSingleResult());
        System.out.println("Max Price: " + maxQuery.getSingleResult());

        // ================= GROUP BY =================

        System.out.println("===== GROUP BY DESCRIPTION =====");

        Query<Object[]> groupQuery = session.createQuery(
                "SELECT p.description, COUNT(p) FROM Product p GROUP BY p.description",
                Object[].class);

        List<Object[]> groupResults = groupQuery.getResultList();

        for (Object[] row : groupResults) {
            System.out.println("Category: " + row[0] + " Count: " + row[1]);
        }

        // ================= WHERE (PRICE RANGE) =================

        System.out.println("===== PRICE BETWEEN 5000 AND 30000 =====");

        Query<Product> priceQuery = session.createQuery(
                "FROM Product p WHERE p.price BETWEEN 5000 AND 30000",
                Product.class);

        priceQuery.getResultList().forEach(p ->
                System.out.println(p.getName() + " - " + p.getPrice()));

        // ================= LIKE QUERIES =================

        System.out.println("===== STARTS WITH 'P' =====");

        Query<Product> startQuery = session.createQuery(
                "FROM Product p WHERE p.name LIKE 'P%'", Product.class);

        startQuery.getResultList().forEach(p ->
                System.out.println(p.getName()));

        System.out.println("===== ENDS WITH 'e' =====");

        Query<Product> endQuery = session.createQuery(
                "FROM Product p WHERE p.name LIKE '%e'", Product.class);

        endQuery.getResultList().forEach(p ->
                System.out.println(p.getName()));

        System.out.println("===== CONTAINS 'ap' =====");

        Query<Product> containQuery = session.createQuery(
                "FROM Product p WHERE p.name LIKE '%ap%'", Product.class);

        containQuery.getResultList().forEach(p ->
                System.out.println(p.getName()));

        // ================= EXACT LENGTH =================

        System.out.println("===== NAME LENGTH = 5 =====");

        Query<Product> lengthQuery = session.createQuery(
                "FROM Product p WHERE LENGTH(p.name) = 5",
                Product.class);

        lengthQuery.getResultList().forEach(p ->
                System.out.println(p.getName()));

        session.close();
    }
}