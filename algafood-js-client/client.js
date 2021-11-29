function consultarRestaurantes() {
  $.ajax({
    url: "http://api.algafood.local:8080/restaurantes",
    type: "get",

    success: function(response) {
     alert("Restaurante foi fechado!")
    }
  });
}

function fecharRestaurante() {
  $.ajax({
    url: "http://api.algafood.local:8080/restaurantes/1/fechamento",
    type: "put",

    success: function(response) {
      $("#conteudo").text(JSON.stringify(response));
    }
  });
}

$("#botao").click(consultarRestaurantes);