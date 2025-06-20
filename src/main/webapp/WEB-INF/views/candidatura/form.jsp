<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${candidatura.id == null ? 'Nova' : 'Editar'} Candidatura</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-sm">
                    <div class="card-header">
                        <h4 class="mb-0">${candidatura.id == null ? 'Nova' : 'Editar'} Candidatura</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">${errorMessage}</div>
                        </c:if>

                        <form action="candidaturas" method="post">
                            <input type="hidden" name="action" value="${candidatura.id == null ? 'insert' : 'update'}">
                            <c:if test="${candidatura.id != null}">
                                <input type="hidden" name="id" value="${candidatura.id}">
                            </c:if>

                            <div class="mb-3">
                                <label for="vagaId" class="form-label">ID da Vaga</label>
                                <input type="number" class="form-control" id="vagaId" name="vagaId" 
                                       value="${candidatura.vagaId}" required>
                            </div>

                            <div class="mb-3">
                                <label for="candidatoId" class="form-label">ID do Candidato</label>
                                <input type="number" class="form-control" id="candidatoId" name="candidatoId" 
                                       value="${candidatura.candidatoId}" required>
                            </div>

                            <div class="mb-3">
                                <label for="tipo" class="form-label">Tipo</label>
                                <select class="form-select" id="tipo" name="tipo" required>
                                    <option value="">Selecione...</option>
                                    <option value="AUTOMATICA" ${candidatura.tipo == 'AUTOMATICA' ? 'selected' : ''}>Automática</option>
                                    <option value="MANUAL" ${candidatura.tipo == 'MANUAL' ? 'selected' : ''}>Manual</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="status" class="form-label">Status</label>
                                <select class="form-select" id="status" name="status" required>
                                    <option value="PENDENTE" ${candidatura.status == 'PENDENTE' ? 'selected' : ''}>Pendente</option>
                                    <option value="VISUALIZADA" ${candidatura.status == 'VISUALIZADA' ? 'selected' : ''}>Visualizada</option>
                                    <option value="ENTREVISTA" ${candidatura.status == 'ENTREVISTA' ? 'selected' : ''}>Entrevista</option>
                                    <option value="CONTRATADO" ${candidatura.status == 'CONTRATADO' ? 'selected' : ''}>Contratado</option>
                                    <option value="REJEITADA" ${candidatura.status == 'REJEITADA' ? 'selected' : ''}>Rejeitada</option>
                                </select>
                            </div>

                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="candidaturas" class="btn btn-secondary me-md-2">Cancelar</a>
                                <button type="submit" class="btn btn-primary">
                                    ${candidatura.id == null ? 'Cadastrar' : 'Atualizar'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
