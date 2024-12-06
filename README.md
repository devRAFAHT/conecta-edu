Conecta Edu

Padões de Commit: https://dev.to/renatoadorno/padroes-de-commits-commit-patterns-41co

Analizar o projeto:

Etapas no desenvolvimento do projeto

2. Criar a Entidade
A entidade ficará na pasta entity. Será uma classe que representa a tabela no banco de dados.

Campos básicos: Adicione os atributos principais, como id, nome, data, etc.
Anotações:
Use @Entity para mapeá-la como uma tabela.
Use @Table para configurar detalhes do nome da tabela.
Configure validações com @NotNull, @Size, etc.
Relacione com outras entidades: Por exemplo, se o exame pertence a várias escolas, crie a relação necessária usando @ManyToOne ou @OneToMany.



3. Criar o Repositório
Na pasta repository, crie uma interface chamada ExameNacionalRepository.

Estenda JpaRepository: Isso fornece acesso a métodos básicos de CRUD automaticamente.
Consultas personalizadas (opcional):
Se precisar de buscas específicas, use o padrão de métodos Spring Data JPA (ex.: findByNomeContaining).
Ou implemente consultas JPQL com @Query.

4. Criar o Serviço
Na pasta service, crie uma classe ExameNacionalService.

Função:
Implementar a lógica de negócios.
Garantir que as regras de validação sejam seguidas.
Trabalhar com os métodos do repositório.
Responsabilidades:
Validar dados antes de salvar (ex.: verificar se o nome não está vazio).
Verificar relações (ex.: uma escola existe antes de associá-la a um exame).
Tratar exceções, como ResourceNotFoundException para buscas inválidas.
Métodos básicos:
createExame(): Para criar um novo exame.
getAllExames(): Retorna todos os exames.
getExameById(): Retorna um exame específico.
updateExame(): Atualiza dados de um exame existente.
deleteExame(): Remove um exame do banco.

5. Criar o Controlador
Na pasta web, crie uma classe ExameNacionalController.

Função:
Criar os endpoints que os clientes (ou frontend) vão usar.
Fazer as requisições chegarem ao serviço.
Endpoints básicos:
POST /exames: Para criar um novo exame.
GET /exames: Para listar todos os exames.
GET /exames/{id}: Para buscar um exame específico.
PUT /exames/{id}: Para atualizar um exame.
DELETE /exames/{id}: Para excluir um exame.
Anotações:
Use @RestController para declarar a classe como um controlador.
Mapeie rotas com @RequestMapping ou @GetMapping, @PostMapping, etc.
Lembre-se de usar @Valid nos parâmetros para validar os dados enviados.

6. Criar o Mapper (se necessário)
Na pasta mapper, crie um ExameNacionalMapper.

Função:
Converter entre a entidade (ExameNacional) e um DTO.
Evitar expor diretamente a estrutura interna da entidade ao frontend.
Por que usar um Mapper?
Protege dados sensíveis.
Permite criar respostas específicas para diferentes cenários (ex.: retornar apenas o nome e data do exame em uma listagem).
Ferramentas:
Pode ser manual (um simples método toDto).
Ou usar bibliotecas como MapStruct para automatizar.

7. Criar os DTOs
Na pasta dto, crie os Data Transfer Objects (DTOs).

Função:
Representar dados de entrada e saída.
Exemplo: ExameNacionalRequestDTO (para requisições) e ExameNacionalResponseDTO (para respostas).
Campos:
O DTO pode conter apenas os dados necessários para o cliente.
Exemplo: O ExameNacionalResponseDTO pode excluir informações como IDs de relacionamentos.

8. Configurar o Banco de Dados
Certifique-se de que o banco de dados está configurado corretamente no arquivo application.properties ou application.yml.
Verifique as dependências no pom.xml:
Spring Data JPA.
Driver do banco (MySQL, PostgreSQL, etc.).

9. Testar com o Postman
Após criar o CRUD, teste cada endpoint no

Postman ou outra ferramenta de API. Aqui estão os passos para isso:

Passos para Testar no Postman
Criar um novo exame (POST)

Endpoint: http://localhost:8080/exames
Método HTTP: POST
Body (JSON):
json
Copy code
{
  "nome": "Exame Nacional de Matemática",
  "data": "2024-12-10",
  "duracao": 180,
  "quantidadeQuestoes": 50
}
Listar todos os exames (GET)

Endpoint: http://localhost:8080/exames
Método HTTP: GET
Resposta esperada (JSON):
json
Copy code
[
  {
    "id": 1,
    "nome": "Exame Nacional de Matemática",
    "data": "2024-12-10",
    "duracao": 180,
    "quantidadeQuestoes": 50
  }
]
Buscar um exame específico (GET)

Endpoint: http://localhost:8080/exames/1
Método HTTP: GET
Resposta esperada (JSON):
json
Copy code
{
  "id": 1,
  "nome": "Exame Nacional de Matemática",
  "data": "2024-12-10",
  "duracao": 180,
  "quantidadeQuestoes": 50
}
Atualizar um exame (PUT)

Endpoint: http://localhost:8080/exames/1
Método HTTP: PUT
Body (JSON):
json
Copy code
{
  "nome": "Exame Nacional de Física",
  "data": "2024-12-15",
  "duracao": 120,
  "quantidadeQuestoes": 40
}
Excluir um exame (DELETE)

Endpoint: http://localhost:8080/exames/1
Método HTTP: DELETE

10. Validação e Tratamento de Erros
Certifique-se de implementar o tratamento adequado de erros:

Exemplo de erros:
400 Bad Request: Dados inválidos no body.
404 Not Found: ID do exame não encontrado.
500 Internal Server Error: Erros inesperados.
Crie um @ControllerAdvice para capturar exceções como ResourceNotFoundException e retornar respostas amigáveis ao cliente.