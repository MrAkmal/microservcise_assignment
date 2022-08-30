package com.example.backend_as_frontend.controller;


import com.example.backend_as_frontend.dto.PaymentTypeCreateDTO;
import com.example.backend_as_frontend.entity.PaymentType;
import com.example.backend_as_frontend.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-type")
public class PaymentTypeController {

    private final PaymentTypeService service;

    @Autowired
    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }

    @GetMapping
    public String getList(Model model) {
        model.addAttribute("paymentTypes", service.getAll());
        return "paymenttype/payment-type";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("paymentCreateDTO", new PaymentTypeCreateDTO());
        return "paymenttype/payment-type-create-form";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(Model model, @PathVariable Integer id) {
        PaymentType paymentType = service.get(id);
        model.addAttribute("paymentCreateDTO", paymentType);
        return "paymenttype/payment-type-update-form";
    }

    @PostMapping
    public String getSave(PaymentTypeCreateDTO paymentTypeCreateDTO) {
        service.save(paymentTypeCreateDTO);
        return "redirect:/payment-type";
    }

    @PostMapping("/update")
    public String getUpdate(PaymentType paymentTypeCreateDTO) {
        System.out.println("paymentTypeCreateDTO = " + paymentTypeCreateDTO);
        service.update(paymentTypeCreateDTO);
        return "redirect:/payment-type";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/payment-type";
    }
}
