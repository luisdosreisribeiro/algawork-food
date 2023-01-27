package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInput {
    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @NotNull
    private EnderecoInput enderecoEntrega;
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @NotNull
    @Size(min =1)
    private List<ItemPedidoInput> itens;
}
