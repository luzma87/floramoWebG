package floramowebg

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class GeneroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Genero.list(params), model:[generoCount: Genero.count()]
    }

    def show(Genero genero) {
        respond genero
    }

    def create() {
        respond new Genero(params)
    }

    @Transactional
    def save(Genero genero) {
        if (genero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (genero.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond genero.errors, view:'create'
            return
        }

        genero.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'genero.label', default: 'Genero'), genero.id])
                flash.color = 'green lighten-2'
                redirect genero
            }
            '*' { respond genero, [status: CREATED] }
        }
    }

    def edit(Genero genero) {
        respond genero
    }

    @Transactional
    def update(Genero genero) {
        if (genero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (genero.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond genero.errors, view:'edit'
            return
        }

        genero.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'genero.label', default: 'Genero'), genero.id])
                flash.color = 'green lighten-2'
                redirect genero
            }
            '*'{ respond genero, [status: OK] }
        }
    }

    @Transactional
    def delete(Genero genero) {

        if (genero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        genero.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'genero.label', default: 'Genero'), genero.id])
                flash.color = 'green lighten-2'
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'genero.label', default: 'Genero'), params.id])
                flash.color = 'orange lighten-2'
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
