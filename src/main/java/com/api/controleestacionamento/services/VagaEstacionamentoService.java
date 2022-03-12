package com.api.controleestacionamento.services;

import com.api.controleestacionamento.repositories.VagaEstacionamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VagaEstacionamentoService {

    final VagaEstacionamentoRepository vagaEstacionamentoRepository;

    public VagaEstacionamentoService(VagaEstacionamentoRepository vagaEstacionamentoRepository) {
        this.vagaEstacionamentoRepository = vagaEstacionamentoRepository;
    }
}