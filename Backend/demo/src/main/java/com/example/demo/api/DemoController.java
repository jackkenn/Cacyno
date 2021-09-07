package com.example.demo.api;

import com.example.demo.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.DemoService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/demo")
@RestController
public class DemoController {

    private final DemoService _demoService;

    @Autowired
    public DemoController(DemoService _demoService) {
        this._demoService = _demoService;
    }

    @PostMapping
    public void addDemo(@RequestBody DemoModel demo) {
        _demoService.addDemo(demo);
    }

    @GetMapping
    public List<DemoModel> getAll() {
        return _demoService.getAll();
    }

    @GetMapping(path = "{id}")
    public DemoModel getDemoById(@PathVariable("id") UUID id) {
        return _demoService.getDemoById(id)
                .orElse(null);
    }

    @DeleteMapping(path="{id}")
    public void deleteDemo(@PathVariable("id") UUID id) {
        _demoService.deleteDemo(id);
    }

    @PutMapping(path="{id}")
    public void updateDemo(@PathVariable("id") UUID id, @RequestBody DemoModel demo) {
        _demoService.updateDemo(id, demo);
    }
}
