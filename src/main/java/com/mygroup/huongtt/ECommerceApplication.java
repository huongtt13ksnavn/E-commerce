package com.mygroup.huongtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ECommerceApplication.class, args);
  }

//  @Bean
//  CommandLineRunner runner(ProductService productService,
//      UserService userService) {
//    return args -> {
//      productService.save(
//          new Product(1L, "TV Set", 300.00, "http://placehold.it/200x100"));
//      productService.save(new Product(2L, "Game Console", 200.00,
//          "http://placehold.it/200x100"));
//      productService
//          .save(new Product(3L, "Sofa", 100.00, "http://placehold.it/200x100"));
//      productService.save(
//          new Product(4L, "Icecream", 5.00, "http://placehold.it/200x100"));
//      productService
//          .save(new Product(5L, "Beer", 3.00, "http://placehold.it/200x100"));
//      productService.save(
//          new Product(6L, "Phone", 500.00, "http://placehold.it/200x100"));
//      productService
//          .save(new Product(7L, "Watch", 30.00, "http://placehold.it/200x100"));
//      userService.create(new SignUpForm("huongtt", "huongtt@gmail.com",
//          "123123", "huong", "dep trai", "0123123123"));
//    };
//  }

}
