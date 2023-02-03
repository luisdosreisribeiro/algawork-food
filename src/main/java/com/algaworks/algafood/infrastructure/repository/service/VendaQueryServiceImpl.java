package com.algaworks.algafood.infrastructure.repository.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
    @PersistenceContext
    private final EntityManager manager;

    public VendaQueryServiceImpl(EntityManager manager){
        this.manager = manager;
    }
    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDateCriacao = builder.function(
                "date", LocalDate.class,root.get("dataCriacao"));

        var selection = builder.construct(VendaDiaria.class,
                functionDateDateCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        var predicates = new ArrayList<Predicate>();

        if(filtro.getRestauranteId()!= null){
            predicates.add(builder.equal(root.get("restaurante"),filtro.getRestauranteId()));
        }
        if(filtro.getDataCriacaoInicio() != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),filtro.getDataCriacaoInicio()));
        }
        if(filtro.getDataCriacaoFim() != null){
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),filtro.getDataCriacaoFim()));
        }
        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.groupBy(functionDateDateCriacao);
        query.where(predicates.toArray(new Predicate[0]));

        return  manager.createQuery(query).getResultList();
    }
}
