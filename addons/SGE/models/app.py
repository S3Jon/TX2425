from odoo import models, fields, api
from odoo.exceptions import UserError

class App(models.Model):
    _name = 'sge.app'
    _description = 'Aplicación Desarrollada'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char('Nombre de la App', required=True, tracking=True)
    client_id = fields.Many2one('res.partner', string='Cliente', required=True, tracking=True)
    coordinator_id = fields.Many2one('res.users', string='Comunicador', required=True, tracking=True)
    is_ios_compatible = fields.Boolean('Compatible con iOS', default=False, tracking=True)
    employee_hours_ids = fields.One2many('sge.app.employee.hours', 'app_id', string='Horas por Empleado')
    total_hours = fields.Float('Total Horas', compute='_compute_total_hours', store=True)

    state = fields.Selection([
        ('draft', 'Borrador'),
        ('in_progress', 'En Desarrollo'),
        ('completed', 'Completada'),
        ('cancelled', 'Cancelada')
    ], string='Estado', default='draft', tracking=True)

    # Campo para seleccionar el estado en el formulario
    selected_state = fields.Selection([
        ('draft', 'Borrador'),
        ('in_progress', 'En Desarrollo'),
        ('completed', 'Completada'),
        ('cancelled', 'Cancelada')
    ], string='Seleccionar Estado', required=True, default='draft')

    @api.depends('employee_hours_ids.hours')
    def _compute_total_hours(self):
        for app in self:
            app.total_hours = sum(hour.hours for hour in app.employee_hours_ids)

    def action_update_state(self):
        # Verificamos si el campo selected_state tiene valor
        for record in self:
            if record.selected_state:
                record.state = record.selected_state
            else:
                raise UserError("Por favor seleccione un estado para actualizar.")
        
        # También se podría enviar una notificación de que el estado ha cambiado
        # record.message_post(body="El estado de la aplicación ha sido actualizado a: {}".format(record.state))

class AppEmployeeHours(models.Model):
    _name = 'sge.app.employee.hours'
    _description = 'Horas trabajadas por empleado en app'

    app_id = fields.Many2one('sge.app', string='Aplicación', required=True)
    employee_id = fields.Many2one('res.users', string='Empleado', required=True)
    hours = fields.Float('Horas Trabajadas', required=True)
