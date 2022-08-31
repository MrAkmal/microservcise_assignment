package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.entity.KeywordBase;
import com.example.backend_as_frontend.service.KeywordBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/keyword-base")
public class KeywordBaseController {


    private final KeywordBaseService service;


    @Autowired
    public KeywordBaseController(KeywordBaseService service) {
        this.service = service;
    }


    @GetMapping
    public String getListPage(Model model, @RequestParam(required = false, defaultValue = "id") String fieldName) {
        model.addAttribute("keywordBases", service.getAll(fieldName));
        return "keywordbase/keyword-base";
    }

    @GetMapping("/{id}")
    public KeywordBase get(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("keywordBase", new KeywordBase());
        return "keywordbase/keyword-base-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        KeywordBase procurementNature = service.get(id);
        model.addAttribute("keywordBase", procurementNature);
        return "keywordbase/keyword-base-form";
    }

    @PostMapping
    public String save(KeywordBase procurementNature) {
        if (procurementNature.getId() != 0) {
            service.update(procurementNature);
        } else {
            service.save(procurementNature);
        }
        return "redirect:/keyword-base";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/keyword-base";
    }


}
