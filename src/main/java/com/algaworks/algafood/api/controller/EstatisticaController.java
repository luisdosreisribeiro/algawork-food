package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticaController {

    private final VendaQueryService vendaQueryService;

    public EstatisticaController(
            VendaQueryService vendaQueryService
    ){
        this.vendaQueryService = vendaQueryService;
    }

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                         @RequestParam(required = false, defaultValue = "+00:00" ) String timeOffset ){
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }
}
