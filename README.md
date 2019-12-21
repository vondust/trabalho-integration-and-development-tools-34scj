# Trabalho de Integrations & Development Tools - 34SCJ

**Professor:** Carlos Vinicius Magnaboschi Hesketh.

**Descrição:** Implementação - Integração com Apache Kafka.

**Integrantes:**

| RM      | NOME                     	  |
|-------- |-------------------------------|
| 334242  | BRUNO DELPHINO ZAMBOTTI       |
| 333652  | BRUNO AGUIAR CLEMENTE         |
| 334151  | MARCELO TADEU MARCHESONI ZANI |

## Informações Gerais
Este projeto realiza operações de produção e consumo de mensagens em um tópico do kafka, conforme imagem abaixo:

![Macrovisão](https://github.com/bruno-zambotti/trabalho-integration-and-development-tools-34scj/blob/master/assets/macrovisao.png?raw=true)

O producer desenvolvido em python carrega informações de um arquivo reduzido em tamanho em relação ao original com 10.000 registros contra os 13.872.315 registros do arquivo original. 
Esse producer prepara mensagens em formato json para cada um dos registros lidos e posta no tópico do kafka para que os consumidores processem de acordo com sua necessidade específica.

Foram desenvolvidos três consumidores na linguagem java que realizam os seguintes tratamentos:
- Consumidor 1:
  - Exibe [UF] + [soma da parcelas por UF] + [quantidade de beneficiários]; 
  - A cada consumo de uma mensagem do tópico do kafka é exibido no console o resultado calculado para cada registro, desta forma, a quantidade de UF retornadas aumenta na medida que forem processados os registros dos beneficiários.

- Consumidor 2:
    - Exibe os dados do beneficiário que possui o maior valor de parcela.
    - São exibidas a seguintes informações:
      - NIS_FAVORECIDO
      - NOME_FAVORECIDO
      - VALOR_PARCELA
      - NOME_MUNICIPIO
      - UF 
    - A cada consumo de uma mensagem do tópico do kafka é exibido no console o beneficiário que possui o maior valor de parcela,este beneficiário pode ou não ser alterador na medida que forem consumidos outros registros de beneficiários.

- Consumidor 3:
  - Exibe a quantidade total de registros consumidos.  

### Exemplo de funcionamento
![exemplo](exemplo.gif)

No exemplo acima são apresentados sete terminais, descritos abaixo na sequência em que houve a interação com cada um deles:

| Terminal | Função                        |
|----------|-------------------------------|
| 1°       | Iniciar o Zookeeper           |
| 2°       | Iniciar o Kafka               |
| 3°       | Criação do tópico             |
| 4°       | Iniciar o primeiro consumidor |
| 5°       | Iniciar o segundo consumidor  |
| 6°       | Iniciar o terceiro consumidor |
| 7°       | Iniciar o produtor            |

### Exemplo das tarefas do Trello
![Trello](trello.png)

## Pré-requisitos
Dispomos duas formas de rodar os servidores do Zookeeper e do Kafka, a primeira utilizando docker e a segunda instalando as dependências do Zookeeper e do Kafka localmente.

### Se optar por utilizar o Docker

#### Docker
- [Instruções para instalação no Linux](https://docs.docker.com/install/linux/docker-ce/ubuntu/)
- [Instruções para instalação no Windows](https://docs.docker.com/docker-for-windows/install/)

#### Docker Compose
- [Instruções para instalação no Linux](https://docs.docker.com/compose/install/#install-compose)
- Não se faz necessário para ambiente Windows pois o Docker Compose já está incluso na instalação do Docker.

### Se optar por utilizar a instalação localmente

#### Kafka e Zookeeper
- [Link para Download](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.4.0/kafka_2.13-2.4.0.tgz)
- Execute o comando abaixo para descompactar o arquivo baixado a partir da pasta de downloads: 
  > tar zxf Downloads/kafka_2.13-2.4.0

- Após ter descompactado o código binário, você pode executar alguns conforme exemplos abaixo: 
  - Para iniciar o zookeeper com as configurações padrão:
    > bin/zookeeper-server-start.sh config/zookeeper.properties
  
  - Para iniciar o kafka com as configurações padrão:
    > bin/kafka-server-start.sh config/server.properties
  
  - Para criar um novo tópico: 
    > bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic topic-name
  
  - Para listar todos os tópicos criados:
    > bin/kafka-topics.sh --list --bootstrap-server localhost:9092
  
  - Para exibir detalhes de um tópico:
    > bin/kafka-topics.sh  --describe --topic topic-name --bootstrap-server localhost:9092
  
  - Para excluir um tópico:
    > bin/kafka-topics.sh --delete --topic topic-name --bootstrap-server localhost:9092

### Java
- Linguagem:
  - [Link para Download](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
  - Validação: Execute em um terminal o seguinte comando:
    > java --version
- Configuração da variável de ambiente do Java em ambientes Windows. 
  - Execute os seguintes comandos abaixo:
    > setx -m JAVA_HOME "C:\Program Files\Java\jdk1.8.0"

    Nota: O diretório informado é apenas um exemplo.

    > setx -m PATH "%PATH%;%JAVA_HOME%\bin";
  - Validação: Execute em um terminal o seguinte comando:
    > echo %JAVA_HOME%

- Configuração da variável de ambiente do Java em ambientes Unix. 
  - Execute os seguintes comandos abaixo:
    > export JAVA_HOME="/var/usr/java". 
    
    Nota: O diretório informado é apenas um exemplo.

    > export PATH=$JAVA_HOME/bin:$PATH
  - Validação: Execute em um terminal o seguinte comando:
    > echo $JAVA_HOME

### Python
- Linguagem:
  - [Link para Download](https://www.python.org/downloads/)
  - Validação: Execute em um terminal o seguinte comando:
    > python --version
- pip (Gerenciador de pacotes/dependências):
  - Instalação: Execute em um terminal o seguinte comando:
    > python get-pip.py
  - Validação: Execute em um terminal o seguinte comando:
    > pip -V
- virtualenv (Ambiente virtual para desenvolvimento - Opcional):
  - Instalação: Execute em um terminal o seguinte comando:
    > pip install virtualenv
  - Validação: Execute em um terminal o seguinte comando:      
    > virtualenv --version

## Configuração do Ambiente

### Python
Para configurar o ambiente virtual (opcional) do python de forma a evitar a instalação de bibliotecas diretamente em seu computador, execute os seguintes passos no terminal:
> virtualenv -p python .env

Para sistemas Unix/Linux: 
> source .env/bin/activate

Para sistemas Windows (necessária a utilização do powershell): 
> powershell .env/Scripts/activate.ps1

Após a instalação do ambiente virtual execute o seguinte comando em um terminal a partir da raiz deste projeto:
> pip install -r "kafka-producer-python\requirements.txt"

Caso não esteja utilizando o virtualenv e queira remover os pacotes baixados no passo anterior execute o comando abaixo em um terminal:
> pip uninstall -r "kafka-producer-python\requirements.txt"

### Java

Na raiz de cada um projetos em java, execute o seguinte comando para instalar as dependências:

> mvnw install -DskipTests

## Executando a aplicação
### Kafka

Para rodar o a aplicação do Kafka, na raiz deste projeto execute o seguinte comando no prompt de comando ou terminal de seu computador: 
> docker-compose up -d

O docker irá subir dois containers, sendo um o serviço de coordenação Zookeper responsável por gerenciar aplicações distribuídas, sendo utilizado pelo Kafka para sincronizar as configurações entre diferentes clusters. E o Kafka propriamente dito, onde iremos submeter e consumir mensagens.

A efeito de curiosidade as imagens referenciadas neste docker-compose são disponibilizados pela comunidade por meio da Confluent, uma empresa fundada pelo criadores do Kafka e que oferecem ferramentas para prover o kafka como um serviço.

Para verificar se os serviços estão funcionando corretamente, execute os seguintes no prompt de comando ou terminal de seu computador:

- **Zookeper:** 
> docker-compose logs zookeeper | grep -i binding

Deverá ser apresentada uma mensagem como está:
  
> zookeeper_1  | [2019-12-07 20:35:55,221] \
  INFO binding to port 0.0.0.0/0.0.0.0:32181 \(org.apache.zookeeper.server.NIOServerCnxnFactory)

- **Kafka:** 
> docker-compose logs kafka | grep -i started

Deverá ser apresentada uma mensagem como está:

> kafka_1    | [2019-12-07 20:36:55,467] \
INFO [SocketServer brokerId=1] Started 1 acceptor threads (kafka.network.SocketServer) \
kafka_1      | [2019-12-07 20:36:55,578] \
INFO [SocketServer brokerId=1] Started processors for 1 acceptors (kafka.network.SocketServer) \
kafka_1      | [2019-12-07 20:36:55,640] \
INFO [KafkaServer id=1] started (kafka.server.KafkaServer) \
kafka_1      | [2019-12-07 20:36:55,681] \
INFO [ReplicaStateMachine controllerId=1] Started replica state machine with initial state -> Map() (kafka.controller.ReplicaStateMachine) \
kafka_1      | [2019-12-07 20:36:55,711] \
INFO [PartitionStateMachine controllerId=1] Started partition state machine with initial state -> Map() (kafka.controller.PartitionStateMachine)
	
Agora execute os seguintes comandos para criar e verificar o estado do tópico criado:

> docker-compose exec kafka kafka-topics --create --topic **integration-topic** --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181

> docker-compose exec kafka kafka-topics --describe --topic **integration-topic** --zookeeper zookeeper:2181

### Producer

A partir da raiz deste projeto execute os seguintes comandos em um novo terminal: 
> cd kafka-producer-python

Caso ainda não tenha realizado o download das dependências informe o seguinte comando:
pip install -r requirements.txt

> python producer.py

Importante: Antes de executar o comando acima, atente-se a ter executado os passos descritos em [Configuração do Ambiente](#configuração-do-ambiente).

### Consumer

Importante: Antes de executar os comando abaixo, atente-se a ter executado os passos descritos em [Configuração do Ambiente](#configuração-do-ambiente).

A partir da raiz deste projeto, execute os seguintes comandos para cada um dos consumers, um por terminal: 

- Consumidor 1
> cd kafka-first-consumer-java

> mvn spring-boot:run

Caso não possua o maven instalado localmente, utilize o wrapper:
> mvnw spring-boot:run

- Consumidor 2
> cd kafka-second-consumer-java

> mvn spring-boot:run

Caso não possua o maven instalado localmente, utilize o wrapper:
> mvnw spring-boot:run

- Consumidor 3
> cd kafka-third-consumer-java

> mvn spring-boot:run

Caso não possua o maven instalado localmente, utilize o wrapper:
> mvnw spring-boot:run

## Referências
Os dados utilizados para esse projeto foram obtidos do site do portal da transparência, segundo orientações do professor e podem ser consultados abaixo:
- [Leiaute do arquivo](http://www.portaltransparencia.gov.br/pagina-interna/603397-dicionario-de-dados-bolsa-familia-pagamentos)
- [Arquivo completo](http://www.portaltransparencia.gov.br/download-de-dados/bolsa-familia-pagamentos/201901)
