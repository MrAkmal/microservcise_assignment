package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.dto.EgpCountryCreateDTO;
import com.example.backend_as_frontend.dto.EgpCountryDTO;
import com.example.backend_as_frontend.dto.EgpCountryUpdateDTO;
import com.example.backend_as_frontend.service.CountryBaseService;
import com.example.backend_as_frontend.service.EgpCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/egp-country")
public class EgpCountryController {

    private final EgpCountryService egpCountryService;
    private final CountryBaseService countryBaseService;


    @Autowired
    public EgpCountryController(EgpCountryService egpCountryService, CountryBaseService countryBaseService) {
        this.egpCountryService = egpCountryService;
        this.countryBaseService = countryBaseService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("egpCountries", egpCountryService.getAll());
        return "egpcountry/egp-country";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("egpCountryCreateDTO", new EgpCountryCreateDTO());
        model.addAttribute("countries", countryBaseService.getAll());
        return "egpcountry/egp-country-create-form";
    }

    @PostMapping
    public String save(@Valid EgpCountryCreateDTO egpCountryCreateDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("egpCountryCreateDTO", egpCountryCreateDTO);
            return "egpcountry/egp-country";
        }
        egpCountryService.save(egpCountryCreateDTO);
        return "redirect:/egp-country";
    }

    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable Integer id) {
        EgpCountryDTO egpCountryDTO = egpCountryService.get(id);
        EgpCountryUpdateDTO egpCountryUpdateDTO = new EgpCountryUpdateDTO(
                egpCountryDTO.getId(),
                countryBaseService.getCountryId(egpCountryDTO.getCountryName()),
                egpCountryDTO.isDefault()
        );
        model.addAttribute("egpCountryUpdateDTO", egpCountryUpdateDTO);
        model.addAttribute("countries", countryBaseService.getAll());
        return "egpcountry/egp-country-update-form";
    }

    @PostMapping("/update")
    public String update(EgpCountryUpdateDTO egpCountryUpdateDTO) {

        egpCountryService.update(egpCountryUpdateDTO);
        return "redirect:/egp-country";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        egpCountryService.delete(id);
        return "redirect:/egp-country";
    }
}
