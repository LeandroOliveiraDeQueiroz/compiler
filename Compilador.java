/*
INTEGRANTES:
586595 - Gabriel Bohn
598888 - Leandro Queiroz
591526 - Lucas Ferreira
*/

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Compilador {
    public static void main(String args[]) {
        // PEGAR DE ARQUIVO FONTE

        try{
          // TODO: Modelar a Utilização da tabela de símbolos
          // A cada utilização da tabela de simbolos queremos ler um token e voltar para o programa principal
          // Na próxima chamada queremos voltar ao mesmo ponto do programa principal
          // scanner.useDelimiter(""); scanner.hasNext(); scanner.next(); - Ler um char, porem nao le /n eu acho 

            // File Teste = new File("./Drive/exemplo015.l");
            // AnalisadorLexico analisadorLexico = new AnalisadorLexico(Teste);

          AnalisadorLexico analisadorLexico = new AnalisadorLexico(System.in);
          AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(analisadorLexico);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}

class Tupla<K, V>{
    private K key;
    private V value;
  
    public Tupla(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }
}

final class TabelaSimbolos {
    private static Map<String, HashMap<String, Byte>> tabela = new HashMap<String, HashMap<String, Byte>>();
    
    public TabelaSimbolos(){
        
        tabela.put("final", new HashMap<String, Byte>() {{ put("final", (byte)0); }});
        tabela.put("int", new HashMap<String,Byte>() {{ put("int", (byte)0); }});
        tabela.put("char", new HashMap<String,Byte>() {{ put("char", (byte)0); }});
        tabela.put("boolean", new HashMap<String,Byte>() {{ put("boolean", (byte)0); }});
        tabela.put("for", new HashMap<String,Byte>() {{ put("for", (byte)0); }});
        tabela.put("if", new HashMap<String,Byte>() {{ put("if", (byte)0); }});
        tabela.put("TRUE", new HashMap<String,Byte>() {{ put("TRUE", (byte)0); }});
  
        tabela.put("else", new HashMap<String,Byte>() {{ put("else", (byte)0); }});
        tabela.put("and", new HashMap<String,Byte>() {{ put("and", (byte)0); }});
        tabela.put("or", new HashMap<String,Byte>() {{ put("or", (byte)0); }});
        tabela.put("not", new HashMap<String,Byte>() {{ put("not", (byte)0); }});
        tabela.put(":=", new HashMap<String,Byte>() {{ put(":=", (byte)0); }});
        tabela.put("=", new HashMap<String,Byte>() {{ put("=", (byte)0); }});
  
        tabela.put("(", new HashMap<String,Byte>() {{ put("(", (byte)0); }});
        tabela.put(")", new HashMap<String,Byte>() {{ put(")", (byte)0); }});
        tabela.put("<", new HashMap<String,Byte>() {{ put("<", (byte)0); }});
        tabela.put(">", new HashMap<String,Byte>() {{ put(">", (byte)0); }});
        tabela.put("<>", new HashMap<String,Byte>() {{ put("<>", (byte)0); }});
        tabela.put(">=", new HashMap<String,Byte>() {{ put(">=", (byte)0); }});
  
        tabela.put("<=", new HashMap<String,Byte>() {{ put("<=", (byte)0); }});
        tabela.put(",", new HashMap<String,Byte>() {{ put(",", (byte)0); }});
        tabela.put("+", new HashMap<String,Byte>() {{ put("+", (byte)0); }});
        tabela.put("-", new HashMap<String,Byte>() {{ put("-", (byte)0); }});
        tabela.put("*", new HashMap<String,Byte>() {{ put("*", (byte)0); }});
        tabela.put("/", new HashMap<String,Byte>() {{ put("/", (byte)0); }});
  
        tabela.put(";", new HashMap<String,Byte>() {{ put(";", (byte)0); }});
        tabela.put("{", new HashMap<String,Byte>() {{ put("{", (byte)0); }});
        tabela.put("}", new HashMap<String,Byte>() {{ put("}", (byte)0); }});
        tabela.put("then", new HashMap<String,Byte>() {{ put("then", (byte)0); }});
        tabela.put("readln", new HashMap<String,Byte>() {{ put("readln", (byte)0); }});
        tabela.put("FALSE", new HashMap<String,Byte>() {{ put("FALSE", (byte)0); }});
  
        tabela.put("write", new HashMap<String,Byte>() {{ put("write", (byte)0); }});
        tabela.put("writeln", new HashMap<String,Byte>() {{ put("writeln", (byte)0); }});
        tabela.put("%", new HashMap<String,Byte>() {{ put("%", (byte)0); }});
        tabela.put("[", new HashMap<String,Byte>() {{ put("[", (byte)0); }});
        tabela.put("]", new HashMap<String,Byte>() {{ put("]", (byte)0); }});
        tabela.put("main", new HashMap<String,Byte>() {{ put("main", (byte)0); }});
        tabela.put("id", new HashMap<String,Byte>());
    }

    public Tupla<String, Byte> pesquisar(String lexema){
        for(Map.Entry<String, HashMap<String, Byte>> entry: tabela.entrySet()){
            String chave = entry.getKey();
            HashMap<String, Byte> valor = entry.getValue();

            for(Map.Entry<String, Byte> entryLinha: valor.entrySet()){
                String chaveLinha = entryLinha.getKey();
                Byte valorLinha = entryLinha.getValue();
                if(lexema.equals(chaveLinha)){
                    return new Tupla<String, Byte>(chave, valorLinha);
                }
            }
        }
        return null;
    }

    public Tupla<String, Byte> inserirToken(String token, String lexema){

        HashMap<String, Byte> tabelaToken = tabela.get(token);
        tabelaToken.put(lexema, (byte)tabelaToken.size());
        return pesquisar(lexema);
    }
}

class RegistroLexico {
    private String token;
    private Byte numToken;
    private String lexema;
    private Byte endereco;
    private String tipo;
    private int tamanho;

    public RegistroLexico(String token, Byte numToken, String lexema, Byte endereco, String tipo, int tamanho){
        this.token = token;
        this.numToken = numToken;
        this.lexema = lexema;
        this.endereco = endereco;
        this.tipo = tipo;
        this.tamanho = tamanho;
    }

    public int getNumToken(){
        return numToken;
    }
    public String getToken(){
        return token;
    }

    public String getLexema(){
        return lexema;
    }

    public Byte getEndereco(){
        return endereco;
    }

    public String getTipo(){
        return tipo;
    }

    public int getTamanho(){
        return tamanho;
    }

    public void show(){
        System.out.println(this.lexema);
    }
    
}

class AnalisadorLexico {
    private ArrayList<RegistroLexico> registrosLexicos = new ArrayList<RegistroLexico>();
    private TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
    private int contadorLinha = 1;
    private int numEstadoInicial = 0;
    private int numEstadoFinal = 20;
    private String strEntrada = "";
    private Scanner scanner;

    char [] simbolosValidos = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F', 'G','H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g','h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        ':','<','>','.',',',';','(',')','[',']','{','}','+','-','%','=','*', '/',
        '\n', '\t', '\r','_', '\'', '"',' ', '?', '$',
    };

    // public AnalisadorLexico(File strFonte) throws Exception{
    public AnalisadorLexico(InputStream strFonte) throws Exception{
        this.scanner = new Scanner(strFonte);
        this.scanner.useDelimiter("");
        this.registrosLexicos = new ArrayList<RegistroLexico>();
    }

    public void checarCaracterValido(char caractere, int contadorLinha) throws Exception {
        if(!(new String(simbolosValidos).contains(caractere + ""))){
            throw new Exception(contadorLinha + "\ncaractere invalido.");
        }
    }

    public RegistroLexico lerString() throws Exception {
        int numEstadoAtual = 0;
        boolean devolve = false;
        boolean descarte = false;
        boolean constante = false;
        String strResultado = "";
        RegistroLexico registro = null;
        String tipo = "";
        
        while(numEstadoAtual != numEstadoFinal && (scanner.hasNext() || !strEntrada.isEmpty())){

            if(strEntrada.isEmpty())
                strEntrada = scanner.next();
            
            char caractere = strEntrada.charAt(0);


            checarCaracterValido(caractere, contadorLinha);
            
            switch(numEstadoAtual){
                case 0:
                    switch(caractere){
                        case '\n':
                            contadorLinha++;
                            numEstadoAtual = numEstadoInicial;
                            descarte = true;
                            break;
                        case '\r': /* //TODO: Windows e MAC tem '\r' no final da linha, pensando em colocar para não bugar */
                        case '\t': /* //TODO: Windows e MAC tem '\r' no final da linha, pensando em colocar para não bugar */
                            numEstadoAtual = numEstadoInicial;
                            descarte = true;
                            break;
                        case ' ':
                            numEstadoAtual = numEstadoInicial;
                            descarte = true;
                            break;
                        case ':':
                            numEstadoAtual = 21; //Fixed
                            break;
                        case '<':
                            numEstadoAtual = 16;
                            break;
                        case '>':
                            numEstadoAtual = 4;
                            break;
                        case '.':
                        case ',':
                        case ';':
                        case '(':
                        case ')':
                        case '[':
                        case ']':
                        case '{':
                        case '}':
                        case '+':
                        case '-':
                        case '%':
                        case '=':
                        case '*':
                            numEstadoAtual = numEstadoFinal;
                            break;
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            constante = true;
                            numEstadoAtual = 15;
                            break;
                        case '0':
                            constante = true;
                            numEstadoAtual = 12;
                            break;
                        case '\'':
                            constante = true;
                            numEstadoAtual = 10;
                            break;
                        case '"':
                            constante = true;
                            numEstadoAtual = 9;
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'R':
                        case 'S':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 's':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                        case 'z':
                            numEstadoAtual = 1;
                            break;
                        case '_':
                            numEstadoAtual = 3;
                            break;
                        case '/':
                            numEstadoAtual = 2;
                            break;
                        default:
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + caractere + "].");
                    } // END switch(caractere)
                    break;
                case 1:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                        case '_':
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'R':
                        case 'S':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 's':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                        case 'z':
                            numEstadoAtual = 1;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere) 
                    break;
                case 2:
                    switch(caractere){
                        case '*':
                            numEstadoAtual = 6;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 3:
                    switch(caractere){
                        case '_':
                            numEstadoAtual = 3;
                            break;
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                        case 'G':
                        case 'H':
                        case 'I':
                        case 'J':
                        case 'K':
                        case 'L':
                        case 'M':
                        case 'N':
                        case 'O':
                        case 'P':
                        case 'Q':
                        case 'R':
                        case 'S':
                        case 'T':
                        case 'U':
                        case 'V':
                        case 'W':
                        case 'X':
                        case 'Y':
                        case 'Z':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 's':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                        case 'z':
                            numEstadoAtual = 1;
                            break;
                        default:
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + "].");
                    } // END switch(caractere))
                    break;
                case 4:
                    switch(caractere){
                        case '=':
                            numEstadoAtual = numEstadoFinal;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 6:
                    switch(caractere){
                        case '*':
                            numEstadoAtual = 8;
                            break;
                        case '\n':
                            contadorLinha++;
                            numEstadoAtual = 6;
                            break;
                        default:
                            numEstadoAtual = 6;
                            break;
                    } // END switch(caractere)
                    break;
                case 7:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                            numEstadoAtual = 18;
                            break;
                        //case //'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            numEstadoAtual = 14;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 8:
                    switch(caractere){
                        case '/':
                            numEstadoAtual = numEstadoInicial;
                            descarte = true;
                            break;
                        case '*':
                            numEstadoAtual = 8;// FIXED
                            break;
                        default:
                            numEstadoAtual = 6;
                            break;
                    } // END switch(caractere)
                    break;
                case 9:
                    switch(caractere){
                        case '"':
                            numEstadoAtual = numEstadoFinal;
                            tipo = "string";
                            break;
                        case '\n':
                            throw new Exception(contadorLinha + "\ncaractere invalido.");
                        case '$':
                            throw new Exception(contadorLinha + "\ncaractere invalido.");
                        default:
                            numEstadoAtual = 9;
                            break;
                    } // END switch(caractere)
                    break;
                case 10:
                    switch(caractere){
                        case '\'': //FIXED
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + "].");
                        // '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' , '0' ,
                        //      'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' ,
                        //      'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                        //      'u' , 'v' , 'w' , 'x' , 'y' , 'z' , 
                        //      'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 
                        //      'K' , 'L' , 'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 
                        //      'U' , 'V' , 'W' , 'X' , 'Y' , 'Z':
                        default:
                            numEstadoAtual = 11;
                            break;
                    } // END switch(caractere)
                    break;
                case 11:
                    switch(caractere){
                        case '\'':
                            numEstadoAtual = numEstadoFinal;
                            tipo = "char";
                            break;
                        default:
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + "].");
                    } // END switch(caractere)
                    break;
                case 12:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                            numEstadoAtual = 7;
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            numEstadoAtual = 13;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal;
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 13:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            numEstadoAtual = 14;
                            break;
                        default:
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + "].");
                    } // END switch(caractere)
                    break;
                case 14:
                    switch(caractere){
                        case 'h' :
                            numEstadoAtual = numEstadoFinal;
                            tipo = "hexa";
                            break;
                        default:
                            throw new Exception(contadorLinha + "\nlexema nao identificado [" + strResultado + "].");
                    } // END switch(caractere)
                    break;
                case 15:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                            numEstadoAtual = 15;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 16:
                    switch(caractere){
                        case '>':
                        case '=':
                            numEstadoAtual = numEstadoFinal;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
                case 18:
                    switch(caractere){
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                        case '0':
                            numEstadoAtual = 15;
                            break;
                        case 'h' :
                            numEstadoAtual = numEstadoFinal;
                            tipo = "hexa";
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            tipo = "int";
                            break;
                    } // END switch(caractere)
                    break;
                case 21:
                    switch(caractere){
                        case '=':
                            numEstadoAtual = numEstadoFinal;
                            break;
                        default:
                            numEstadoAtual = numEstadoFinal; // DEVOLVE
                            devolve = true;
                            break;
                    } // END switch(caractere)
                    break;
            }  // END switch(numEstadoAtual)

            if(!devolve){
                strEntrada = "";
                strResultado += caractere;
            }

            if(descarte){
                strResultado = "";
                descarte = false;
            }
        }

        if(numEstadoAtual != numEstadoInicial){
            if(numEstadoAtual != numEstadoFinal)
                throw new Exception(contadorLinha + "\nfim de arquivo nao esperado.");

            Tupla<String, Byte> retorno = null;

            if(!constante){
                retorno = tabelaSimbolos.pesquisar(strResultado);

                if(retorno == null)
                    retorno = tabelaSimbolos.inserirToken("id", strResultado);
                    String token = (retorno.getKey().equals("TRUE") || retorno.getKey().equals("FALSE")) ? "const" : retorno.getKey();
                registro = new RegistroLexico(token, retorno.getValue(), strResultado, retorno.getValue(), tipo, strResultado.length());
            } 
            else {
                registro = new RegistroLexico("const", null, strResultado, null, tipo, strResultado.length());
            }

            registrosLexicos.add(registro);
            //registro.show();
        }

        if(registro == null){
            registro = new RegistroLexico("", null, "", null, "", 0);
        }

        return registro;
    }

    public int getContadorLinha(){
        return this.contadorLinha;
    }
}

class AnalisadorSintatico {
    private AnalisadorLexico analisadorLexico;
    private RegistroLexico registroLido;

    public AnalisadorSintatico(AnalisadorLexico analisadorLexico) throws Exception {
        this.analisadorLexico = analisadorLexico;
        this.registroLido = this.analisadorLexico.lerString();
        S();
        System.out.println(this.analisadorLexico.getContadorLinha() + " linhas compiladas.");
    }

    public void casaToken(String tokenEsperado) throws Exception {
        if(tokenEsperado.equals(this.registroLido.getToken())){
            this.registroLido = this.analisadorLexico.lerString();
        }
        else if(this.registroLido.getToken().equals("")){
            throw new Exception(this.analisadorLexico.getContadorLinha() + "\nfim de arquivo nao esperado");
        }
        else{
            throw new Exception(this.analisadorLexico.getContadorLinha() + "\ntoken nao esperado [" + this.registroLido.getLexema() + "].");
        }
    }

    public void S() throws Exception {
        while(  this.registroLido.getToken().equals("final")   || this.registroLido.getToken().equals("int") || 
                this.registroLido.getToken().equals("boolean") || this.registroLido.getToken().equals("char")){
            Declaracao();
            casaToken(";");
        }
        casaToken("main");
        BlocoComando();
    }

    public void Declaracao() throws Exception{
        if(this.registroLido.getToken().equals("final")){
            casaToken("final");
            casaToken("id");
            if(this.registroLido.getToken().equals(":=")){
                casaToken(":=");
            } else {
                casaToken("=");
            }
            if(this.registroLido.getToken().equals("-")){
                casaToken("-");
            } else if(this.registroLido.getToken().equals("+")){
                casaToken("+");
            }
            casaToken("const");
        } 
        else {
            DeclaracaoVar();
        }
    }

    public void DeclaracaoVar() throws Exception{
        if(this.registroLido.getToken().equals("int")){
            casaToken("int");
        } 
        else if(this.registroLido.getToken().equals("boolean")) {
            casaToken("boolean");
        } 
        else { // char
            casaToken("char");
        }

        Indentificador();

        while(this.registroLido.getToken().equals(",")){
            casaToken(",");
            Indentificador();
        }
    }

    public void Indentificador() throws Exception {
        casaToken("id");

        if(this.registroLido.getToken().equals("=") || this.registroLido.getToken().equals(":=")){
            if(this.registroLido.getToken().equals(":=")){
                casaToken(":=");
            } else {
                casaToken("=");
            }
            
            if(this.registroLido.getToken().equals("-")){
                casaToken("-");
            } else if(this.registroLido.getToken().equals("+")){
                casaToken("+");
            }
            casaToken("const");
            
        } else if(this.registroLido.getToken().equals("[")){
            casaToken("[");
            casaToken("const");
            casaToken("]");
        }
    }
    
    public void BlocoComando() throws Exception{
        casaToken("{");

        while(!this.registroLido.getToken().equals("}")){
            Comando();
        }

        casaToken("}");
    }

    public void Comando() throws Exception{
        if(this.registroLido.getToken().equals("id")){
            ComandoAtribuicao();
            casaToken(";");
        } else if(this.registroLido.getToken().equals("for")){
            ComandoRepeticao();
        }  else if(this.registroLido.getToken().equals("if")){
            ComandoTeste();
        }  else if(this.registroLido.getToken().equals("readln")){
            casaToken("readln");
            casaToken("(");
            casaToken("id");
            if(this.registroLido.getToken().equals("[")){
                casaToken("[");
                Exp();
                casaToken("]");
            }
            casaToken(")");
        }  else if(this.registroLido.getToken().equals("write")){
            casaToken("write");
            ExpEscrita();
            casaToken(";");
        }  else if(this.registroLido.getToken().equals("writeln")){
            casaToken("writeln");
            ExpEscrita();
            casaToken(";");
        }  else { // ;
            casaToken(";");
        }
    }

    public void ExpEscrita () throws Exception {
        casaToken("(");
        Exp();
        while(this.registroLido.getToken().equals(",")){
            casaToken(",");
            Exp();
        }
        casaToken(")");
    }

    public void ComandoAtribuicao() throws Exception{
        casaToken("id");
        if(this.registroLido.getToken().equals("[")){
            casaToken("[");
            Exp();
            casaToken("]");
        }
        casaToken(":=");
        Exp();
    }

    public void ComandoRepeticao() throws Exception{
        casaToken("for");
        casaToken("(");
        if(!this.registroLido.getToken().equals(";")){
            ComandosInternosRep();
            while(this.registroLido.getToken().equals(",")){
                casaToken(",");
                ComandosInternosRep();
            }
        }
        casaToken(";");
        Exp();
        casaToken(";");
        if(!this.registroLido.getToken().equals(")")){
            ComandosInternosRep();
            while(this.registroLido.getToken().equals(",")){
                casaToken(",");
                ComandosInternosRep();
            }
        }
        casaToken(")");
        if(!this.registroLido.getToken().equals("{")){
            Comando();
        }
        else{
            BlocoComando();
        }
    }

    public void ComandosInternosRep() throws Exception{
        if(this.registroLido.getToken().equals("id")){
            ComandoAtribuicao();
        // } else if(this.registroLido.getToken().equals("for")){ //TODO: Pode ter repeticao dentro de repeticao?
        //     ComandoRepeticao();
        // }  else if(this.registroLido.getToken().equals("if")){ //TODO: Pode ter teste dentro de repeticao?
        //     ComandoTeste();
        }  else if(this.registroLido.getToken().equals("readln")){ //TODO: Não pode readln primeira parte da repeticao
            casaToken("readln");
            casaToken("(");
            casaToken("id");
            if(this.registroLido.getToken().equals("[")){
                casaToken("[");
                Exp();
                casaToken("]");
            }
            casaToken(")");
        }  else if(this.registroLido.getToken().equals("write")){  //TODO: Não pode write primeira parte da repeticao
            casaToken("write");
            ExpEscrita();
        }  else {
            casaToken("writeln");  //TODO: Não pode writeln primeira parte da repeticao
            ExpEscrita();
        }
    }
    
    public void ComandoTeste() throws Exception{
        casaToken("if");
        casaToken("(");
        Exp();
        casaToken(")");
        casaToken("then");

        if(this.registroLido.getToken().equals("{")){
            BlocoComando();
        }
        else{
            Comando();
        }

        if(this.registroLido.getToken().equals("else")){
            casaToken("else");
            if(this.registroLido.getToken().equals("{")){
                BlocoComando();
            }
            else{
                Comando();
            }
        }
    }
    
    public void Exp() throws Exception{
        ExpS();
        if(this.registroLido.getToken().equals("=")){
            casaToken("=");
            ExpS();
        } else if (this.registroLido.getToken().equals("<>")) {
            casaToken("<>");
            ExpS();
        } else if (this.registroLido.getToken().equals("<")) {
            casaToken("<");
            ExpS();
        } else if (this.registroLido.getToken().equals(">")) {
            casaToken(">");
            ExpS();
        } else if (this.registroLido.getToken().equals("<=")) {
            casaToken("<=");
            ExpS();
        } else if (this.registroLido.getToken().equals(">=")) {
            casaToken(">=");
            ExpS();
        }
    }

    public void ExpS() throws Exception{
        if(this.registroLido.getToken().equals("+")){
            casaToken("+");
        }
        else if(this.registroLido.getToken().equals("-")){
            casaToken("-");
        }

        T();

        while(this.registroLido.getToken().equals("+") || this.registroLido.getToken().equals("-") || this.registroLido.getToken().equals("or")){
            if(this.registroLido.getToken().equals("+")){
                casaToken("+");
            } else if(this.registroLido.getToken().equals("-")){
                casaToken("-");
            } else {
                casaToken("or");
            }
            T();
        }
    }

    public void T() throws Exception {
        F();
        while(  this.registroLido.getToken().equals("*") || this.registroLido.getToken().equals("and") || 
                this.registroLido.getToken().equals("/") || this.registroLido.getToken().equals("%")){
            if(this.registroLido.getToken().equals("*")){
                casaToken("*");
            } else if(this.registroLido.getToken().equals("and")){
                casaToken("and");
            } else if(this.registroLido.getToken().equals("/")){
                casaToken("/");
            } else {
                casaToken("%");
            }
            F();
        }
    }

    public void F() throws Exception {
        if (this.registroLido.getToken().equals("id")){
            casaToken("id");
            if(this.registroLido.getToken().equals("[")){
                casaToken("[");
                Exp();
                casaToken("]");
            }
        } else if (this.registroLido.getToken().equals("const")){
            casaToken("const");
        } else if (this.registroLido.getToken().equals("not")){
            casaToken("not");
            F();
        } else {
            casaToken("(");
            Exp();
            casaToken(")");
        }
    }
}

/*

1) IMPLEMENTAR HASH MELHOR NA TABELA DE SIMBOLOS
2) DESCOBRIR COMO COLOCAR O REGISTRO LÉXICO NA TABELA


*/

/*
q4, q17, q16, q20, q19
q12, q13, q14

Inicio
    S<-0 //estado inicial
    Repita enquanto S<>2 //estado final
    Se não fim de arquivo
    então Leia(c); Se c inválido ERRO;
    senão c<-EOF
        Caso (S)
            0: Caso (c)
                a:
                    lexema<-c;
                    S<-1;
                d,EOF:
                    t<-inteiro;
                    S<-2;
                b:S<-3;
            1: Caso (c)
                b,d,EOF: 
                    S<-2;
		        a:
                    tok<-X;
            3: Caso (c)
                a: S<-2;
                b: ;
                d: ERRO(lexema não identificado)
                EOF: ERRO(Fim de arquivo não esperado);
    fim
fim
retornar registro léxico (lexema, token, posição TS, tipo constante…
 */

 /*Uma repetição que não faz nada não obrigatoriamente precisa estar no switch case, mas acho interessante colocar porque fica mais visível. Caso tenha alguma ação semântica(lex += c), precisa estar no switch case.
Fazendo assim, podemos colocar todos os casos que não se encaixam como erro.


Implementação da tabela de simbolos:
	Hash
	Tem que ser global: Vai ser utilizado toda hora pelo léxico, sintático e pelo gerador de código
	Tamanho da tabela de símbolos = 10*(Palavras reservada + ID) => Converter para o primo para diminuir colisões.
	Melhor fazer hashing fechado
	Procurar o hash na lista da posição e pegar o ponteiro(posição na memória).
	Se achar ok Pesq(lexema)
	Se não achar inserir(lex, token) e retornar o ponteiro(posição na memória).

 */