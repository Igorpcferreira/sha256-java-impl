# SHA-256 (implementação do zero) — Java 17, Maven

Projeto educacional que **implementa o SHA‑256 do zero** (sem `java.security.MessageDigest`) em Java 17,
seguindo uma estrutura limpa (Clean Code) e com **CLI** + **testes JUnit**.

## Estrutura

```
src/
  main/java/br/com/igor/crypto/hash/
    HashFunction.java            # contrato para funções de hash
    sha256/
      Sha256.java                # implementação SHA‑256 (core)
      BitUtils.java              # utilitários de bits/inteiros
      HexUtils.java              # utilitário de conversão hexa
    app/
      Cli.java                   # aplicação de linha de comando
```

## Como importar no IntelliJ

1. `File > New > Project from Existing Sources…`
2. Aponte para a pasta do projeto e selecione **Maven**.
3. JDK: **17**.

## Como executar

No **Windows (PowerShell)**:

```bash
mvn -q "-Dexec.args=-s hello" exec:java
mvn -q "-Dexec.args=-f README.md" exec:java
```

No **Linux/macOS (bash/zsh)**:

```bash
mvn -q -Dexec.args="-s 'hello world'" exec:java
mvn -q -Dexec.args="-f ./README.md" exec:java
```

- Via `java` direto (após `mvn package`):
```bash
mvn package
java -cp target/sha256-java-impl-1.0.0.jar br.com.igor.crypto.hash.app.Cli -s "hello"
```

### Opções da CLI
```
Usage: sha256 [-s <texto>] [-f <arquivo>] [--raw]
  -s, --string <texto>   Faz hash do texto em UTF-8
  -f, --file <arquivo>   Faz hash do conteúdo de um arquivo
      --raw              Imprime bytes do hash em Base64 (além do hex)
  -h, --help             Ajuda
```

## Testes

Execute:
```bash
mvn test
```

Incluí vetores de teste clássicos (RFC 6234):
- `""` (string vazia)
- `"abc"`
- `"abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq"`
- um texto longo com 1 milhão de caracteres `'a'` (teste pesado, desabilitado por padrão).
