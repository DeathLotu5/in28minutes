package com.in28minutes.springboot.firstrestapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserDetailsRepository userDetailsRepository;

    public UserDetailsCommandLineRunner(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(Arrays.toString(args));
        userDetailsRepository.save(new UserDetails("Ranga", "Admin"));
        userDetailsRepository.save(new UserDetails("Ravi", "Admin"));
        userDetailsRepository.save(new UserDetails("John", "Member"));

        List<UserDetails> users = userDetailsRepository.findAll();

        users.forEach(user -> logger.info(user.toString()));
        logger.info("======================");

        List<UserDetails> admin = userDetailsRepository.findByRole("Admin");
        admin.forEach(member -> logger.info(member.toString()));
    }

}
