package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.bar.*;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.services.bar.BarService;
import com.pubito.pubito_backend.services.menu.MenuService;
import com.pubito.pubito_backend.services.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars")
@RequiredArgsConstructor
public class BarController {

    private final BarService barService;
    private final ReviewService reviewService;
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<BarResponseDTO> createBar(@RequestBody BarCreateRequestDTO dto){
        return ResponseEntity.ok(barService.createBar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarResponseDTO> getBar(@PathVariable Long id){
        return ResponseEntity.ok(barService.getBarById(id));
    }

    @GetMapping
    public ResponseEntity<List<BarResponseDTO>> getAllBars(){
        return ResponseEntity.ok(barService.getAllBars());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarResponseDTO> updateBar(@PathVariable Long id, @RequestBody BarUpdateRequestDTO dto){
        return ResponseEntity.ok(barService.uptadeBar(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBar(@PathVariable Long id){
        barService.deleteBarById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{barId}/reviews/count")
    public ResponseEntity<Long> getReviewsCountForBar(@PathVariable Long barId){
        Long count = reviewService.countByBarId(barId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/trendy")
    public ResponseEntity<List<TrendyBarResponseDTO>> getTrendyBars(){
        List<TrendyBarResponseDTO> trendyBars = barService.getTrendyBarsFromLastWeek();
        return ResponseEntity.ok(trendyBars);
    }

    @GetMapping("/{barId}/cheapest")
    public ResponseEntity<List<MenuResponseDTO>> getTop3Cheapest(@PathVariable Long barId){
        return ResponseEntity.ok(menuService.getTop3CheapestByBarId(barId));
    }

    @GetMapping("most-menu-items")
    public ResponseEntity<List<BarMenuItemCountResponseDTO>> getBarsWithMostMenuItems(){
        List<BarMenuItemCountResponseDTO> bars = barService.getBarsByMenuItemsCount();
        return ResponseEntity.ok(bars);
    }


}
