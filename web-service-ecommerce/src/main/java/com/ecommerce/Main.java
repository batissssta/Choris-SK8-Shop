package com.ecommerce;

import com.ecommerce.util.ConexaoMySql;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.sql.SQLException;

@ServletComponentScan
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SpringApplication.run(Main.class, args);
    }

}

