package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.ProdutoFotoInput;
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
public class RestauranteProdutoFotoController {

    @PutMapping
    public void atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                          @Valid ProdutoFotoInput produtoFotoInput) {

        System.out.println(produtoFotoInput.getArquivo().getContentType());
        System.out.println(produtoFotoInput.getDescricao());

        String newFileName = UUID.randomUUID().toString() + "_" + produtoFotoInput.getArquivo().getOriginalFilename();
        try {
            produtoFotoInput.getArquivo().transferTo(Paths.get("C:\\Users\\gabriel.emer\\Documents\\Produtos",
                    newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}