<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidaturas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .status-badge {
            padding: 0.35em 0.65em;
            font-weight: 600;
        }
        .status-PENDENTE { background-color: #6c757d; }
        .status-VISUALIZADA { background-color: #0dcaf0; color: #000; }
        .status-ENTREVISTA { background-color: #0d6efd; }
        .status-CONTRATADO { background-color: #198754; }
        .status-REJEITADA { background-color: #dc3545; }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Candidaturas</h2>
            <a href="candidaturas?action=new" class="btn btn-primary">
                <i class="bi bi-plus-circle"></i> Nova Candidatura
            </a>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="card shadow-sm">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Vaga ID</th>
                                <th>Candidato ID</th>
                                <th>Data</th>
                                <th>Tipo</th>
                                <th>Status</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${candidaturas}" var="candidatura">
                                <tr>
                                    <td>${candidatura.id}</td>
                                    <td>${candidatura.vagaId}</td>
                                    <td>${candidatura.candidatoId}</td>
                                    <td>${candidatura.dataCandidatura}</td>
                                    <td>${candidatura.tipo}</td>
                                    <td>
                                        <span class="status-badge status-${candidatura.status}">
                                            ${candidatura.status}
                                        </span>
                                    </td>
                                    <td>
                                        <div class="d-flex gap-2">
                                            <a href="candidaturas?action=edit&id=${candidatura.id}" 
                                               class="btn btn-sm btn-outline-primary" title="Editar">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <form action="candidaturas" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${candidatura.id}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger" 
                                                        title="Excluir" onclick="return confirm('Tem certeza?')">
                                                    <i class="bi bi-trash"></i>
                                                </button>
                                            </form>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
