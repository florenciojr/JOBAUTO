package util;

import java.io.*;
import java.nio.file.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;

public class FileUploadUtil {
    private static final String[] EXTENSOES_PERMITIDAS = {".jpg", ".jpeg", ".png", ".gif"};
    private static final long MAX_TAMANHO_ARQUIVO = 2 * 1024 * 1024; // 2MB

    /**
     * Processa o upload de um arquivo e retorna o caminho relativo
     * @param request HttpServletRequest
     * @param fieldName Nome do campo do formulário
     * @param uploadDir Diretório de upload relativo
     * @param userId ID do usuário (para nome do arquivo)
     * @param currentFilePath Caminho do arquivo atual (para remoção)
     * @return Caminho relativo do arquivo ou null se não houver upload
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws ServletException 
     */
    public String processarUpload(HttpServletRequest request, String fieldName, 
                                String uploadDir, int userId, String currentFilePath) 
            throws IOException, IllegalArgumentException, ServletException {
        
        Part filePart = request.getPart(fieldName);
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        // Validar tamanho do arquivo
        if (filePart.getSize() > MAX_TAMANHO_ARQUIVO) {
            throw new IllegalArgumentException("Tamanho do arquivo excede o limite de 2MB");
        }

        // Validar extensão do arquivo
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        
        if (!Arrays.asList(EXTENSOES_PERMITIDAS).contains(fileExtension)) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido. Use apenas JPG, JPEG, PNG ou GIF");
        }

        // Criar diretório de upload se não existir
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + uploadDir;
        File uploadDirFile = new File(uploadPath);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Gerar nome único para o arquivo
        String newFileName = "user_" + userId + "_" + System.currentTimeMillis() + fileExtension;
        String filePath = uploadPath + File.separator + newFileName;

        // Remover arquivo antigo se existir
        if (currentFilePath != null && !currentFilePath.isEmpty()) {
            removerArquivo(currentFilePath, request);
        }

        // Escrever o novo arquivo
        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        return uploadDir + "/" + newFileName;
    }

    /**
     * Remove um arquivo do sistema de arquivos
     * @param filePath Caminho relativo do arquivo
     * @param request HttpServletRequest para obter o caminho real
     */
    public void removerArquivo(String filePath, HttpServletRequest request) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        try {
            String fullPath = request.getServletContext().getRealPath("") + filePath;
            File file = new File(fullPath);
            if (file.exists()) {
                if (!file.delete()) {
                    System.err.println("Não foi possível deletar o arquivo: " + fullPath);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover arquivo: " + e.getMessage());
        }
    }

    /**
     * Remove um arquivo do sistema de arquivos (sobrecarga para uso em Servlets)
     * @param filePath Caminho relativo do arquivo
     * @param servletContext ServletContext para obter o caminho real
     */
    public void removerArquivo(String filePath, ServletContext servletContext) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        try {
            String fullPath = servletContext.getRealPath("") + filePath;
            File file = new File(fullPath);
            if (file.exists()) {
                if (!file.delete()) {
                    System.err.println("Não foi possível deletar o arquivo: " + fullPath);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover arquivo: " + e.getMessage());
        }
    }
}