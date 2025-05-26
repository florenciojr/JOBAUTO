<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .card-details {
            max-width: 800px;
            margin: 0 auto;
        }
        .detail-section {
            margin-bottom: 20px;
        }
        .detail-section h5 {
            border-bottom: 1px solid #eee;
            padding-bottom: 5px;
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="card card-details">
            <div class="card-header bg-primary text-white">
                <h2 class="mb-0">Detalhes do Perfil</h2>
            </div>
            
            <div class="card-body">
                <c:if test="${not empty mensagemSucesso}">
                    <div class="alert alert-success">${mensagemSucesso}</div>
                </c:if>
                
                <div class="detail-section">
                    <h5>Informações Básicas</h5>
                    <p><strong>ID do Usuário:</strong> ${perfil.usuarioId}</p>
                    <p><strong>Profissão:</strong> ${perfil.profissao}</p>
                </div>
                
                <div class="detail-section">
                    <h5>Resumo Profissional</h5>
                    <p class="card-text">${perfil.resumo}</p>
                </div>
                
                <div class="detail-section">
                    <h5>Habilidades</h5>
                    <p class="card-text">${perfil.habilidades}</p>
                </div>
                
                <div class="detail-section">
                    <h5>Experiência Profissional</h5>
                    <p class="card-text">${perfil.experiencia}</p>
                </div>
                
                <div class="detail-section">
                    <h5>Pretensão Salarial</h5>
                    <p class="card-text">${perfil.pretensaoSalarial}</p>
                </div>
                
                <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                    <a href="perfil-candidato?action=listar" class="btn btn-secondary me-md-2">Voltar</a>
                    <a href="perfil-candidato?action=editar&usuarioId=${perfil.usuarioId}" 
                       class="btn btn-primary">Editar</a>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>