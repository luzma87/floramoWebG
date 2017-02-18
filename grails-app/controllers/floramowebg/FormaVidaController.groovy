package floramowebg

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FormaVidaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FormaVida.list(params), model:[formaVidaCount: FormaVida.count()]
    }

    def show(FormaVida formaVida) {
        respond formaVida
    }

    def create() {
        respond new FormaVida(params)
    }

    @Transactional
    def save(FormaVida formaVida) {
        if (formaVida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (formaVida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaVida.errors, view:'create'
            return
        }

        formaVida.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'formaVida.label', default: 'FormaVida'), formaVida.id])
                flash.color = 'green lighten-2'
                redirect formaVida
            }
            '*' { respond formaVida, [status: CREATED] }
        }
    }

    def edit(FormaVida formaVida) {
        respond formaVida
    }

    @Transactional
    def update(FormaVida formaVida) {
        if (formaVida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (formaVida.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaVida.errors, view:'edit'
            return
        }

        formaVida.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'formaVida.label', default: 'FormaVida'), formaVida.id])
                flash.color = 'green lighten-2'
                redirect formaVida
            }
            '*'{ respond formaVida, [status: OK] }
        }
    }

    @Transactional
    def delete(FormaVida formaVida) {

        if (formaVida == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        formaVida.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'formaVida.label', default: 'FormaVida'), formaVida.id])
                flash.color = 'green lighten-2'
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'formaVida.label', default: 'FormaVida'), params.id])
                flash.color = 'orange lighten-2'
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
