package de.dhbw.karlsruhe.helper;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Diese Klasse implementiert eine {@link SessionFactory} für den Zugriff auf die Datenbank.
 *
 * <p>
 * Die Implementierung ist adaptiert von <a href="https://dzone.com/articles/hibernate-5-xml-configuration-example">dzone.com</a>.
 * </p>
 *
 * @author Markus Bilz
 */
public class HibernateHelper {
    private static SessionFactory sessionFactory;

    /**
     * Hilfsmethode zur Konfiguration der {@link SessionFactory}.
     *
     * @return SessionFactory
     * @author Markus Bilz
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
     * <p>
     * Implementierung des Singelton Patterns (GOF).
     * </p>
     *
     * @return instanz
     * @author Markus Bilz
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
}