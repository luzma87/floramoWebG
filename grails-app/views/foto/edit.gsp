<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="luz" />
        <g:set var="entityName" value="${message(code: 'foto.label', default: 'Foto')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:link class="create waves-effect waves-light btn deep-purple lighten-2" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        <g:link class="list waves-effect waves-light btn deep-purple lighten-2" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>

        <div id="edit-foto" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="card-panel ${flash.color}" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.foto}">
            <div class="card-panel red lighten-1" role="alert">
                <ul class="errors" role="alert">
                    <g:eachError bean="${this.foto}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </div>
            </g:hasErrors>
            <g:form resource="${this.foto}" method="PUT">
                <g:hiddenField name="version" value="${this.foto?.version}" />
                <f:all bean="foto"/>
                <input class="save waves-effect waves-light btn green lighten-1" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </g:form>
        </div>
    </body>
</html>
