<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty perfil.usuarioId ? 'Novo' : 'Editar'} Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            background-color: #fff;
        }
        .form-header {
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        textarea {
            min-height: 120px;
        }
    </style>
</head>
<body>
    <div class="container py-5">
        <div class="form-container">
            <div class="form-header">
                <h2 class="mb-3">
                    ${empty perfil.usuarioId ? 'Criar Novo Perfil' : 'Editar Perfil'}
                </h2>
                
                <c:if test="${not empty erro}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        ${erro}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
            </div>
            
            <form action="perfil-candidato" method="post">
                <input type="hidden" name="action" value="${empty perfil.usuarioId ? 'salvar' : 'atualizar'}">
                
                <c:if test="${not empty perfil.usuarioId}">
                    <input type="hidden" name="usuarioId" value="${perfil.usuarioId}">
                </c:if>
                
             <div class="mb-3">
    <label for="usuarioId" class="form-label fw-bold">ID do Usuário</label>
    <c:choose>
        <c:when test="${empty perfil.usuarioId or perfil.usuarioId == 0}">
            <input type="number" class="form-control" id="usuarioId" name="usuarioId" required>
        </c:when>
        <c:otherwise>
            <input type="number" class="form-control" id="usuarioId" name="usuarioId" 
                   value="${perfil.usuarioId}" readonly>
        </c:otherwise>
    </c:choose>
</div>
                
                <div class="mb-3">
                    <label for="profissao" class="form-label">Profissão</label>
                    <input type="text" class="form-control" id="profissao" name="profissao" 
                           value="${perfil.profissao}" required>
                </div>
                
                <div class="mb-3">
                    <label for="resumo" class="form-label">Resumo Profissional</label>
                    <textarea class="form-control" id="resumo" name="resumo" required>${perfil.resumo}</textarea>
                </div>
                
                <div class="mb-3">
                    <label for="habilidades" class="form-label">Habilidades</label>
                    <textarea class="form-control" id="habilidades" name="habilidades">${perfil.habilidades}</textarea>
                    <div class="form-text">Separe as habilidades com vírgula</div>
                </div>
                
                <div class="mb-3">
                    <label for="experiencia" class="form-label">Experiência Profissional</label>
                    <textarea class="form-control" id="experiencia" name="experiencia">${perfil.experiencia}</textarea>
                </div>
                
                <div class="mb-3">
                    <label for="pretensaoSalarial" class="form-label">Pretensão Salarial</label>
                    <div class="input-group">
                        <span class="input-group-text">R$</span>
                        <input type="text" class="form-control" id="pretensaoSalarial" name="pretensaoSalarial" 
                               value="${perfil.pretensaoSalarial}" placeholder="Ex: 8.000,00">
                    </div>
                </div>
                
                <div class="d-flex justify-content-between mt-4">
                    <a href="perfil-candidato?action=listar" class="btn btn-outline-secondary">
                        Voltar
                    </a>
                    <button type="submit" class="btn btn-primary">
                        ${empty perfil.usuarioId ? 'Criar' : 'Atualizar'}
                    </button>
                </div>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>