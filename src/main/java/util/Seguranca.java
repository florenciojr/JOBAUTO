package util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguranca {
    
    public static String criptografar(String senha) {
        try {
            // Cria instância do algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Aplica o hash na senha
            byte[] hash = md.digest(senha.getBytes());
            
            // Converte bytes para hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // Caso o algoritmo não exista (improvável para SHA-256)
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
}