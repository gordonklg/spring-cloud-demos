package gordon.scdemo.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableAdminServer
public class AdminServer {
    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }
}