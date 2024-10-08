package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Appointment;
import za.ac.cput.repository.AppointmentRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Appointment create(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    public Appointment read(Integer appointmentId) {
        return repository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with id: " + appointmentId));
    }

    @Override
    public Appointment update(Appointment appointment) {
        if (!repository.existsById(appointment.getAppointmentId())) {
            throw new NoSuchElementException("Appointment not found with id: " + appointment.getAppointmentId());
        }
        return repository.save(appointment);
    }

    @Override
    public void delete(Integer appointmentId) {
        if (repository.existsById(appointmentId)) {
            repository.deleteById(appointmentId);
        } else {
            throw new NoSuchElementException("Appointment not found with id: " + appointmentId);
        }
    }

    @Override
    public List<Appointment> getAll() {
        return repository.findAll();
    }
}
