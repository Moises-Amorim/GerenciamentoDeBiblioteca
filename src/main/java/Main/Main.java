package Main;

import Model.Livros;
import Model.Usuario;
import Model.Emprestimo;
import Util.ConexaoBancoDeDados;

import java.sql.*;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Connection connection = ConexaoBancoDeDados.getConnection();
        Scanner leitor = new Scanner(System.in);
        leitor.useLocale(Locale.US);

        ConexaoBancoDeDados.testarConexao();

        // Criando ArrayLists para armazenar os livros cadastrados
        List<Livros> livrosList = new ArrayList<>();
        // Criando ArrayLists para armazenar os usuarios cadastrados
        List<Usuario> usuarioList = new ArrayList<>();
        // Criando ArrayLists para armazenar os emprestimos cadastrados
        List<Emprestimo> emprestimoList = new ArrayList<>();

        // carrega os livros do banco de dados na lista
        Livros.carregarLivrosBanco(livrosList);
        // carrega os usuarios do banco de dados na lista
        Usuario.carregarUsuariosBanco(usuarioList);
        // carrega os usuarios do banco de dados na lista
        Emprestimo.carregarEmprestimosBanco(emprestimoList);


        while (true) {

            System.out.println("\nEscolha uma opção:\n");
            System.out.println("1 - Gerenciar livro");
            System.out.println("2 - Gerenciar aluno");
            System.out.println("3 - Realizar empréstimo de livro");
            System.out.println("4 - Realizar devolução de livro");
            System.out.println("5 - Sair");

            int opcaoGerenciar = leitor.nextInt();
            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

            switch (opcaoGerenciar) {
                case 1:

                    System.out.println("\nEscolha uma opção:\n");
                    System.out.println("1 - Cadastrar livro");
                    System.out.println("2 - Mostrar todos os livros cadastrados");
                    System.out.println("3 - Buscar livro por código");
                    System.out.println("4 - Editar um dos livros cadastrados");
                    System.out.println("5 - Apagar um dos livros cadastrados");
                    System.out.println("6 - Sair");
                    int opcoesLivro = leitor.nextInt();

                    leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                    switch (opcoesLivro) {
                        case 1:
                            Livros livro = new Livros();

                            System.out.printf("Código do livro:\n");
                            livro.setCodigoLivro(leitor.nextInt());

                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            System.out.printf("Nome do livro:\n");
                            livro.setTitulo(leitor.nextLine().toUpperCase());

                            System.out.printf("autor/autora do livro:\n");
                            livro.setAutor(leitor.nextLine().toUpperCase());

                            System.out.printf("Tipo(gênero) do livro:\n");
                            livro.setGenero(leitor.nextLine().toUpperCase());

                            System.out.printf("Editora do livro:\n");
                            livro.setEditora(leitor.nextLine().toUpperCase());

                            System.out.printf("Quantidade do livro em estoque:\n");
                            livro.setQuantidadeCopias(leitor.nextInt());

                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            // Imprimir os detalhes do livro cadastrado
                            System.out.println("Detalhes do Livro Cadastrado:");
                            System.out.println("Codigo:" + livro.getCodigoLivro());
                            System.out.println("Titulo: " + livro.getTitulo());
                            System.out.println("Autor: " + livro.getAutor());
                            System.out.println("Tipo(gênero do livro " + livro.getGenero());
                            System.out.println("Editora: " + livro.getEditora());
                            System.out.println("Quantidade em estoque: " + livro.getQuantidadeCopias());

                            livrosList.add(livro);
                            livro.salvarlivro();
                            break;

                        case 2:
                            // Mostrar os livros cadastrados
                            List<Livros> livrosCadastrados = Livros.obterTodosOsLivros();
                            System.out.println("livros Cadastrados:\n");
                            for (Livros l : livrosCadastrados) {
                                System.out.println("\nDetalhes dos Livros Cadastrados:");
                                System.out.println("Codigo:" + l.getCodigoLivro());
                                System.out.println("Nome: " + l.getTitulo());
                                System.out.println("Autor/Autora: " + l.getAutor());
                                System.out.println("Tipo(gênero do livro " + l.getGenero());
                                System.out.println("Editora: " + l.getEditora());
                                System.out.println("Quantidade em estoque: " + l.getQuantidadeCopias());
                            }
                            break;

                        case 3:
                            // Filtrar livro por código
                            System.out.println("Digite o código do livro");
                            int buscarLivroId = leitor.nextInt();
                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            boolean livroEncontrado = false;

                            for (Livros l : livrosList) {
                                if (l.getCodigoLivro() == buscarLivroId) {
                                    Livros.buscarLivro(l.getCodigoLivro());
                                    System.out.println("\nDetalhes do Livro:");
                                    System.out.println("Codigo:" + l.getCodigoLivro());
                                    System.out.println("Nome: " + l.getTitulo());
                                    System.out.println("Autor/Autora: " + l.getAutor());
                                    System.out.println("Tipo(gênero do livro " + l.getGenero());
                                    System.out.println("Editora: " + l.getEditora());
                                    System.out.println("Quantidade em estoque: " + l.getQuantidadeCopias());
                                    livroEncontrado = true;
                                    break;
                                }
                            }
                            if (!livroEncontrado) {
                                System.out.println("Não foi encontrado nenhum livro com o código informado");
                            }

                            break;

                        case 4:
                            // Editar um dos livros cadastrados
                            System.out.println("Digite o código do livro que deseja editar");
                            int editarLivroId = leitor.nextInt();
                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()
                            // Percorre a lista de livros e encontra o cod_livro
                            for (Livros b : livrosList) {
                                if (b.getCodigoLivro() == editarLivroId) {
                                    System.out.printf("Nome do livro:\n");
                                    b.setTitulo(leitor.nextLine().toUpperCase());

                                    System.out.printf("autor/autora do livro:\n");
                                    b.setAutor(leitor.nextLine().toUpperCase());

                                    System.out.printf("Editora do livro:\n");
                                    b.setEditora(leitor.nextLine().toUpperCase());

                                    System.out.printf("Quantidade do livro em estoque:\n");
                                    b.setQuantidadeCopias(leitor.nextInt());

                                    b.atualizarLivro();

                                    System.out.println("Livro editado com sucesso.");
                                    break;
                                }
                            }

                            break;

                        case 5:
                            // Apagar um dos livros
                            System.out.println("Digite o codigo do livro que deseja apagar:\n");
                            int apagarLivroId = leitor.nextInt();

                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            // Percorre a lista de livros e encontra o nome
                            for (Livros l : livrosList) {
                                if (l.getCodigoLivro() == apagarLivroId) {
                                    l.apagarLivro(l.getCodigoLivro());
                                    System.out.println("Livro removido com sucesso.");
                                    break;
                                } else {
                                    System.out.println("Opção inválida!");
                                }
                            }
                            break;

                        case 6:
                            // Sair do programa
                            System.exit(0);
                            leitor.close();
                            break;

                        default:
                            System.out.println("\nNenhuma opção escolhida:\n");
                            break;
                    }

                    break;
                case 2:

                    System.out.println("\nEscolha uma opção:\n");
                    System.out.println("1 - Cadastrar Usuario");
                    System.out.println("2 - Mostrar todos os usuarios cadastrados");
                    System.out.println("3 - Buscar usuário pelo numero do cartão");
                    System.out.println("4 - Editar um dos usuarios cadastrados");
                    System.out.println("5 - Apagar um dos usuarios cadastrados");
                    System.out.println("6 - Sair");
                    int opcoesUsuario = leitor.nextInt();

                    leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                    switch (opcoesUsuario) {
                        case 1:
                            Usuario usuario = new Usuario();

                            System.out.printf("Numero do cartão:\n");
                            usuario.setNumeroCartao(leitor.nextInt());
                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            System.out.printf("Nome:\n");
                            usuario.setNomeUsuario(leitor.nextLine().toUpperCase());

                            System.out.printf("Telefone:\n");
                            usuario.setTelefoneUsuario(leitor.nextLine());

                            System.out.printf("email:\n");
                            usuario.setEmail(leitor.nextLine().toLowerCase());

                            // Imprimir os detalhes do usuario cadastrado
                            System.out.println("\nDetalhes do usuario cadastrado:");
                            System.out.println("Numero do cartão:" + usuario.getNumeroCartao());
                            System.out.println("Nome: " + usuario.getNomeUsuario());
                            System.out.println("Telefone: " + usuario.getTelefoneUsuario());
                            System.out.println("Email:" + usuario.getEmail());

                            usuarioList.add(usuario);
                            usuario.salvarUsuario();
                            break;

                        case 2:
                            // Mostrar os usuarios cadastrados
                            List<Usuario> usuarioCadastrados = Usuario.obterTodosOsUsuarios();
                            System.out.println("usuarios Cadastrados:");
                            for (Usuario u : usuarioCadastrados) {
                                System.out.println("\nDetalhes do usuario cadastrado:");
                                System.out.println("Numero do cartão:" + u.getNumeroCartao());
                                System.out.println("Nome: " + u.getNomeUsuario());
                                System.out.println("Telefone: " + u.getTelefoneUsuario());
                                System.out.println("Email: " + u.getEmail());
                            }
                            break;
                        case 3:
                            // Filtrar usuario por numero cartão
                            System.out.println("Digite o numero do cartão do usuário");
                            int buscarUsuarioId = leitor.nextInt();
                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            boolean usuarioEncontrado = false;

                            for (Usuario u : usuarioList) {
                                if (u.getNumeroCartao() == buscarUsuarioId) {
                                    Usuario.buscarUsuario(u.getNumeroCartao());
                                    System.out.println("\nDetalhes do usuario:");
                                    System.out.println("Numero do cartão: " + u.getNumeroCartao());
                                    System.out.println("Nome: " + u.getNomeUsuario());
                                    System.out.println("Telefone: " + u.getTelefoneUsuario());
                                    System.out.println("Email: " + u.getEmail());
                                    usuarioEncontrado = true;
                                    break;
                                }
                            }

                            if (!usuarioEncontrado) {
                                System.out.println("Não foi encontrado nenhum usuario com o numero de cartão informado");
                            }

                            break;
                        case 4:
                            // Editar um dos usuarios cadastrados
                            System.out.println("Digite o numero do cartao do usuario que deseja editar");
                            int editarUsuarioId = leitor.nextInt();
                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            // Percorre a lista de usuarios e encontra o numero_cartao
                            for (Usuario u : usuarioList) {
                                if (u.getNumeroCartao() == editarUsuarioId) {
                                    System.out.printf("Nome:\n");
                                    u.setNomeUsuario(leitor.nextLine().toUpperCase());

                                    System.out.printf("Telefone:\n");
                                    u.setTelefoneUsuario(leitor.nextLine());

                                    System.out.printf("Email:\n");
                                    u.setEmail(leitor.nextLine());

                                    u.atualizarUsuario();

                                    System.out.println("Dados do usuario editado com sucesso.");
                                    break;
                                }
                            }
                            break;
                        case 5:
                            // Apagar um dos usuarios
                            System.out.println("Digite o numero do cartão do usuario que deseja apagar:\n");
                            int apagarUsuarioId = leitor.nextInt();

                            leitor.nextLine(); // Consome a nova linha pendente após leitor.nextInt()

                            // Percorre a lista de livros e encontra o nome
                            for (Usuario u : usuarioList) {
                                if (u.getNumeroCartao() == apagarUsuarioId) {
                                    u.apagarUsuario(u.getNumeroCartao());
                                    System.out.println("Livro removido com sucesso.");
                                    break;
                                } else {
                                    System.out.println("Opção inválida!");
                                }
                            }
                            break;
                        case 6:
                            // Sair do programa
                            System.exit(0);
                            leitor.close();
                            break;

                        default:
                            System.out.println("Nenhuma opção escolhida:\n");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Digite o código do livro que vai ser emprestado ao usuario");
                    int codigoLivro = leitor.nextInt();
                    leitor.nextLine();

                    System.out.println("Digite o numero de cartão do usuario");
                    int numeroCartao = leitor.nextInt();
                    leitor.nextLine();

                    Livros livroSelecionado = null;
                    Usuario usuarioSelecionado = null;

                    // Encontra o livro e o usuário correspondente
                    for (Livros l : livrosList) {
                        if (l.getCodigoLivro() == codigoLivro) {
                            livroSelecionado = l;
                            break;
                        }
                    }
                    for (Usuario u : usuarioList) {
                        if (u.getNumeroCartao() == numeroCartao) {
                            usuarioSelecionado = u;
                            break;
                        }
                    }

                    if (livroSelecionado != null && usuarioSelecionado != null) {
                        Emprestimo emprestimo = new Emprestimo(livroSelecionado, usuarioSelecionado);
                        emprestimoList.add(emprestimo);
                        livroSelecionado.decrementarQuantidadeCopias();
                        emprestimo.salvarEmprestimo();
                        System.out.println("Empréstimo realizado com sucesso.");
                    } else {
                        System.out.println("Livro ou usuario não encontrado");
                    }
                    break;
                case 4:
                    System.out.println("Digite o numero do cartão do usuario");
                    int numeroCartaoConsulta = leitor.nextInt();
                    leitor.nextLine();

                    // Encontra o usuário correspondente
                    Usuario usuarioConsulta = null;
                    for (Usuario u : usuarioList) {
                        if (u.getNumeroCartao() == numeroCartaoConsulta) {
                            usuarioConsulta = u;
                            break;
                        }
                    }

                    // Se o usuário foi encontrado, verifica se há empréstimos ativos
                    if (usuarioConsulta != null) {
                        List<Emprestimo> emprestimosAtivos = Emprestimo.obterEmprestimosAtivos(usuarioConsulta, emprestimoList);

                        if (emprestimosAtivos.isEmpty()) {
                            System.out.println("O usuario não tem empréstimos ativos");
                        } else {
                            System.out.println("Selecione o empréstimo que deseja finalizar");
                            //Caso o usuário tenha empréstimos ativos, é incrementado essa lista com todos empréstimos
                            for (int i = 0; i < emprestimosAtivos.size(); i++) {
                                Emprestimo emprestimo = emprestimosAtivos.get(i);
                                Livros livro = emprestimo.getLivro();
                                if (livro != null) {
                                    System.out.println((i + 1) + " - Livro: " + livro.getTitulo());
                                } else {
                                    System.out.println((i + 1) + " - Livro: null");
                                }
                            }
                            int indiceEmprestimo = leitor.nextInt() - 1;
                            leitor.nextLine();
                            Emprestimo emprestimoFinalizado = emprestimosAtivos.get(indiceEmprestimo);

                            try {
                                // Define a data de devolução manualmente
                                System.out.println("Digite a data de devolução no formato YYYY-MM-DD");
                                String dataDevolucaoStr = leitor.nextLine();
                                Date dataDevolucao = Date.valueOf(dataDevolucaoStr);
                                emprestimoFinalizado.setDataDevolucao(dataDevolucao);

                                // Com data fornecida faz realiza o caálculo para fializar o empréstimo
                                double valorMulta = emprestimoFinalizado.calcularMulta(dataDevolucao);
                                System.out.println("O valor da multa é: " + valorMulta);
                                System.out.println("Deseja confirmar o pagamento da multa e a devolução do livro? (s/n)");
                                String confirmacao = leitor.nextLine().toLowerCase();
                                if (confirmacao.equalsIgnoreCase("s")) {
                                    Livros livroDevolvido = emprestimoFinalizado.getLivro();
                                    livroDevolvido.icrementarQuantidadeCopias();
                                    emprestimoFinalizado.finalizarEmprestimo();
                                    emprestimoList.remove(emprestimoFinalizado);
                                    System.out.println("Devolução registrada com sucesso.");
                                } else {
                                    System.out.println("Devolução cancelada");
                                }
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato de data inválido. Use o formato YYYY-MM-DD.");
                            }
                        }
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;
                case 5:
                    // Sair do programa
                    System.exit(0);
                    leitor.close();
                    break;
            }
            ConexaoBancoDeDados.closeConnection(connection);
        }
    }
}
