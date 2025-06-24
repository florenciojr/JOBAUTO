<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Vagas - JobConnect</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        :root {
            --primary: #4361ee;
            --secondary: #3f37c9;
            --accent: #4895ef;
            --success: #4cc9f0;
            --light: #f8f9fa;
            --dark: #212529;
            --text: #2b2d42;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fc;
        }
        
        .card {
            border: none;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        
        .card-header {
            background-color: var(--primary);
            color: white;
            border-radius: 10px 10px 0 0 !important;
        }
        
        .table th {
            background-color: rgba(67, 97, 238, 0.05);
            color: var(--primary);
            text-transform: uppercase;
            font-size: 0.75rem;
            letter-spacing: 0.5px;
        }
        
        .badge {
            padding: 0.5em 0.75em;
            border-radius: 0.5rem;
            font-weight: 500;
        }
        
        .action-btn {
            width: 36px;
            height: 36px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 50% !important;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <div class="card shadow-sm">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">
                    <c:choose>
                        <c:when test="${isEmpresaView}"><i class="bi bi-building me-2"></i>Minhas Vagas</c:when>
                        <c:when test="${isAtivasView}"><i class="bi bi-search me-2"></i>Vagas Disponíveis</c:when>
                        <c:otherwise><i class="bi bi-list-check me-2"></i>Todas as Vagas</c:otherwise>
                    </c:choose>
                </h5>
                <c:if test="${usuarioLogado.tipo == 'EMPRESA' && isEmpresaView}">
                    <a href="<c:url value='/vagas?action=new'/>" class="btn btn-light btn-sm">
                        <i class="bi bi-plus-lg me-1"></i> Nova Vaga
                    </a>
                </c:if>
            </div>
            
            <div class="card-body">
                <c:if test="${not empty mensagemSucesso}">
                    <div class="alert alert-success alert-dismissible fade show">
                        <i class="bi bi-check-circle-fill me-2"></i> ${mensagemSucesso}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <c:if test="${not empty mensagemErro}">
                    <div class="alert alert-danger alert-dismissible fade show">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${mensagemErro}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>
                
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>Título</th>
                                <th>Empresa</th>
                                <th>Localização</th>
                                <th>Tipo</th>
                                <th>Status</th>
                                <th class="text-end">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${vagas}" var="vaga">
                                <tr>
                                    <td>${vaga.titulo}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty vaga.nomeEmpresa}">
                                                ${vaga.nomeEmpresa}
                                            </c:when>
                                            <c:otherwise>
                                                Empresa ID: ${vaga.empresaId}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${vaga.localizacao}</td>
                                    <td>${vaga.tipoContrato}</td>
                                    <td>
                                        <span class="badge ${vaga.status == 'ABERTA' ? 'bg-success' : 'bg-secondary'}">
                                            ${vaga.status}
                                        </span>
                                    </td>
                                    <td>
                                        <div class="d-flex justify-content-end gap-2">
                                            <a href="<c:url value='/vagas?action=view&id=${vaga.id}'/>" 
                                               class="btn btn-sm btn-outline-primary action-btn">
                                                <i class="bi bi-eye"></i>
                                            </a>
                                            <c:if test="${usuarioLogado.tipo == 'EMPRESA' && (isEmpresaView || vaga.empresaId == usuarioLogado.id)}">
                                                <a href="<c:url value='/vagas?action=edit&id=${vaga.id}'/>" 
                                                   class="btn btn-sm btn-outline-secondary action-btn">
                                                    <i class="bi bi-pencil"></i>
                                                </a>
                                                <a href="<c:url value='/vagas?action=delete&id=${vaga.id}'/>"
                                                   class="btn btn-sm btn-outline-danger action-btn"
                                                   onclick="return confirm('Tem certeza que deseja excluir esta vaga?')">
                                                    <i class="bi bi-trash"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${usuarioLogado.tipo == 'CANDIDATO' && isAtivasView}">
                                                <a href="<c:url value='/candidaturas?action=new&vagaId=${vaga.id}'/>" 
                                                   class="btn btn-sm btn-success">
                                                    Candidatar-se
                                                </a>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
