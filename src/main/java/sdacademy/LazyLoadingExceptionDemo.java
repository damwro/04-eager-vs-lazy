package sdacademy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sdacademy.demo.entity.BusLine;
import sdacademy.demo.entity.Driver;
import sdacademy.demo.entity.DriverDetail;

public class LazyLoadingExceptionDemo {

    public static void main(String[] args) {
        //tworzenie fabryki
        // dodanie konfiguracji, dodanie klas
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(DriverDetail.class)
                .addAnnotatedClass(Driver.class)
                .addAnnotatedClass(BusLine.class)
                .buildSessionFactory();
        //tworzenie sesji na potrzeby naszej pracy
        Session session = factory.getCurrentSession();

        try {
            //rozpocznij transakcję żeby zapisać
            session.beginTransaction();

            //pobierz kierowcę
            Long id = 4L;
            Driver driver = session.get(Driver.class, id);

            System.out.println("Pobrany kierowca: " + driver);

            //zakomituj transakcję
            session.getTransaction().commit();

            //zamknij sesję
            session.close();

            //po zamkniętej sesji wywołanie o obiekt
            System.out.println("Pobrane kursy TRAM/BUS: " + driver.getBusLines());

        } finally {
            //posprzątaj po otwartej sesji
            session.close();
            factory.close();
        }
    }

}
