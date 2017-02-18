<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="luz" />
        <g:set var="entityName" value="${message(code: 'lugarEspecie.label', default: 'LugarEspecie')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:link class="create waves-effect waves-light btn deep-purple lighten-2" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        <g:link class="list waves-effect waves-light btn deep-purple lighten-2" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>

        <div id="show-lugarEspecie" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="card-panel ${flash.color}" role="status">${flash.message}</div>
            </g:if>
            <f:display bean="lugarEspecie" />
            <g:form resource="${this.lugarEspecie}" method="DELETE">
                <g:link class="edit waves-effect waves-light btn blue lighten-1" action="edit" resource="${this.lugarEspecie}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                <input class="delete waves-effect waves-light btn red lighten-1" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            </g:form>
        </div>
    </body>
</html>
