<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Candidaturas da Vaga ${vagaId}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        .status-badge { padding: 0.35em 0.65em; font-weight: 600; }
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
            <h2>Candidaturas da Vaga ${vagaId}</h2>
            <a href="candidaturas" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Voltar
            </a>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
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
                                    <td>${candidatura.candidatoId}</td>
                                    <td>${candidatura.dataCandidatura}</td>
                                    <td>${candidatura.tipo}</td>
                                    <td>
                                        <form action="candidaturas" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="updateStatus">
                                            <input type="hidden" name="id" value="${candidatura.id}">
                                            <select name="status" class="form-select form-select-sm" 
                                                    onchange="this.form.submit()">
                                                <option value="PENDENTE" ${candidatura.status == 'PENDENTE' ? 'selected' : ''}>Pendente</option>
                                                <option value="VISUALIZADA" ${candidatura.status == 'VISUALIZADA' ? 'selected' : ''}>Visualizada</option>
                                                <option value="ENTREVISTA" ${candidatura.status == 'ENTREVISTA' ? 'selected' : ''}>Entrevista</option>
                                                <option value="CONTRATADO" ${candidatura.status == 'CONTRATADO' ? 'selected' : ''}>Contratado</option>
                                                <option value="REJEITADA" ${candidatura.status == 'REJEITADA' ? 'selected' : ''}>Rejeitada</option>
                                            </select>
                                        </form>
                                    </td>
                                    <td>
                                        <a href="candidaturas?action=edit&id=${candidatura.id}" 
                                           class="btn btn-sm btn-outline-primary" title="Editar">
                                            <i class="bi bi-pencil"></i>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
