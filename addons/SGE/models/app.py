from odoo import models, fields, api

class Project(models.Model):
    _name = 'sge.project'
    _description = 'Proyecto'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char(string='Nombre del Proyecto', tracking=True)
    state = fields.Selection([
        ('draft', 'Borrador'),
        ('in_progress', 'En Desarrollo'),
        ('developing', 'Desarrollando'),
        ('cancelled', 'Cancelada')
    ], string='Estado', default='draft', tracking=True)
    company_id = fields.Many2one('res.company', string='Empresa Cliente', tracking=True)
    communicator_id = fields.Many2one('hr.employee', string='Comunicador', tracking=True)
    has_ios_support = fields.Boolean(string='Soporte iOS', tracking=True)
    employees_ids = fields.Many2many('hr.employee', string='Empleados', tracking=True)
    total_hours = fields.Float(string='Total de Horas', compute='_compute_total_hours', store=True, tracking=True)

    @api.depends('employees_ids.hours_spent')
    def _compute_total_hours(self):
        for project in self:
            project.total_hours = sum(employee.hours_spent for employee in project.employees_ids)