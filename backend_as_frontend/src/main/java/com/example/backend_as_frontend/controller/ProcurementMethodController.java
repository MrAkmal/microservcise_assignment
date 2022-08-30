package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.entity.ProcurementMethod;
import com.example.backend_as_frontend.entity.ProcurementNature;
import com.example.backend_as_frontend.service.ProcurementMethodService;
import com.example.backend_as_frontend.service.ProcurementNatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/procurement-method")
public class ProcurementMethodController {


    private final ProcurementMethodService service;
    private final ProcurementNatureService procurementNatureService;


    @Autowired
    public ProcurementMethodController(ProcurementMethodService service, ProcurementNatureService procurementNatureService) {
        this.service = service;
        this.procurementNatureService = procurementNatureService;
    }


    @GetMapping
    public String getListPage(Model model, @RequestParam(required = false, defaultValue = "id") String fieldName) {
        model.addAttribute("procurementMethods", service.getAll(fieldName));
        return "procurement-method";
    }

    @GetMapping("/{id}")
    public ProcurementMethod get(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("procurementNatures", procurementNatureService.getAll());
        model.addAttribute("procurementMethod", new ProcurementMethod());
        return "procurement-method-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        ProcurementMethod procurementMethod = service.get(id);
        model.addAttribute("procurementNatures", procurementNatureService.getAll());
        model.addAttribute("procurementMethod", procurementMethod);
        return "procurement-method-form";
    }

    @PostMapping
    public String save(ProcurementMethod procurementMethod) {
        if (procurementMethod.getId() != 0) {
            service.update(procurementMethod);
        } else {
            service.save(procurementMethod);
        }
        return "redirect:/procurement-method";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/procurement-method";
    }
}
