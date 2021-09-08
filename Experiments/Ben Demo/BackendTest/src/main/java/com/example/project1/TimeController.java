package com.example.project1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeController {
    LocalTime time = LocalTime.now();
    DateTimeFormatter formattedTimeObj = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = time.format(formattedTimeObj);
    @GetMapping("/")
    public String time(){return "The time is " + formattedTime;}

    @GetMapping("/{timezone}")
    public String time(@PathVariable String timezone){return "The time is " + formattedTime + " in " + timezone;}
}
