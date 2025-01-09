from odoo import models, fields, api
from datetime import date

class Project(models.Model):
    _name = 'sge.project'
    _description = 'Proyecto'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char(string='Nombre del Proyecto', tracking=True, size=50)
    state = fields.Selection([
        ('cancelled', 'Cancelada'),
        ('draft', 'Planificando'),
        ('in_progress', 'En Desarrollo'),
        ('review', 'Revisando'),
        ('done', 'Terminada'),
    ], string='Estado', default='draft', tracking=True)
    company_id = fields.Many2one('res.company', string='Empresa Cliente', tracking=True)
    communicator_id = fields.Many2one('hr.employee', string='Comunicador', tracking=True)
    has_ios_support = fields.Boolean(string='Soporte iOS', tracking=True)
    project_hours_ids = fields.One2many('sge.project.hours', 'project_id', string='Horas Trabajadas')
    total_hours = fields.Float(string='Total de Horas', compute='_compute_total_hours', store=True, tracking=True)
    start_date = fields.Date(string='Fecha de Inicio', default=fields.Date.context_today, readonly=True, tracking=True)
    end_date = fields.Date(string='Fecha de Fin', readonly=True, tracking=True)
    description = fields.Text(string='Descripción', tracking=True, size=50)

    @api.depends('project_hours_ids.hours')
    def _compute_total_hours(self):
        for project in self:
            project.total_hours = sum(hours.hours for hours in project.project_hours_ids)

    @api.model
    def create(self, vals):
        if 'start_date' not in vals:
            vals['start_date'] = fields.Date.context_today(self)
        return super(Project, self).create(vals)

    def action_set_cancelled(self):
        if self.state not in ['cancelled', 'done']:
            self.state = 'cancelled'
            self.end_date = date.today()

    def action_set_draft(self):
        if self.state not in ['cancelled', 'done']:
            self.state = 'draft'

    def action_set_in_progress(self):
        if self.state not in ['cancelled', 'done']:
            self.state = 'in_progress'

    def action_set_review(self):
        if self.state not in ['cancelled', 'done']:
            self.state = 'review'

    def action_set_done(self):
        if self.state not in ['cancelled', 'done']:
            self.state = 'done'
            self.end_date = date.today()