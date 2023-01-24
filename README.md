# Imersão Java

Projeto proposto durante a [Imersão Java](https://www.alura.com.br/imersao-java) da Alura.  
O primeiro desafio é criar uma aplicação cliente para consumir uma API de filmes do IMDb e salvar os posters obtidos. O próximo desafio é aplicar os conceitos de orientação a objetos para permitir o consumo de outras APIs que contenham imagens.

## Funcionalidades

A aplicação consome as imagens da [API de top 250 filmes do IMBb](https://imdb-api.com/api/#Top250Movies-header) e da [API de fotos astronômicas da Nasa](https://api.nasa.gov). Elas são alteradas para exibir um texto conforme os exemplos abaixo e depois salvas como arquivos png.

<p align="center">
<img src="https://user-images.githubusercontent.com/61603835/214197719-2b1333e8-629f-4d98-abdd-544a36b3edf3.png" alt="Schindler's List" height="300">
<img src="https://user-images.githubusercontent.com/61603835/214197724-38c23e93-f6bc-4788-8b2a-854c2ac1868e.png" alt="The Colliding Spiral Galaxies of Arp 274" height="300">
</p>

## Tecnologias

- [Java 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Jackson](https://github.com/FasterXML/jackson)
- [IntelliJ IDEA](https://www.jetbrains.com/idea)

## Execução

A execução é feita através do método main da classe [App.java](src/main/java/com/andreick/App.java), as imagens são salvas na pasta "images" que é criada na pasta raiz.  
Para realizar as chamadas de API é necessário a existência arquivo "api.properties" na pasta [resources](src/main/resources) com o valor das chaves das APIs, conforme exemplificado no arquivo [api.properties.example](src/main/resources/api.properties.example).
