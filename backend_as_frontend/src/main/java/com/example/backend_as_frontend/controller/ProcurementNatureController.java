package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.entity.ProcurementNature;
import com.example.backend_as_frontend.service.ProcurementNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/procurement-nature")
public class ProcurementNatureController {


    private final ProcurementNatureService service;

    @Autowired
    public ProcurementNatureController(ProcurementNatureService service) {
        this.service = service;
    }


    @GetMapping
    public String getListPage(Model model, @RequestParam(required = false, defaultValue = "id") String fieldName) {
        model.addAttribute("procurementNatures", service.getAll(fieldName));
        return "procurement-nature";
    }

    @GetMapping("/{id}")
    public ProcurementNature get(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("procurementNature", new ProcurementNature());
        return "procurement-nature-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        ProcurementNature procurementNature = service.get(id);
        model.addAttribute("procurementNature", procurementNature);
        return "procurement-nature-form";
    }

    @PostMapping
    public String save(ProcurementNature procurementNature) {
        if (procurementNature.getId() != 0) {
            service.update(procurementNature);
        } else {
            service.save(procurementNature);
        }
        return "redirect:/procurement-nature";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/procurement-nature";
    }


}
