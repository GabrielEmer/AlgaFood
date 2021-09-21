package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @PutMapping
    public void atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                          @Valid FotoProdutoInput fotoProdutoInput) {

        System.out.println(fotoProdutoInput.getArquivo().getContentType());
        System.out.println(fotoProdutoInput.getDescricao());

        String newFileName = UUID.randomUUID().toString() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();
        try {
            fotoProdutoInput.getArquivo().transferTo(Paths.get("C:\\Users\\gabriel.emer\\Documents\\Produtos",
                    newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}