# Quarkus - Security JPA

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```
Como a ideia é dar os primeiros passos na segurança de APIs com Quarkus, a ideia é utilizar **HTTP Basic** e **Form Based** Authentication, que são 2 métodos bastante simples de configurar quando pensamos em segurança de aplicações.

Uma informação extra, definitivamente existem melhores alternativas para tornar uma API segura. Desde utilizar protocolos como OAuth2, ou identity layers como OIDC (utilizando frameworks com o KeyCloak) e até delegar a o controle de acesso a API Gateways.  

## Aplicação

Para implementação da nossa API com o básico da segurança valos utilizar as seguintes *extensions*

- RESTEasy JAX-RS
- Security JPA *(preview)*

A aplicação base é bem simples, terá 3 *endpoins*

1. /api/public - *Pode ser acessado sem crendenciais (acesso sem estar autenticado)*
2. /api/admin - *Apenas usuários com **Role** "Admin" tem acesso ao recurso*
3. /api/user - *Apenas usuários com **Role** "User" tem acesso ao recurso*

Vamos utilizar a extension **security-jpa** do Quarkus para definir o JPA como **IdentityProvider**.

A partir daqui devemos entender o que são Roles e como configurá-las na nossa aplicação Quarkus.

## RBAC Security

*Role-Based Access Contro*l (RBAC) é uma abordagem de controle de acesso baseada no conceitos de ***Roles*** e **Grants** de privilégios, onde os usuários possuem um conjunto de papéis *(Roles)* dentro da aplicação e esses são utilizadas na associação de privilégios *(Grants)* de acesso aos recursos.

### Passo-a-passo

1. Definir as Roles da aplicação
2. Definir os Grants para Roles
3. Criar Usuário
4. Associar o Usuário a Roles

Exemplo:
 O recurso Cardápio deve ser acessado sem necessidade de autenticação (public), porém, apenas o verbo GET.
Já para ter acesso à criação de um Pedido (POST no recurso Pedido) apenas usuários autenticados e com função (Role) de *Cliente.*

## Implementação

No Quarkus é possível definir esses *Grants* através de propriedades ou anotações.

**@PermitAll** - *Permite o acesso não autenticado ao recurso.*

**@RolesAllowed** - Permite acesso ao recurso para a lista de *Roles* passada por parâmetro. Qualquer usuário que tenha determinada *Role* poderá acessar o recurso.

1. Public

    ```java
    @Path("/api/public")
    @Produces(MediaType.TEXT_PLAIN)
    public class PublicResource {

    	@GET
    	@PermitAll
    	public String publicPath() {
    		return "public";
    	}
    }
    ```

2. Admin

    ```java
    @Path("/api/admin")
    @Produces(MediaType.TEXT_PLAIN)
    public class AdminResource {

        @GET
        @RolesAllowed("admin")
        public String adminPath() {
            return "admin";
        }
    }
    ```

3. User

    ```java
    @Path("/api/user")
    @Produces(MediaType.TEXT_PLAIN)
    public class UserResource {

        @GET
        @Path("me")
        @RolesAllowed("user")
        public String me(@Context SecurityContext securityContext) {
            return securityContext.getUserPrincipal().getName();
        }
    }
    ```

Para quem gosta de usar extensions experimentais, a combinação do uso de anotações com a extension ***REST resources for Hibernate ORM with Panache*** não parece funcionar (pelo menos não na versão [1.10.2.Final](http://1.10.2.Final) do Quarkus). Mas não se preocupe, para configurar a segurança os endpoints gerados a abordagem de definir através de properties funciona perfeitamente. 

## Definição do Usuário

Para começar a finalizar a configuração de segurança da nossa API precisamos do principal, definir nosso **IdentityProvider**, ou seja, quem vai garantir e identidade dos usuários na nossa aplicação. Como dito anteriormente, usaremos o JPA (Banco de Dados) como Identity Provider.

No Quarkus é necessário configurar a entidade que define o Usuário da aplicação e esta entidade precisa ter alguns atributos específicos. 

```java
@Entity
@Table(name = "app_user")
@UserDefinition
public class User extends PanacheEntity {

    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;

    public static void addUser(String username, String rawPassword, String role) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(rawPassword);
        user.role = role;

        user.persist();
    }
}
```

Nessa configuração é necessário informar através da anotação **@UserDefinition.** A anotação indica que essa classe será utilizada como fonte de informação de identidade à aplicação.

Assim, também é necessário definir alguns atributos.

- **Username** (Login do nosso usuário)
- **Password** (Senha de acesso do usuário)
- **Roles** (Aqui as Roles são definidas como uma String com cada Role separada por vírgula, também é possível criar uma entidade própria para estas Roles )

Existe uma restrição quanto a anotação **@UserDefinition**. Ela só pode ser utilizada uma única vez na aplicação, uma vez que vai utilizar a classe anotada como parte da configuração.

Outro ponto interessante é em relação ao *password*. Por padrão, a *extension* do Quarkus já espera que o *password* seja armazenado criptografado no banco de dados utilizando o Bcrypt na criptografia dos dados.

As Roles podem ser armazenadas de 2 formas. De maneira mais simples, em forma de String separada por vírgula, ou por meio de entidades próprias (*https://quarkus.io/guides/security-jpa#storing-roles-in-another-entity*).

### Criando Usuários

O método abaixo, simplesmente cria um usuário recebendo, além das informações básicas, o password em plain text. Como vimos, esse password precisa ser criptografado antes de ser armazenado no banco (além de ser o básico do básico, a extension anida nos obriga).

Para isso o podemos utilizar a classe **BcryptUtil** que a extension disponibiliza.

```java
    public static void addUser(String username, String rawPassword, String role) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(rawPassword);
        user.role = role;

        user.persist();
    }
```

## HTTP Basic Authentication

Configurados os *Endpoints* e o *IdentityProvider* ao executar a aplicação e tentar acessar os nosso *endpoints*.

Acessando o path */api/public* tudo funcionará normalmente, nada de novo em relação a utilizar sem nenhuma configuração de segurança.

Porém, ao acessar algum *path "*protegido" *através de um browser*, */api/admin,* irá aparecer um dialog solicitando dados de autenticação. 

![.github/login-basic.jpg](.github/login-basic.jpg)

Por padrão o método de autenticação utilizado é o HTTP Basic ([https://tools.ietf.org/html/rfc7617](https://tools.ietf.org/html/rfc7617)), semelhante ao que acontece no Sprint Security. 

No Quarkus a propriedade que configura o HTTP Basic é

```java
quarkus.http.auth.basic=true
```

No caso de uma *request* feita por outro *agent* (fetch API, Axios ou mesmo um aplicação como o Postman ou Isonmnia), se não enviarmos as informações com as credenciais do usuário, tomamos um 401 como resposta do servidor.

![.github/code.png](.github/code.png)

Para enviar as credenciais utilizando o HTTP Basic temos de informar, através de um Header da request os dados de autenticação com o login e senha do usuário (concatenados com o caractere dois pontos) e codificado em formato base64.

O header deve seguir o seguinte padrão:

```java
> header
Authentication: Basic [base64(user:password)]
```

![.github/code_1.png](.github/code_1.png)

Outra forma (que não é muito recomendada) é enviar essas credenciais na própria URL
*http://user:password@localhost:8080/api/admin*

Como as informações de autenticação do usuário não são criptografadas (são apenas codificadas em base64), é mandatório o uso de HTTPs (TLS) na comunicação. *Na verdade não estar sob https hoje em dia já é um grande problema para a maioria dos casos.*

Inclusive existe um trecho na própria RFC com considerações de segurança que diz o seguinte:

> The Basic authentication scheme is not a secure method of user
authentication, nor does it in any way protect the entity, which is
transmitted in cleartext across the physical network used as the
carrier. HTTP does not prevent the addition of enhancements (such as
schemes to use one-time passwords) to Basic authentication.

The most serious flaw of Basic authentication is that it results in
the cleartext transmission of the user's password over the physical
network. Many other authentication schemes address this problem.

Because Basic authentication involves the cleartext transmission of
passwords, it SHOULD NOT be used (without enhancements such as HTTPS
[RFC2818]) to protect sensitive or valuable information.

## Form Based Authentication

Outra abordagem para autenticação de usuários que mantém nosso critério de simplicidade é a tradicional *Form Based Authentication*.

A ideia aqui é submeter um formulário web com as credenciais do usuário para o servidor, que validará as informações e, caso os dados estejam corretos, criará uma sessão compartilhada com o cliente. Esse compartilhamento geralmente é feito através de um cookie com o ID da sessão no servidor (o que gera um problema conhecido quando temos várias 'replicas' do mesmo serviço, sendo necessário ou distribuir os dados da sessão entre os nós, ou 'amarrar' a sessão ao servidor em que o cliente 'caiu' e fez o login - *sticky session*).

Uma das principais diferenças dessa abordagem, em relação ao *HTTP Basic* está na possibilidade de realizar o logout, uma vez que com *HTTP Basic* a unica forma de fazer algo "parecido" com um logout é enviar uma requisição com as credenciais erradas (Naquele modo com os dados da URL) invalidando assim as requisições subsequentes. Já com *Form Based*, como existe uma informação compartilhada entre o Cliente e o Servidor (o session ID) então basta invalidar essa sessão que o usuário estará "deslogado".

Aqui vale o mesmo ponto importante, é mandatório o uso de HTTPs (TLS) na comunicação, uma vez que agora as credencias do usuário são enviadas no corpo da requisição.

### Form Based no Quarkus

No Quarkus existe uma diferença conceitual na forma em que o método de autenticação por formulário é implementado. 

Não existe vai existir uma sessão compartilhada entre Cliente e Servidor (afinal a ideia é ser Supersonic & Subatomic e não uma *Servlet* tradicional), porém a implementação proposta para prover a funcionalidade (e sobretudo resolver o problema das replicas da aplicação) é bem interessante.

```java
quarkus.http.auth.form.enabled=true
```

Ao iniciar a aplicação configurada com a autenticação *Form Based,* o Quarkus cria uma *encryption-key* (que pode ser informada nas properties ou é criada na inicialização) que é utilizada na geração de um Token de autenticação que é armazenado em um *Cookie* ***quarkus-credentials***.

Este Cookie contém os dados de autenticação do usuário. Ele é criptografado utilizando AES-256 utilizando como chave o *digest* SHA-256 da *encryption-key.*

![.github/code_2.png](.github/code_2.png)

Assim que uma requisição é realizada o Quarkus verifica a presença do Cookie e caso não exista ou esteja expirado, ele redireciona a requisição para o path */login.html*, já setando outro cookie com a requisição 'original' para futuro redirect.

Esse login pode ser modificado através da propriedade
*quarkus.http.auth.form.login-page=/login.html*

![.github/code_3.png](.github/code_3.png)

Essa página precisa ter um formulário com os dados de login e senha do usuário submetendo um POST para ***/j_security_check*** com os dados de ***j_username*** com o login e ***j_password*** para a senha.

Login realizado e credenciais devidamente validadas, agora a requisição é redirecionada novamente para o path 'original' requisitado já com a instrução de criação do *Cookie.*

![.github/code_4.png](.github/code_4.png)

Assim, agora as novas requisições serão devidamente validas utilizando as credenciais do usuário que estão armazenadas no *Cookie.* Validando devidamente se as Roles do usuário tem o privilégio necessário para acessar o recurso solicitado.

![.github/code_5.png](.github/code_5.png)

Aqui como o usuário foi identificado, mas não tem acesso ao recurso, o servidor retorna 403, não autorizando o acesso ao recurso pelo usuário, diferente do 401 que temos quando o usuário não é identificado (seja por enviar credenciais inválidas ou mesmo não enviar qualquer credencial).

![.github/code_6.png](.github/code_6.png)

## Referências

[https://quarkus.io/guides/security](https://quarkus.io/guides/security)

[https://quarkus.io/guides/security-jpa](https://quarkus.io/guides/security-jpa)

[https://quarkus.io/guides/security-jdbc](https://quarkus.io/guides/security-jdbc)

[https://quarkus.io/guides/security-built-in-authentication](https://quarkus.io/guides/security-built-in-authentication)