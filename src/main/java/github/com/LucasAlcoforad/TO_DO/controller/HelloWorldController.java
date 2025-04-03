package github.com.LucasAlcoforad.TO_DO.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Hello World")
public class HelloWorldController {
    @GetMapping
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello World");
    }
}
