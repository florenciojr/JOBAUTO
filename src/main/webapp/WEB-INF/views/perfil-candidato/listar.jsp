<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Perfis</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .table-container {
            margin-top: 20px;
        }
        .action-buttons .btn {
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="row mt-4">
            <div class="col">
                <h2>Perfis de Candidatos</h2>
            </div>
            <div class="col text-end">
                <a href="perfil-candidato?action=novo" class="btn btn-success">Novo Perfil</a>
            </div>
        </div>

        <c:if test="${not empty mensagemSucesso}">
            <div class="alert alert-success mt-3">${mensagemSucesso}</div>
        </c:if>
        
        <c:if test="${not empty mensagemErro}">
            <div class="alert alert-danger mt-3">${mensagemErro}</div>
        </c:if>

        <div class="card mt-3">
            <div class="card-header">
                <form action="perfil-candidato" method="get" class="row g-3">
                    <input type="hidden" name="action" value="buscar">
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="profissao" 
                               placeholder="Buscar por profissão" value="${termoBusca}">
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary">Buscar</button>
                        <a href="perfil-candidato?action=listar" class="btn btn-outline-secondary">Limpar</a>
                    </div>
                </form>
            </div>
            
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>ID Usuário</th>
                                <th>Profissão</th>
                                <th>Resumo</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty perfis}">
                                    <tr>
                                        <td colspan="4" class="text-center">Nenhum perfil encontrado</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${perfis}" var="perfil">
                                        <tr>
                                            <td>${perfil.usuarioId}</td>
                                            <td>${perfil.profissao}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(perfil.resumo) > 50}">
                                                        ${fn:substring(perfil.resumo, 0, 50)}...
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${perfil.resumo}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="action-buttons">
                                                <a href="perfil-candidato?action=detalhes&usuarioId=${perfil.usuarioId}" 
                                                   class="btn btn-sm btn-info">Detalhes</a>
                                                <a href="perfil-candidato?action=editar&usuarioId=${perfil.usuarioId}" 
                                                   class="btn btn-sm btn-primary">Editar</a>
                                                <a href="perfil-candidato?action=excluir&usuarioId=${perfil.usuarioId}" 
                                                   class="btn btn-sm btn-danger"
                                                   onclick="return confirm('Tem certeza que deseja excluir este perfil?')">Excluir</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>