package com.example.MSCafe.Controller;

import com.example.MSCafe.Repository.DishRepository;
import com.example.MSCafe.Service.DishService;
import com.example.MSCafe.dto.request.DishRequestDto;
import com.example.MSCafe.dto.response.DishResponseDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishRepository dishRepository;

    @PostMapping
    public ResponseEntity<DishResponseDto> addDish(@RequestBody DishRequestDto dishRequestDto) {
        return new ResponseEntity<>(dishService.addDish(dishRequestDto), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishResponseDto> getDish(@PathVariable Long id) {
        return new ResponseEntity<>(dishService.getDish(id),HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<DishResponseDto>>getAllDish() {
        return new ResponseEntity<>(dishService.getAllDish(), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishResponseDto> updateDish(@PathVariable Long id, @RequestBody DishRequestDto dishRequestDto) {
        return new ResponseEntity<>(dishService.updateDish(id, dishRequestDto), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<GenericResponseDto> removeDish(@RequestParam Long id) {
        return new ResponseEntity<>(dishService.removeDish(id),HttpStatusCode.valueOf(200));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DishResponseDto>> getDishByName(
            @RequestParam String q,
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return new ResponseEntity<>(dishService.searchDish(q,min, max), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<DishResponseDto>> getDishPage(
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortByAttribute,
            @RequestParam(defaultValue = "asc") String sortInOrder
    ) {
        return new ResponseEntity<>(
                dishService.getDishPage(
                        pageIndex,
                        pageSize,
                        sortByAttribute,
                        sortInOrder
                ),
                HttpStatusCode.valueOf(200)
        );
    }
}
