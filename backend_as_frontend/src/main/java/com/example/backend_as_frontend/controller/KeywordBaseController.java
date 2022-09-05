package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.dto.KeywordBaseCreateDTO;
import com.example.backend_as_frontend.dto.KeywordBaseUpdateDTO;
import com.example.backend_as_frontend.entity.KeywordBase;
import com.example.backend_as_frontend.service.CountryBaseService;
import com.example.backend_as_frontend.service.KeywordBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/keyword-base")
public class KeywordBaseController {


    private final KeywordBaseService service;
    private final CountryBaseService countryBaseService;


    @Autowired
    public KeywordBaseController(KeywordBaseService service, CountryBaseService countryBaseService) {
        this.service = service;
        this.countryBaseService = countryBaseService;
    }


    @GetMapping
    public String getListPage(Model model, @RequestParam(required = false, defaultValue = "id") String fieldName) {
        model.addAttribute("keywordBases", service.getAll());
        return "keywordbase/keyword-base";
    }

    @GetMapping("/{id}")
    public KeywordBase get(@PathVariable Integer id) {
        return service.get(id);
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("keywordBase", new KeywordBaseCreateDTO());
        model.addAttribute("countries", countryBaseService.getAll());
        return "keywordbase/keyword-base-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable Integer id) {
        KeywordBaseUpdateDTO keywordBase = service.getKeywordBase(id);
        model.addAttribute("keywordBase", keywordBase);
        model.addAttribute("countries",countryBaseService.getAll());
        return "keywordbase/keyword-base-form-update";
    }

    @PostMapping
    public String save(KeywordBaseCreateDTO keywordBase) {
        service.save(keywordBase);
        return "redirect:/keyword-base";
    }

    @PostMapping("/update")
    public String update(KeywordBaseUpdateDTO keywordBase) {
        service.update(keywordBase);
        return "redirect:/keyword-base";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/keyword-base";
    }




}
