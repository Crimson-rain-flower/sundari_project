package com.example.demo.Controller;

import com.example.demo.model.Appointment;
import com.example.demo.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping("/")
    public String home(Model model) {
        return loadIndexPage(model);
    }

    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        return loadIndexPage(model);
    }

    private String loadIndexPage(Model model) {
        model.addAttribute("listAppointments", service.getAllAppointments());
        model.addAttribute("appointment", new Appointment());
        return "index";
    }

    @PostMapping("/appointments/save")
    public String saveAppointment(
            @ModelAttribute Appointment appointment,
            RedirectAttributes redirectAttributes) {

        if (appointment.getCustomerName() == null || appointment.getCustomerName().isBlank()
                || appointment.getServiceType() == null || appointment.getServiceType().isBlank()
                || appointment.getAppointmentTime() == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "Please fill all required fields.");
            return "redirect:/appointments";
        }

        Appointment saved = service.saveAppointment(appointment);
        redirectAttributes.addFlashAttribute("bookingSuccess", true);
        redirectAttributes.addFlashAttribute("confirmedName", saved.getCustomerName());
        redirectAttributes.addFlashAttribute("confirmedService", saved.getServiceType());
        redirectAttributes.addFlashAttribute("confirmedTime", saved.getAppointmentTime());

        return "redirect:/appointments";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean ok = service.cancelAppointment(id);
        redirectAttributes.addFlashAttribute(ok ? "infoMsg" : "errorMsg",
            ok ? "Appointment #" + id + " has been cancelled." : "Appointment not found.");
        return "redirect:/appointments";
    }

    @PostMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean ok = service.deleteAppointment(id);
        redirectAttributes.addFlashAttribute(ok ? "infoMsg" : "errorMsg",
            ok ? "Appointment #" + id + " removed." : "Appointment not found.");
        return "redirect:/appointments";
    }
}
