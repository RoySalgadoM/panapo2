package mx.edu.utez.panapo.report.controller;

import mx.edu.utez.panapo.client.model.Client;
import mx.edu.utez.panapo.person.model.Person;
import mx.edu.utez.panapo.report.model.Report;
import mx.edu.utez.panapo.report.model.ReportRepository;
import mx.edu.utez.panapo.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll(){
        return  new ResponseEntity<>(new Message("OK",false,reportRepository.findAll()), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(long id){
        if( reportRepository.existsById(id)){
            return new ResponseEntity<>(new Message("OK", false, reportRepository.findById(id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El Reporte no existe", true, reportRepository.findById(id)), HttpStatus.BAD_REQUEST);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> save(Report report){
        Optional<Report> existsReport = reportRepository.findByDate(report.getDate());
        if(existsReport.isPresent()){
            return new ResponseEntity<>(new Message("El cliente ya existe", true, null), HttpStatus.BAD_REQUEST);
        }
        Report savedReport  = reportRepository.saveAndFlush(report);
        return new ResponseEntity<>(new Message("Reporte registrada correctamente", false, savedReport), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class}) // si encuenra un error lo vuelve a hacer
    public ResponseEntity<Message> update(Report report){
        if(reportRepository.existsById(report.getId())){
            return new ResponseEntity<>(new Message("OK", false, reportRepository.saveAndFlush(report)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("El Reporte no existe", true, null), HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findByProject(long id){
        if( reportRepository.existsByProject(id)){
            return new ResponseEntity<>(new Message("OK", false, reportRepository.findAllByProject(id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("No existe reporte no existe", true, null), HttpStatus.BAD_REQUEST);
    }
}
