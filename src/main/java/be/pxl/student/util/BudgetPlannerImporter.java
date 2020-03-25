package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;


/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    private static final Logger LOGGER = LogManager.getLogger(BudgetPlannerImporter.class);
    private PathMatcher csvMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.csv"); // kijken als het path voldoet aan een formaat
    private AccountMapper accountMapper = new AccountMapper();
    private CounterAccountMapper counterAccountMapper = new CounterAccountMapper();
    private PaymentMapper paymentMapper = new PaymentMapper();
    private Map<String, Account> createdAccounts = new HashMap<>();
    private EntityManager entityManager;

    public BudgetPlannerImporter(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    public void importCsv(Path path){

        if (!csvMatcher.matches(path)){
            LOGGER.debug("Invalid file: .csv expected .Provided: {}",path);
            return;
        }
        if(!Files.exists(path)){
            LOGGER.error("File {} does not exist.",path);
            return;
        }

        try(BufferedReader reader = Files.newBufferedReader(path)) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                try{
                    Payment payment = paymentMapper.map(line);
                    payment.setAccount(getOrCreateAccount(accountMapper.map(line)));
                    payment.setCounterAccount(getOrCreateAccount(counterAccountMapper.map(line)));
                    entityManager.persist(payment);
                }
                catch(InvalidPaymentException e){
                    LOGGER.error("Error while mapping line: {}",e.getMessage());
                }

            }
            tx.commit();
        }catch(IOException e){
                LOGGER.fatal("An error occured while reading file: {}",path);
            }
        }

    private Account getOrCreateAccount(Account account) {
        if (createdAccounts.containsKey(account.getIBAN())){
            return createdAccounts.get(account.getIBAN());

        }
        entityManager.persist(account);
        createdAccounts.put(account.getIBAN(), account);
        return account;
    }


    //    private static Account createAccount(String[] metadata) {
//        String name = metadata[0];
//        String IBAN = metadata[1];
//        String author = metadata[2];
//
//        // create and return book of this metadata
//        Account account = new Account();
//        account.setName(name);
//        account.setIBAN(IBAN);
//
//    }
//        try (BufferedReader br = Files.newBufferedReader(path,StandardCharsets.US_ASCII)){
//            String line = br.readLine();
//            while(line !=null){
//                String [] attributes = line.split(",");
//                LocalDateTime date = new LocalDateTime()
//                Payment payment = new Payment((LocalDateTime)attributes[3],attributes[4],attributes[5],attributes[6]);
//                Account account = new Account(attributes[0],attributes[1]);
//            }
//        }catch(IOException ex){
//            ex.printStackTrace();
//        }



}
