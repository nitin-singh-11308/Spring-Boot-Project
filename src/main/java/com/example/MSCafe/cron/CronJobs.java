package com.example.MSCafe.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobs {
    int count = 0;

    @Scheduled(cron = "* * * * * ?")
    private void randomJob() {
        System.out.println("Hello" + ++count);
        if (count == 60) {
            count = 0;
        }
    }

    @Scheduled(cron = "${my.cron.expression}")
    private void random() {
        System.out.println("Hello From CronJob");
    }
}
