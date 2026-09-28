package com.example.demo.Service;

import com.example.demo.model.Appointment;
import com.example.demo.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository repo;

    public List<Appointment> getAllAppointments() {
        return repo.findAll()
                   .stream()
                   .sorted((a, b) -> a.getAppointmentTime().compareTo(b.getAppointmentTime()))
                   .toList();
    }

    public List<Appointment> getUpcomingAppointments() {
        return repo.findByAppointmentTimeAfterOrderByAppointmentTimeAsc(LocalDateTime.now());
    }

    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getStatus() == null || appointment.getStatus().isBlank()) {
            appointment.setStatus("CONFIRMED");
        }
        return repo.save(appointment);
    }

    public boolean cancelAppointment(Long id) {
        Optional<Appointment> opt = repo.findById(id);
        if (opt.isPresent()) {
            Appointment appt = opt.get();
            appt.setStatus("CANCELLED");
            repo.save(appt);
            return true;
        }
        return false;
    }

    public boolean deleteAppointment(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Appointment> getById(Long id) {
        return repo.findById(id);
    }
}

