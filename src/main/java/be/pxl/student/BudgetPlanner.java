package be.pxl.student;

import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BudgetPlanner {
    //private static final Logger LOGGER = LogManager.getLogger(BudgetPlanner.class);
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager= null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory("musicdb_pu");
            entityManager = entityManagerFactory.createEntityManager();
            BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter(entityManager);
            budgetPlannerImporter.importCsv(Paths.get("src/main/resources/account_payments.csv"));
        }
        finally {
            if(entityManager != null){
                entityManager.close();
            }
            if(entityManagerFactory != null){
                entityManagerFactory.close();
            }
        }






//        for(int i = 0; i < 25 ; i++){
//            LOGGER.info("Start reading file");
//            new BudgetPlannerImporter().importCsv( Paths.get("src/main/resources/account_payments.csv"));
//            LOGGER.info("Finished readeing file");
//        }
//
//        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();
//        Path path = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/account_payments.csv");
//        budgetPlannerImporter.importCsv(path);

    }


}
