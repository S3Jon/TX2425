from odoo import models, fields, api

from odoo.exceptions import UserError

class App(models.Model):
    _name = 'sge.app'
    _description = 'Aplicación Desarrollada'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char('Nombre de la App', required=True, tracking=True)
    client_id = fields.Many2one('res.partner', string='Cliente', required=True, tracking=True)
    coordinator_id = fields.Many2one('res.users', string='Coordinador', required=True, tracking=True)
    is_ios_compatible = fields.Boolean('Compatible con iOS', default=False, tracking=True)
    employee_hours_ids = fields.One2many('sge.app.employee.hours', 'app_id', string='Horas por Empleado')
    total_hours = fields.Float('Total Horas', compute='_compute_total_hours', store=True)

    state = fields.Selection([
        ('draft', 'Borrador'),
        ('in_progress', 'En Desarrollo'),
        ('completed', 'Completada'),
        ('cancelled', 'Cancelada')
    ], string='Estado', default='draft', tracking=True)

    @api.depends('employee_hours_ids.hours')
    def _compute_total_hours(self):
        for app in self:
            app.total_hours = sum(hour.hours for hour in app.employee_hours_ids)

    def action_set_in_progress(self):
        for app in self:
            if app.state != 'draft':
                raise UserError('Solo puedes iniciar proyectos que estén en estado "Borrador".')
            app.state = 'in_progress'

    def action_set_completed(self):
        for app in self:
            if app.state != 'in_progress':
                raise UserError('Solo puedes completar proyectos que estén "En Desarrollo".')
            app.state = 'completed'

    def action_set_cancelled(self):
        for app in self:
            if app.state not in ['draft', 'in_progress']:
                raise UserError('Solo puedes cancelar proyectos que estén "Borrador" o "En Desarrollo".')
            app.state = 'cancelled'

    def action_reset_to_draft(self):
        for app in self:
            if app.state == 'draft':
                raise UserError('El proyecto ya está en estado "Borrador".')
            app.state = 'draft'

class AppEmployeeHours(models.Model):
    _name = 'sge.app.employee.hours'
    _description = 'Horas trabajadas por empleado en app'

    app_id = fields.Many2one('sge.app', string='Aplicación', required=True)
    employee_id = fields.Many2one('res.users', string='Empleado', required=True)
    hours = fields.Float('Horas Trabajadas', required=True)
