package com.api.controleestacionamento.controllers;

import com.api.controleestacionamento.dtos.VagaEstacionamentoDto;
import com.api.controleestacionamento.models.VagaEstacionamentoModel;
import com.api.controleestacionamento.services.VagaEstacionamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga-estacionamento")
public class VagaEstacionamentoController {

    final VagaEstacionamentoService vagaEstacionamentoService;


    public VagaEstacionamentoController(VagaEstacionamentoService vagaEstacionamentoService) {
        this.vagaEstacionamentoService = vagaEstacionamentoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVagaEstacionamento(@RequestBody @Valid VagaEstacionamentoDto vagaEstacionamentoDto) {
        if (vagaEstacionamentoService.existsByPlacaCarro(vagaEstacionamentoDto.getPlacaCarro())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Placa do Carro já está em uso! ");
        }
        if (vagaEstacionamentoService.existsByNumeroVaga(vagaEstacionamentoDto.getNumeroVaga())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A vaga de estacionamento já está em uso!");
        }
        if (vagaEstacionamentoService.existsByApartamentoAndBloco(vagaEstacionamentoDto.getApartamento(), vagaEstacionamentoDto.getBloco())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vaga já registrada para este apartamento/bloco! ");
        }
        var vagaEstacionamentoModel = new VagaEstacionamentoModel();
        BeanUtils.copyProperties(vagaEstacionamentoDto, vagaEstacionamentoModel);
        vagaEstacionamentoModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(vagaEstacionamentoService.save(vagaEstacionamentoModel));
    }
}