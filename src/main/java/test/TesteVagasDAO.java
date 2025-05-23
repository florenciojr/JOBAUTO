package test;

import model.Vaga;
import dao.Vagas;
import java.util.List;

public class TesteVagasDAO {
    public static void main(String[] args) {
        Vagas vagas = new Vagas();
        
        // Teste de inserção
        Vaga vaga1 = new Vaga();
        vaga1.setEmpresaId(1);
        vaga1.setTitulo("Desenvolvedor Java Pleno");
        vaga1.setDescricao("Vaga para desenvolvedor Java com experiência em Spring Boot");
        vaga1.setRequisitos("Java, Spring Boot, SQL, Git");
        vaga1.setBeneficios("VT, VR, Plano de Saúde");
        vaga1.setSalario("R$ 8.000,00");
        vaga1.setTipoContrato("CLT");
        vaga1.setModalidade("Híbrido");
        vaga1.setLocalizacao("São Paulo/SP");
        vaga1.setDataPublicacao("2023-10-01 00:00:00");
        vaga1.setStatus("ABERTA");
        vaga1.setValorRecrutamento(1500.00);
        
        boolean inserida = vagas.inserir(vaga1);
        System.out.println("Vaga inserida: " + inserida);
        
        if (inserida) {
            System.out.println("ID gerado: " + vaga1.getId());
            
            // Teste de busca por ID
            Vaga vagaBuscada = vagas.buscarPorId(vaga1.getId());
            if (vagaBuscada != null) {
                System.out.println("\nVaga encontrada:");
                System.out.println("Título: " + vagaBuscada.getTitulo());
                System.out.println("Status: " + vagaBuscada.getStatus());
                
                // Teste de atualização
                vagaBuscada.setStatus("PAUSADA");
                boolean atualizada = vagas.atualizar(vagaBuscada);
                System.out.println("\nVaga atualizada: " + atualizada);
                
                // Teste de listagem
                System.out.println("\nTodas as vagas:");
                List<Vaga> todasVagas = vagas.listarTodas();
                for (Vaga v : todasVagas) {
                    System.out.println(v.getId() + " - " + v.getTitulo() + " (" + v.getStatus() + ")");
                }
                
                // Teste de busca por título
                System.out.println("\nBusca por 'Java':");
                List<Vaga> vagasJava = vagas.buscarPorTitulo("Java");
                for (Vaga v : vagasJava) {
                    System.out.println(v.getTitulo() + " - " + v.getModalidade());
                }
                
             
            }
        }
    }
}