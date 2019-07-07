package de.dhbw.karlsruhe.helper;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Diese Klasse implementiert eine SessionFactory für den Zugriff auf die Datenbank.
 * Die Implementierung ist adaptiert von @see <a href="https://dzone.com/articles/hibernate-5-xml-configuration-example">dzone</a>
 *
 * @author Markus Bilz
 */
public class HibernateHelper {
    private static SessionFactory sessionFactory;

    /**
     * Hilfsmethode zur Konfiguration der {@code SessionFactory}.
     *
     * @return SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").build();

        Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().
                build();

        SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();

        return sessionFactoryBuilder.build();
    }

    /**
     * Methode zur Erzeugung einer gemeinsamen Instanz der {@code SessionFactory}.
     *
     * @return instanz
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
}