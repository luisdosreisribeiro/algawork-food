package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmissaoPedidoService {
    private final PedidoRepository pedidoRepository;
    private final CadastroCidadeService cadastroCidadeService;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;
    private final CadastroProdutoService cadastroProdutoService;




    public EmissaoPedidoService(
            PedidoRepository pedidoRepository,
            CadastroCidadeService cadastroCidadeService,
            CadastroUsuarioService cadastroUsuarioService,
            CadastroRestauranteService cadastroRestauranteService,
            CadastroFormaPagamentoService cadastroFormaPagamentoService,
            CadastroProdutoService cadastroProdutoService){
        this.pedidoRepository = pedidoRepository;
        this.cadastroCidadeService = cadastroCidadeService;
        this.cadastroUsuarioService = cadastroUsuarioService;
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.cadastroFormaPagamentoService = cadastroFormaPagamentoService;
        this.cadastroProdutoService = cadastroProdutoService;
    }

    public Pedido buscarOuFalhar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(()->new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido){
        validaPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    private void validaPedido(Pedido pedido){
        Cidade cidade = cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if(restaurante.naoAceitaFormaPagamento(formaPagamento)){
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido){
        pedido.getItens().forEach((item -> {
            Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        }));
    }

}