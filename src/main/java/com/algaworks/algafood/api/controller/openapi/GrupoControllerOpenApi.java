package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public List<GrupoModel> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public GrupoModel buscar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);

    @ApiOperation("Adiciona um novo grupo")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Grupo criado")})
    public GrupoModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
            GrupoInput grupo);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrad", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public GrupoModel atualizar(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId,
            @ApiParam(name = "corpo", value = "Representação de um novo grupo")
            GrupoInput grupo);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo excluído"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problem.class)))
    })
    public void excluir(
            @ApiParam(value = "ID de um grupo", example = "1")
            Long grupoId);
}
