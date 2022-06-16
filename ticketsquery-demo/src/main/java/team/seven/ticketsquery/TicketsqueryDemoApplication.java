package team.seven.ticketsquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TicketsqueryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsqueryDemoApplication.class, args);
    }

}
