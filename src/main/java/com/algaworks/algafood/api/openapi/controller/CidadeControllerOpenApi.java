package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    public CollectionModel<CidadeModel> listar ();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public CidadeModel buscar (
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long cidadeId);

    @ApiOperation("Adiciona uma nova cidade")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cidade criada")})
    public CidadeModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            CidadeInput cidade);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public CidadeModel atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade")
            CidadeInput cidade);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public void remover(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long cidadeId);
}
