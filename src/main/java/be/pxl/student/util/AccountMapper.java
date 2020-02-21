package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountMapper {

    public Account map(String validLine) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);


                String[] attributes = validLine.split(",");

                List<Payment> payments = new ArrayList<>();
                Payment payment = new Payment(LocalDateTime.parse(attributes[3], formatter), Double.parseDouble(attributes[4]), attributes[5], attributes[6]);
                payments.add(payment);
                Account account = new Account(attributes[1], attributes[0],payments);

      return account;
    }
}
