package com.algaworks.algafood.infrastructure.service.query;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        query.where(montarFiltro(filtro, builder, root));
        query.select(retornarSelect(builder, root, getFunctionDateDataCriacao(builder, root, timeOffset)));
        query.groupBy(getFunctionDateDataCriacao(builder, root, timeOffset));

        return manager.createQuery(query).getResultList();
    }

    private Expression<Date> getFunctionDateDataCriacao(CriteriaBuilder builder, Root<Pedido> root, String timeOffset) {
        return builder.function("date", Date.class,
                builder.function("convert_tz", Date.class, root.get("dataCriacao"),
                        builder.literal("+00:00"), builder.literal(timeOffset)));
    }

    private CompoundSelection<VendaDiaria> retornarSelect(CriteriaBuilder builder, Root<Pedido> root, Expression<Date> functionDateDataCriacao) {
        return builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
    }

    private Predicate montarFiltro(VendaDiariaFilter filtro, CriteriaBuilder builder, Root<Pedido> root) {
        var predicates = new ArrayList<Predicate>();

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        if (filtro.getRestauranteId() != null)
            predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));

        if (filtro.getDataCriacaoInicio() != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));

        if (filtro.getDataCriacaoFim() != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
