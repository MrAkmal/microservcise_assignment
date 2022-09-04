package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.service.CountryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/country-base")
public class CountryBaseController {

    private final CountryBaseService service;

    @Autowired
    public CountryBaseController(CountryBaseService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model){

        model.addAttribute("countries",service.getAll());
        return "countrybase/country-base";
    }
}
