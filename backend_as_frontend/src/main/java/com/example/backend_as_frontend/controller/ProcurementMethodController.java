package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.dto.ProcurementMethodCreateDTO;
import com.example.backend_as_frontend.dto.ProcurementMethodUpdateDTO;
import com.example.backend_as_frontend.entity.ProcurementMethod;
import com.example.backend_as_frontend.service.KeywordBaseService;
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

    private final KeywordBaseService keywordBaseService;

    @Autowired
    public ProcurementMethodController(ProcurementMethodService service, ProcurementNatureService procurementNatureService, KeywordBaseService keywordBaseService) {
        this.service = service;
        this.procurementNatureService = procurementNatureService;
        this.keywordBaseService = keywordBaseService;
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
        model.addAttribute("keywordBases",keywordBaseService.getAll());
        model.addAttribute("procurementMethod", new ProcurementMethodCreateDTO());
        return "procurement-method-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        ProcurementMethodUpdateDTO procurementMethod = service.getById(id);
        model.addAttribute("procurementNatures", procurementNatureService.getAll());
        model.addAttribute("procurementMethod", procurementMethod);
        model.addAttribute("keywordBases",keywordBaseService.getAll());
        return "procurement-method-form-update";
    }

    @PostMapping
    public String save(ProcurementMethodCreateDTO procurementMethod) {
        service.save(procurementMethod);
        return "redirect:/procurement-method";
    }

    @PostMapping("/update")
    public String update(ProcurementMethodUpdateDTO procurementMethod) {
        service.update(procurementMethod);
        return "redirect:/procurement-method";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/procurement-method";
    }
}
