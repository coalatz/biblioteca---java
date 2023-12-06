import java.util.*;
import java.io.*;

abstract class ItemBiblioteca implements Serializable {
    protected boolean disponibilidade;
    protected float idItem;
    protected String integridade;

    public abstract boolean verificarIntegridade();
    public abstract void reparar();
}

class Livro extends ItemBiblioteca {
    private String titulo;
    private String autor;
    private String genero;
    private static final int serialVersionUID = 1;

    public Livro(float id, String integridade, String titulo, String autor, String genero) {
        this.idItem = id;
        this.integridade = integridade;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.disponibilidade = true;
    }

    @Override
    public boolean verificarIntegridade() {
        if(this.getIntegridade().equals("boa") || this.getIntegridade().equals("media")) {
            return true;
        }
        return false;
    }

    @Override
    public void reparar() {
        if(!verificarIntegridade()) {
            this.setIntegridade("medio");
            System.out.println("O livro: " + this.getTitulo() + " foi reparado");
            return;
        }
        System.out.println("O livro: " + this.getTitulo() + " nao precisa de reparo");
    }

    @Override
    public String toString() {
        return  "Id: " + this.idItem + " " +
                "Título: " + this.titulo + " " +
                "Autor: " + this.autor + " " +
                "Gênero: " + this.genero + " " +
                "Intêgridade: " + this.integridade + " " +
                "Disponiblidade: " + (getDisponibilidade() ? "Disponível" : "Indisponível") + "\n=";
                 
    }

    public String detalhes() {
    return "> Detalhes do livro:\n" +
           "Título: " + this.titulo + "\n" +
           "Autor: " + this.autor + "\n" +
           "Gênero: " + this.genero + "\n" +
           "Intêgridade: " + this.integridade + "\n" +
           (getDisponibilidade() ? "Disponível" : "Indisponível");
    }

    public void devolver() {
        this.setIntegridade("medio");
        this.setDisponibilidade(true);
        System.out.println("> O livro: " + this.getTitulo() + " foi devolvido com sucesso e esta com uma integridade " + this.getIntegridade());
        return;
    }

    public float getIdLivro() {
        return this.idItem;
    }
     public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean getDisponibilidade() {
        return this.disponibilidade;
    }

    public void setDisponibilidade(boolean disponivel) {
        this.disponibilidade = disponivel;
    }
    public String getIntegridade() {
        return this.integridade;
    }
    public void setIntegridade(String a) {
        this.integridade = a;
    }

}

class Biblioteca implements Serializable {
    private ArrayList<Livro> listaLivro;
    private ArrayList<Pessoa> listaPessoa;
    private ArrayList<Emprestimo> listaEmprestimos;

    public Biblioteca() {
        listaLivro = new ArrayList<Livro>();
        listaPessoa = new ArrayList<Pessoa>();
        listaEmprestimos = new ArrayList<Emprestimo>();
    }
    public void addLivro(Livro livro) {
        this.listaLivro.add(livro);
        System.out.println(livro.detalhes());
        this.salvarEstado();
        System.out.println("> Livro adicionado com sucesso");
    }
    public boolean verificarIdLivro(Livro livro) {
        Livro livroSelecionado = null;
        for(Livro livroCom : this.listaLivro) {
            if(livro.getIdLivro() == livroCom.getIdLivro()) {
                livroSelecionado = livroCom;
            }
        }
        if(livroSelecionado != null) {
            return false;
        }
        return true;
    }
    public void salvarEstado() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("estado_biblioteca.ser"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Biblioteca carregarEstado() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("estado_biblioteca.ser"))) {
            return (Biblioteca) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Biblioteca();
        }
    }

    public void removerLivro(Livro livro) {
        this.listaLivro.remove(livro);
        this.salvarEstado();
        System.out.println("> Livro removido com sucesso");
    }
    public void removerEmprestimo(Emprestimo emprestimo) {
        this.listaEmprestimos.remove(emprestimo);
        this.salvarEstado();
    }
    public boolean verificarIdPessoa(Pessoa pessoa) {
        Pessoa pessoaSelecionado = null;
        for(Pessoa pessoaCom : this.listaPessoa) {
            if(pessoa.getIdPessoa() == pessoaCom.getIdPessoa()) {
                pessoaSelecionado = pessoaCom;
            }
        }
        if(pessoaSelecionado != null) {
            return false;
        }
        return true;
    }

    public void addPessoa(Pessoa pessoa) {
        this.listaPessoa.add(pessoa);
        this.salvarEstado();
        System.out.println("> Pessoa cadastrada com sucesso");
    }
    public boolean verificarIdEmprestimo(Emprestimo emprestimo) {
        Emprestimo emprestimoSelecionado = null;
        for(Emprestimo emprestimoCom : this.listaEmprestimos) {
            if(emprestimo.getIdEmprestimo().equals(emprestimoCom.getIdEmprestimo())) {
                emprestimoSelecionado = emprestimoCom;
            }
        }
        if(emprestimoSelecionado != null) {
            return false;
        }
        return true;
    }

    public void addEmprestimo(Emprestimo emprestimo) {
        this.listaEmprestimos.add(emprestimo);
        this.salvarEstado();
    }

    public ArrayList<Pessoa> getListaPessoa() {
        return this.listaPessoa;
    }
    public ArrayList<Livro> getListaLivro() {
        return this.listaLivro;
    }
    public ArrayList<Emprestimo> getListaEmprestimo() {
        return this.listaEmprestimos;
    }
    public void setListaEmprestimo(ArrayList<Emprestimo> v1) {
        this.listaEmprestimos = v1;
    }
}
class Emprestimo implements Serializable {
    private String idEmprestimo;
    private Pessoa pessoaEmprestimo;
    private Livro livroEmprestado;
    private int diaEmprestimo;
    private int mesEmprestimo;
    private int anoEmprestimo;
    private double multa;

    public Emprestimo(Pessoa pessoa, Livro livro, String id, int diaEmprestimo, int mesEmprestimo, int anoEmprestimo) {
        this.idEmprestimo = id;
        this.diaEmprestimo = diaEmprestimo;
        this.mesEmprestimo = mesEmprestimo;
        this.anoEmprestimo = anoEmprestimo;
        this.multa = 0.0;
        this.pessoaEmprestimo = pessoa;
        this.livroEmprestado = livro;
    }

    public double calcularMulta(int diaDevolucao, int mesDevolucao, int anoDevolucao) {
        int ano = anoDevolucao - this.anoEmprestimo;
        int mes = mesDevolucao - this.mesEmprestimo; 
        int dia = diaDevolucao - this.diaEmprestimo;
        
        if (ano > 0 || (ano == 0 && (mes > 0 || (mes == 0 && dia > 30)))) {
            double valorDia = 1.00;
            this.multa = (ano * 365 + mes * 30 + dia) * valorDia;

            return multa;
        } else {
            return 0.0; 
        }
    }

    public void pagarMulta() {
        this.multa = 0;
        System.out.println("> Multa paga com sucesso");
    }

    public void realizarEmprestimo(Livro livro, Pessoa pessoa) {
        livro.setDisponibilidade(false);
        this.pessoaEmprestimo = pessoa;
        this.livroEmprestado =livro;
        System.out.println("> 30 dias para a devolucao do livro");
        return;
    }
    @Override
    public String toString() {
        return  ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n" +
                "ID Emprestimo: " + this.idEmprestimo + " " +
                " Dia: " + this.diaEmprestimo + " " +
                " Mes: " + this.mesEmprestimo +" " +
                " Ano: " + this.anoEmprestimo + "\n" +
                " Informacoes da Pessoa: " + this.pessoaEmprestimo + "\n" +
                " Informacoes do Livro: " + this.livroEmprestado + "\n" +
                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "\n";
                
    }
    public Livro getLivroEmprestado() {
        return this.livroEmprestado;
    }

    public double getMulta() {
        return this.multa;
    }
    public String getIdEmprestimo() {
        return this.idEmprestimo;
    }
}
class Pessoa implements Serializable {
    private String nome;
    private int idPessoa;
    private String rua;
    private int numero;
    private String bairro;

    public Pessoa(String nome, int idPessoa, String rua, int numero, String bairro) {
            this.nome = nome;
            this.idPessoa = idPessoa;
            this.rua = rua;
            this.numero = numero;
            this.bairro = bairro;
        }
    
    public String detalhesPessoa() {
        String s = "Nome: " + this.nome + 
                " Id: " + this.idPessoa + 
                " Rua: " + this.rua + 
                " Numero: " + this.numero + 
                " Bairro: " + this.bairro;
        return s;
    }
    @Override
    public String toString() {
        return  "Nome: " + this.nome + 
                " Id: " + this.idPessoa + 
                " Rua: " + this.rua + 
                " Numero: " + this.numero + 
                " Bairro: " + this.bairro + "\n" +
                "=";
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
class ConsoleUtils {
    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class Solver {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        ConsoleUtils limpar = new ConsoleUtils();

        System.out.println("Bem-vindo à Biblioteca Virtual!");
        System.out.println("-------------------------------");
        System.out.println("    Livros e Conhecimento ");
        System.out.println("-------------------------------\n");
        System.out.println("> digite 'ajuda' se precisar de orientacao");

        biblioteca = Biblioteca.carregarEstado();

        while (true) {
            System.out.print("@ ");
            String line = scanner.nextLine();
            limpar.clearConsole();
            String[] ui = line.split(" ");

            if (ui[0].equals("exit")) {
                break;
            } 
            else if (ui[0].equals("ajuda")) {
                limpar.clearConsole();
                System.out.println("\n> Digite o numero do assunto que deseja ajuda:");
                System.out.println("1 - Pessoa");
                System.out.println("2 - Livro");
                System.out.println("3 - Empréstimo");
                
            }
                
            else if (ui[0].equals("1")) {
                System.out.println("> O id da pessoa e um numero inteiro id = 1.\n");
                System.out.println("Comandos relacionados a Pessoa:");
                System.out.println("> addPessoa: Cria e cadastra uma Pessoa na sua biblioteca.\n" +
                                    "#exemplo: addPessoa [nome_completo: joao_alves] [id: 1] [rua_completa] [numero] [bairro]\n" + 
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> detalhesPessoa: Informações sobre uma Pessoa.\n" +
                                    "#exemplo: detalhesPessoa [id]\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> mostrarPessoas: Mostrar Pessoas cadastradas na biblioteca.\n" +
                                    "#exemplo: mostrarPessoas\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> exit: Sair do programa.");
                                    
            } 
            else if (ui[0].equals("2")) {
                System.out.println("> O id do livro e um numero com ponto flutuante id = 1.1.\n" + 
                                   "> A integridade do livro pode ser boa, medio e ruim.\n");
                System.out.println("Comandos relacionados ao livro:");
                System.out.println("> addLivro: Cria e cadastra um Livro na sua biblioteca.\n" +
                                    "#exemplo: addLivro [idLivro] [integridade] [titulo_livro] [autor] [genero]\n" +
                                    "-------------------------------------------------------------------------------------------------\n" + 
                                    "> detalhesLivro: Informações sobre um Livro.\n" +
                                    "#exemplo: detalhesLivro [idLivro]\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> devolver: Devolve um Livro que foi emprestado.\n" +
                                    "#exemplo: devolver [idLivro] [idPessoa] [idEmprestimo] [diaDevolucao] [mesDevolucao] [anoDevolucao]\n" +
                                    "----------------------------------------------------------------------------------------------------\n" +
                                    "> reparar: Repara um livro.\n" +
                                    "#exemplo: reparar [idLivro]\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> remoLivro: Remove um Livro da sua Biblioteca.\n" +
                                    "#exemplo: remoLivro [idLivro]\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> mostrarLivros: Mostrar livros cadastrados na biblioteca.\n" +
                                    "-------------------------------------------------------------------------------------------------\n" +
                                    "> exit: Sair do programa.");
            } 
            else if (ui[0].equals("3")) {
                System.out.println("> O id do emprestimo e uma letra ou conjunto de letras id = ab.\n"); ;
                System.out.println("Comandos relacionados ao empréstimo:");
                System.out.println("> fazerEmprestimo: Cria um Empréstimo de um Livro com a Data, Livro e Pessoa.\n" +
                                    "#exemplo: fazerEmprestimo [idLivro] [idPessoa] [idEmprestimo] [diaEmprestimo] [mesEmprestimo] [anoEmprestimo]\n" +
                                    "-------------------------------------------------------------------------------------------------------------\n" +
                                    "> mostrarEmprestimos: Mostrar empréstimos feitos na biblioteca.\n" +
                                    "#exemplo: mostrarEmprestimos\n" +
                                    "-------------------------------------------------------------------------------------------------------------\n" +
                                    "> exit: Sair do programa.\n");
            } 
        
            
            
            else if (ui[0].equals("addLivro")) {
                try{
                    Livro livro = new Livro(Float.parseFloat(ui[1]), ui[2], ui[3], ui[4], ui[5]);
                   
                    if(biblioteca.verificarIdLivro(livro)) {
                        biblioteca.addLivro(livro);
                    } else {
                        System.out.println("ERRO: id ja existe tente novamente com um diferente");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERRO: Formato invalido para o id do livro");
                    System.out.println("> exemplo de formato de id valido: id = 2.3");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERRO: não foram fornecidos informações sobre o livro suficiente");
                    System.out.println("> Informacoes necessarias: [id] [integridade] [titulo_livro] [nome_autor] [genero]");
                } catch(Exception e) {
                    System.out.println("ERRO: não foi possivel adicionar o livro");
                }
            }
            else if (ui[0].equals("addPessoa")) {
                try{
                    Pessoa pessoa = new Pessoa(ui[1], Integer.parseInt(ui[2]), ui[3], Integer.parseInt(ui[4]), ui[5]);
                    if(biblioteca.verificarIdPessoa(pessoa) == true) {
                        biblioteca.addPessoa(pessoa);
                    } else {
                        System.out.println("ERRO: id ja existe tente novamente com um diferente");
                    }
                } catch(NumberFormatException e) {
                    System.out.println("ERRO: formato invalido para o id da pessoa");
                    System.out.println("> exemplo de formato de id valido: id = 2");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERRO: não foram fornecidos informações suficientes para cadastrar a pessoa");
                    System.out.println("> Informacoes necessarias: [nome] [id] [rua] [numero] [bairro]");
                } catch(Exception e) {
                    System.out.println("ERRO: não foi possivel cadastrar a pessoa");
                }

            } 
            else if (ui[0].equals("mostrarLivros")) {
                try {
                    for(Livro livros : biblioteca.getListaLivro()) {
                        System.out.println(livros + "\n");
                    }
                } catch(Exception e) {
                    System.out.println("ERRO: não foi possivel mostrar os livros da biblioteca");
                }
            } 
            else if (ui[0].equals("mostrarPessoas")) {
                
                try {
                    for(Pessoa pessoa : biblioteca.getListaPessoa()) {
                        System.out.println(pessoa + "\n");
                    }
                } catch(Exception e ) {
                    System.out.println("ERRO: não foi possivel mostrar as pessoas cadastradas na biblioteca");
                }
            }
            else if (ui[0].equals("mostrarEmprestimos")) {
                limpar.clearConsole();
                try {
                    for(Emprestimo emprestimo : biblioteca.getListaEmprestimo()) {
                        System.out.println(emprestimo + "\n");
                    }
                } catch(Exception e ) {
                    System.out.println("ERRO: não foi possivel mostrar os emprestimos da biblioteca");
                }
            }
            else if (ui[0].equals("fazerEmprestimo")) {
                try{
                    if (ui.length == 7) {
                        float livroId = Float.parseFloat(ui[1]);
                        int pessoaId = Integer.parseInt(ui[2]);
                        String idEmprestimo = ui[3];
                        int diaEmprestimo = Integer.parseInt(ui[4]);
                        int mesEmprestimo = Integer.parseInt(ui[5]);
                        int anoEmprestimo = Integer.parseInt(ui[6]);    

                        Livro livroSelecionado = null;
                        Pessoa pessoaSelecionada = null;

                        for (Livro livro : biblioteca.getListaLivro()) {
                            if (livro.getIdLivro() == livroId) {
                                livroSelecionado = livro;
                                break;
                            }
                        }

                        for (Pessoa pessoa : biblioteca.getListaPessoa()) {
                            if (pessoa.getIdPessoa() == pessoaId) {
                            pessoaSelecionada = pessoa;
                            break;
                            }
                        }

                        if (livroSelecionado != null && pessoaSelecionada != null) {
                            if(livroSelecionado.getDisponibilidade()) {
                                Emprestimo emprestimo = new Emprestimo(pessoaSelecionada, livroSelecionado, idEmprestimo, diaEmprestimo, mesEmprestimo, anoEmprestimo);
                                if(biblioteca.verificarIdEmprestimo(emprestimo)) {
                                    emprestimo.realizarEmprestimo(livroSelecionado, pessoaSelecionada);
                                    biblioteca.addEmprestimo(emprestimo);
                                    System.out.println("> emprestimo realizado com sucesso");
                                } else if(!biblioteca.verificarIdEmprestimo(emprestimo)) {
                                    System.out.println("ERRO: id do emprestimo ja existe tente novamente com um diferente");
                                }
                            } else {
                                System.out.println("> livro ja esta emprestado");
                        }
                        } else {
                            System.out.println("> livro ou pessoa não encontrados.");
                        }
                    }
                } catch(NumberFormatException e) {
                    System.out.println("ERRO: formato invalido do id para realizar o emprestimo");
                    System.out.println("> exemplo de formato de id valido: id = vd");
                } catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("ERRO: não foram fornecidos informações suficientes para fazer o emprestimo");
                     System.out.println("> Informacoes necessarias: [idLivro] [idPessoa] [idEprestimo] [diaEprestimo] [mesEmprestimo] [anoEmprestimo]");
                } catch(Exception e) {
                    System.out.println("ERRO: nao foi possivel realizar o emprestimo");
                }
            }
            else if (ui[0].equals("detalhesPessoa")) {
                try{
                    if (ui.length == 2) {
                        int pessoaId = Integer.parseInt(ui[1]);
                        Pessoa pessoaEncontrada = null;
                        for (Pessoa pessoa : biblioteca.getListaPessoa()) {
                            if (pessoa.getIdPessoa() == pessoaId) {
                                pessoaEncontrada = pessoa;
                                break;
                            }
                        }
                    if (pessoaEncontrada != null) {
                        System.out.println("Detalhes da Pessoa:");
                        System.out.println(pessoaEncontrada.detalhesPessoa());
                    } else {
                        throw new Exception("> Pessoa não encontrada no banco de dados da biblioteca.");
                      }
                    } 
                } catch (Exception e) {
                    System.out.println("ERRO: ocorreu um erro ao obter detalhes da pessoa");
                }
            }

            else if(ui[0].equals("detalhesLivro")) {
                try {
                    if(ui.length == 2) {
                        float livroId = Float.parseFloat(ui[1]);
                        Livro livroEncontrado = null;
    
                        for(Livro livro : biblioteca.getListaLivro()) {
                            if(livro.getIdLivro() == livroId) {
                                livroEncontrado = livro;
                            }
                        }
                        if(livroEncontrado != null) {
                            System.out.println(livroEncontrado.detalhes());
                        } else {
                            System.out.println("ERRO: Livro não encontrado");
                        }
                    }
                } catch(Exception e) {
                    System.out.println("ERRO: ocorreu um problema ao obter detalhes do livro");
                }
            }
            else if(ui[0].equals("removerLivro")) {
                try {
                    if(ui.length == 2) {
                        float livroId = Float.parseFloat(ui[1]);
                        Livro livroEncontrado = null;
    
                        for(Livro livro : biblioteca.getListaLivro()) {
                            if(livro.getIdLivro() == livroId) {
                                livroEncontrado = livro;
                            }
                        }
                        if(livroEncontrado != null) {
                            biblioteca.removerLivro(livroEncontrado);
                        }
                    }
                } catch(Exception e) {
                    System.out.println("ERRO: ocorreu um problema ao remover o livro");
                }
            }
            else if(ui[0].equals("reparar")) {
                try {
                    if(ui.length == 2) {
                        float livroId = Float.parseFloat(ui[1]);
                        Livro livroreparo = null;
                        
                        for(Livro livro : biblioteca.getListaLivro()) {
                            if((livro.getIdLivro() == livroId) && livro.getIntegridade().equals("ruim")) {
                                livroreparo = livro;
                            }
                        }
                        if(livroreparo != null) {
                            livroreparo.reparar();
                        }
                    }
                } catch(Exception e) {
                    System.out.println("ERRO: ocorreu um problema ao reparar o livro");
                }
            }
            else if (ui[0].equals("devolver")) {
                try {
                    if (ui.length == 7) {
                        float livroId = Float.parseFloat(ui[1]);
                        int pessoaId = Integer.parseInt(ui[2]);
                        String idEmprestimo = ui[3];
                        int diaDevolucao = Integer.parseInt(ui[4]);
                        int mesDevolucao = Integer.parseInt(ui[5]);
                        int anoDevolucao = Integer.parseInt(ui[6]);

                        Livro livroSelecionado = null;
                        Pessoa pessoaSelecionada = null;
                        Emprestimo emprestimoSelecionado = null;

                        for (Livro livro : biblioteca.getListaLivro()) {
                            if (livro.getIdLivro() == livroId) {
                                livroSelecionado = livro;
                                break;
                            }
                        }
                        for (Pessoa pessoa : biblioteca.getListaPessoa()) {
                            if (pessoa.getIdPessoa() == pessoaId) {
                                pessoaSelecionada = pessoa;
                                break;
                            }
                        }
                        for(Emprestimo emprestimo : biblioteca.getListaEmprestimo()) {
                                if(emprestimo.getIdEmprestimo().equals(idEmprestimo)) {
                                    emprestimoSelecionado = emprestimo;
                                }
                            }

                        if(livroSelecionado != null && pessoaSelecionada != null) {
                            emprestimoSelecionado.calcularMulta(diaDevolucao, mesDevolucao, anoDevolucao);
                            if(emprestimoSelecionado.getMulta() == 0.0) {
                                livroSelecionado.devolver();
                                biblioteca.removerEmprestimo(emprestimoSelecionado);
                            } else {
                                System.out.println("> multa de " + emprestimoSelecionado.getMulta() + " reais a ser paga");
                                System.out.println("> deseja pagar multa? (s/n)");
                                String line2 = scanner.nextLine();
                                
                                if(line2.charAt(0) =='s') {
                                    emprestimoSelecionado.pagarMulta();
                                    livroSelecionado.devolver();
                                    biblioteca.removerEmprestimo(emprestimoSelecionado);
                                } else {
                                    System.out.println("> devolva novamente para pagar a multa");
                                }


                            }
                        }
                    }
                } catch(Exception e) {
                    System.out.println("ERRO: ocorreu um problema ao devolver o livro");
                }
            }

            else {
                System.out.println("ERRO: comando invalido");
            }
            
        }
        scanner.close();     
    }
}
              
