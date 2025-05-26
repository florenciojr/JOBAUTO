<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<%
   request.setCharacterEncoding("UTF-8");
   response.setCharacterEncoding("UTF-8");
%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista de Usuários</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .profile-img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 50%;
            border: 2px solid #dee2e6;
        }
        .action-btn {
            margin-right: 5px;
        }
        .card-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
        }
        .status-badge {
            font-size: 0.8rem;
            padding: 0.35em 0.65em;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="card shadow">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h2 class="mb-0"><i class="fas fa-users me-2"></i>Lista de Usuários</h2>
                <a href="${pageContext.request.contextPath}/usuario?action=novo" class="btn btn-primary">
                    <i class="fas fa-plus me-1"></i> Novo Usuário
                </a>
            </div>
            
            <div class="card-body">
                <c:if test="${not empty mensagemSucesso}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${mensagemSucesso}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty mensagemErro}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${mensagemErro}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Foto</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th>Tipo</th>
                                <th>Telefone</th>
                                <th>Status</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios}" var="usuario">
                                <tr>
                                    <td>${usuario.id}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty usuario.fotoPerfil}">
                                                <img src="${pageContext.request.contextPath}/${usuario.fotoPerfil}" 
                                                     alt="Foto de ${usuario.nome}" class="profile-img">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="profile-img bg-secondary text-white d-flex align-items-center justify-content-center">
                                                    <i class="fas fa-user"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${usuario.nome}</td>
                                    <td>${usuario.email}</td>
                                    <td>
                                        <span class="badge 
                                            ${usuario.tipo == 'ADMIN' ? 'bg-danger' : 
                                              usuario.tipo == 'EMPRESA' ? 'bg-info' : 'bg-primary'}">
                                            ${usuario.tipo}
                                        </span>
                                    </td>
                                    <td>${usuario.telefone}</td>
                                    <td>
                                        <span class="badge rounded-pill ${usuario.ativo ? 'bg-success' : 'bg-secondary'} status-badge">
                                            ${usuario.ativo ? 'Ativo' : 'Inativo'}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/usuario?action=editar&id=${usuario.id}" 
                                           class="btn btn-sm btn-outline-primary action-btn" title="Editar">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/usuario?action=excluir&id=${usuario.id}" 
                                           class="btn btn-sm btn-outline-danger action-btn" title="Excluir"
                                           onclick="return confirm('Tem certeza que deseja excluir este usuário?')">
                                            <i class="fas fa-trash-alt"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>