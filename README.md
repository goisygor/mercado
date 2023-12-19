
Linguagem de programação central do projeto, utilizada para implementar toda a lógica e funcionalidades:

O núcleo do projeto é desenvolvido na linguagem Java, sendo responsável por abrigar a lógica e as funcionalidades essenciais do sistema.

GUI Swing:

A interface gráfica do usuário é construída utilizando a biblioteca GUI Swing. Esta é responsável por criar elementos visuais, como botões e tabelas, proporcionando uma experiência interativa e amigável.

PostgreSQL:

O sistema de banco de dados PostgreSQL é empregado para armazenar e gerenciar informações persistentes do sistema. A estrutura do banco de dados pode ser ajustada no arquivo ConnectionFactory.java, localizado no pacote Gerenciamento.Connection.

Estrutura do Projeto:

O código-fonte está organizado em diferentes pacotes, cada um desempenhando uma função específica:

Gerenciamento.Control: Classes responsáveis pelo controle e lógica de negócios do sistema.
Gerenciamento.Connection: Classes para interação com o banco de dados PostgreSQL.
Gerenciamento.Model: Classes de modelo representando as entidades do sistema.
Gerenciamento.View: Classes relacionadas à interface gráfica do usuário.
Manual de Uso:

Configuração do Banco de Dados:

Antes de executar as aplicações, é necessário configurar corretamente o banco de dados PostgreSQL. As informações de conexão podem ser ajustadas no arquivo ConnectionFactory.java no pacote Gerenciamento.Connection.

Compilação e Execução:

Para compilar e executar as aplicações, é necessário ter um ambiente de desenvolvimento Java configurado. Isso pode ser feito por meio de uma IDE Java, como Eclipse, ou utilizando comandos no terminal.

Funcionalidades do Sistema:

Clientes:

Cadastro de Cliente: Adição de um novo cliente com CPF, nome completo e idade.
Atualização de Cliente: Modificação de informações de um cliente existente.
Exclusão de Cliente: Remoção de um cliente do sistema.
Estoque:

Cadastro de Produto: Adição de um novo produto no estoque com nome, preço e quantidade.
Atualização de Produto: Modificação de informações de um produto existente.
Exclusão de Produto: Remoção de um produto do estoque.
Atualização de Quantidade: Modificação da quantidade de um produto no estoque.
Vendas:

Registro de Venda: Adição de uma venda, incluindo cliente, valor, data e quantidade de produtos.
Além disso, o sistema é composto por duas aplicações distintas, cada uma com sua interface de login. A primeira é destinada aos gerentes, enquanto a segunda é para os funcionários. É necessário efetuar o login em ambas as aplicações para obter acesso completo às interfaces. Em cada janela, é preciso inserir usuário e senha, sendo que na primeira tela de login, você terá acesso à interface de gerente, e na segunda, à interface de funcionário.


Bom para mim foi um desafio e tanto ainda mais que fiz esse projeto sozinho, estou enviando somente agora por conta que não estava conseguindo fazer a conexão com meu POSTGRESQL, porém consegui, tive muito trabalho em fazer o relatório de venda fiquei 1 dia todo só trabalhando nele
fiz duas telas, uma tela de funcionario e de gerente, pesquisei bastante como eu poderia fazer, usei youtubee gpt, porém nenhum dos dois acabou me ajudando e pedi ajuda a um amigo que ja trabalha na área e ele me esclareceu algumas coisas, espero que goste !
