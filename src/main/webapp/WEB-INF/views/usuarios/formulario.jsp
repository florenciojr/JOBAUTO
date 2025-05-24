<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${usuario.id == 0 ? 'Novo Usuário' : 'Editar Usuário'}</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .profile-img-container {
            width: 150px;
            height: 150px;
            margin: 0 auto 20px;
            position: relative;
        }
        .profile-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 50%;
            border: 3px solid #dee2e6;
        }
        .profile-img-placeholder {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 3px dashed #dee2e6;
        }
        .profile-img-placeholder i {
            font-size: 3rem;
            color: #6c757d;
        }
        .form-section {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            padding: 30px;
        }
        .form-title {
            color: #495057;
            border-bottom: 2px solid #dee2e6;
            padding-bottom: 10px;
            margin-bottom: 25px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="form-section">
                    <h2 class="form-title">
                        <i class="fas ${usuario.id == 0 ? 'fa-user-plus' : 'fa-user-edit'} me-2"></i>
                        ${usuario.id == 0 ? 'Cadastrar Novo Usuário' : 'Editar Usuário'}
                    </h2>
                    
                    <c:if test="${not empty mensagemSucesso}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${mensagemSucesso}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty erro}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${erro}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/usuario" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="${usuario.id == 0 ? 'cadastrar' : 'atualizar'}">
                        <input type="hidden" name="id" value="${usuario.id}">
                        
                        <!-- Seção de Foto de Perfil -->
                        <div class="text-center mb-4">
                            <div class="profile-img-container">
                                <c:choose>
                                    <c:when test="${not empty usuario.fotoPerfil}">
                                        <img src="${pageContext.request.contextPath}/${usuario.fotoPerfil}" 
                                             alt="Foto atual" class="profile-img" id="profileImage">
                                        <input type="hidden" name="fotoAtual" value="${usuario.fotoPerfil}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="profile-img-placeholder" id="profilePlaceholder">
                                            <i class="fas fa-user"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="mb-3">
                                <label for="fotoPerfil" class="btn btn-outline-primary">
                                    <i class="fas fa-camera me-2"></i>Alterar Foto
                                </label>
                                <input type="file" id="fotoPerfil" name="fotoPerfil" accept="image/*" 
                                       class="d-none" onchange="previewImage(this)">
                                <div class="form-text">Formatos: JPG, PNG ou GIF (Máx. 2MB)</div>
                            </div>
                        </div>
                        
                        <!-- Dados Básicos -->
                        <div class="row g-3 mb-4">
                            <div class="col-md-6">
                                <label for="nome" class="form-label">Nome Completo</label>
                                <input type="text" class="form-control" id="nome" name="nome" 
                                       value="${usuario.nome}" required>
                            </div>
                            
                            <div class="col-md-6">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${usuario.email}" ${usuario.id != 0 ? 'readonly' : ''} required>
                            </div>
                            
                            <div class="col-md-6">
                                <label for="senha" class="form-label">Senha</label>
                                <input type="password" class="form-control" id="senha" name="senha" 
                                       ${usuario.id == 0 ? 'required' : ''}>
                                <c:if test="${usuario.id != 0}">
                                    <div class="form-text">Deixe em branco para manter a senha atual</div>
                                </c:if>
                            </div>
                            
                            <div class="col-md-6">
                                <label for="telefone" class="form-label">Telefone</label>
                                <input type="text" class="form-control" id="telefone" name="telefone" 
                                       value="${usuario.telefone}">
                            </div>
                        </div>
                        
                        <!-- Tipo e Status -->
                        <div class="row g-3 mb-4">
                            <div class="col-md-6">
                                <label for="tipo" class="form-label">Tipo de Usuário</label>
                                <select class="form-select" id="tipo" name="tipo" required>
                                    <option value="CANDIDATO" ${usuario.tipo == 'CANDIDATO' ? 'selected' : ''}>Candidato</option>
                                    <option value="EMPRESA" ${usuario.tipo == 'EMPRESA' ? 'selected' : ''}>Empresa</option>
                                    <option value="ADMIN" ${usuario.tipo == 'ADMIN' ? 'selected' : ''}>Administrador</option>
                                </select>
                            </div>
                            
                            <c:if test="${usuario.id != 0}">
                                <div class="col-md-6">
                                    <label class="form-label">Status</label>
                                    <div class="form-check form-switch">
                                        <input class="form-check-input" type="checkbox" id="ativo" 
                                               name="ativo" ${usuario.ativo ? 'checked' : ''} value="true">
                                        <label class="form-check-label" for="ativo">
                                            ${usuario.ativo ? 'Ativo' : 'Inativo'}
                                        </label>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        
                        <!-- Botões de Ação -->
                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/usuario?action=listar" 
                               class="btn btn-outline-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Voltar
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas ${usuario.id == 0 ? 'fa-save' : 'fa-sync-alt'} me-2"></i>
                                ${usuario.id == 0 ? 'Cadastrar' : 'Atualizar'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // Preview da imagem selecionada
        function previewImage(input) {
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                const maxSize = 2 * 1024 * 1024; // 2MB
                
                if (input.files[0].size > maxSize) {
                    alert('A imagem deve ter no máximo 2MB!');
                    input.value = '';
                    return;
                }
                
                reader.onload = function(e) {
                    const placeholder = document.getElementById('profilePlaceholder');
                    const existingImg = document.getElementById('profileImage');
                    
                    if (existingImg) {
                        existingImg.src = e.target.result;
                    } else if (placeholder) {
                        placeholder.innerHTML = `<img src="${e.target.result}" alt="Preview" class="profile-img" id="profileImage">`;
                    }
                }
                
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>
</body>
</html>