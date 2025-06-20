<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Minhas Candidaturas</title>
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
            <h2>Minhas Candidaturas</h2>
            <a href="vagas" class="btn btn-outline-primary">
                <i class="bi bi-briefcase"></i> Ver Vagas
            </a>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="row">
            <c:forEach items="${candidaturas}" var="candidatura">
                <div class="col-md-6 mb-4">
                    <div class="card h-100 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex justify-content-between">
                                <h5 class="card-title">Vaga #${candidatura.vagaId}</h5>
                                <span class="status-badge status-${candidatura.status}">
                                    ${candidatura.status}
                                </span>
                            </div>
                            <p class="card-text">
                                <small class="text-muted">
                                    <i class="bi bi-calendar"></i> ${candidatura.dataCandidatura}
                                </small>
                            </p>
                            <p class="card-text">
                                <strong>Tipo:</strong> ${candidatura.tipo}
                            </p>
                        </div>
                        <div class="card-footer bg-transparent">
                            <div class="d-flex justify-content-between">
                                <a href="vagas?action=view&id=${candidatura.vagaId}" 
                                   class="btn btn-sm btn-outline-primary">
                                    <i class="bi bi-eye"></i> Ver Vaga
                                </a>
                                <form action="candidaturas" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${candidatura.id}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger" 
                                            onclick="return confirm('Cancelar esta candidatura?')">
                                        <i class="bi bi-x-circle"></i> Cancelar
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
