package test;

import dao.Vagas;
import model.Vaga;
import java.util.List;

public class TesteVagasDAO{
    public static void main(String[] args) {
        Vagas vagasDao = new Vagas();

        // 1. Inserir uma nova vaga
        Vaga novaVaga = new Vaga();
        novaVaga.setEmpresaId(1);  // Use um id de empresa válido da sua base
        novaVaga.setTitulo("Analista de Sistemas");
        novaVaga.setDescricao("Descrição da vaga de Analista de Sistemas.");
        novaVaga.setRequisitos("Requisitos necessários...");
        novaVaga.setBeneficios("Vale transporte, VR");
        novaVaga.setSalario("1500.00");
        novaVaga.setTipoContrato("CLT");
        novaVaga.setModalidade("Presencial");
        novaVaga.setLocalizacao("Maputo");
        novaVaga.setDataPublicacao("2025-06-23 10:00:00");
        novaVaga.setDataEncerramento("2025-07-23 23:59:59");
        novaVaga.setStatus("ABERTA");
        novaVaga.setValorRecrutamento(500.0);

        boolean inseriu = vagasDao.inserir(novaVaga);
        System.out.println("Inserção da vaga: " + (inseriu ? "SUCESSO" : "FALHA"));
        System.out.println("ID gerado: " + novaVaga.getId());

        // 2. Listar todas as vagas (com nome da empresa)
        List<Vaga> todasVagas = vagasDao.listarTodas();
        System.out.println("\nLista de todas as vagas:");
        for (Vaga v : todasVagas) {
            System.out.println(v.getId() + ": " + v.getTitulo() + " | Empresa: " + v.getNomeEmpresa());
        }

        // 3. Buscar vaga por ID
        int idBusca = novaVaga.getId();
        Vaga vagaBuscada = vagasDao.buscarPorId(idBusca);
        if (vagaBuscada != null) {
            System.out.println("\nVaga buscada pelo ID " + idBusca + ": " + vagaBuscada.getTitulo());
        } else {
            System.out.println("\nVaga com ID " + idBusca + " não encontrada.");
        }

        // 4. Atualizar a vaga
        if (vagaBuscada != null) {
            vagaBuscada.setTitulo("Analista de Sistemas Sênior");
            boolean atualizou = vagasDao.atualizar(vagaBuscada);
            System.out.println("Atualização da vaga: " + (atualizou ? "SUCESSO" : "FALHA"));
        }

        // 5. Listar vagas ativas
        List<Vaga> vagasAtivas = vagasDao.buscarAtivas();
        System.out.println("\nVagas ativas:");
        for (Vaga v : vagasAtivas) {
            System.out.println(v.getId() + ": " + v.getTitulo() + " | Empresa: " + v.getNomeEmpresa());
        }

        // 6. Deletar a vaga criada (opcional)
        boolean deletou = vagasDao.deletar(novaVaga.getId());
        System.out.println("\nDeletar vaga ID " + novaVaga.getId() + ": " + (deletou ? "SUCESSO" : "FALHA"));
    }
}
