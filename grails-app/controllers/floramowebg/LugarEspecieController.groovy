package floramowebg

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LugarEspecieController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LugarEspecie.list(params), model:[lugarEspecieCount: LugarEspecie.count()]
    }

    def show(LugarEspecie lugarEspecie) {
        respond lugarEspecie
    }

    def create() {
        respond new LugarEspecie(params)
    }

    @Transactional
    def save(LugarEspecie lugarEspecie) {
        if (lugarEspecie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lugarEspecie.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lugarEspecie.errors, view:'create'
            return
        }

        lugarEspecie.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lugarEspecie.label', default: 'LugarEspecie'), lugarEspecie.id])
                flash.color = 'green lighten-2'
                redirect lugarEspecie
            }
            '*' { respond lugarEspecie, [status: CREATED] }
        }
    }

    def edit(LugarEspecie lugarEspecie) {
        respond lugarEspecie
    }

    @Transactional
    def update(LugarEspecie lugarEspecie) {
        if (lugarEspecie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (lugarEspecie.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond lugarEspecie.errors, view:'edit'
            return
        }

        lugarEspecie.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lugarEspecie.label', default: 'LugarEspecie'), lugarEspecie.id])
                flash.color = 'green lighten-2'
                redirect lugarEspecie
            }
            '*'{ respond lugarEspecie, [status: OK] }
        }
    }

    @Transactional
    def delete(LugarEspecie lugarEspecie) {

        if (lugarEspecie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        lugarEspecie.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lugarEspecie.label', default: 'LugarEspecie'), lugarEspecie.id])
                flash.color = 'green lighten-2'
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lugarEspecie.label', default: 'LugarEspecie'), params.id])
                flash.color = 'orange lighten-2'
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
