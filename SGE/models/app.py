from odoo import models, fields, api
from datetime import date
import logging

_logger = logging.getLogger(__name__)

class Project(models.Model):
    _name = 'sge.project'
    _description = 'Proyecto'
    _inherit = ['mail.thread', 'mail.activity.mixin']

    name = fields.Char(string='Nombre del Proyecto', tracking=True, size=50, required=True)
    state = fields.Selection([
        ('cancelled', 'Cancelada'),
        ('draft', 'Planificando'),
        ('in_progress', 'En Desarrollo'),
        ('review', 'Revisando'),
        ('done', 'Terminada'),
    ], string='Estado', default='draft', tracking=True)
    company_id = fields.Many2one('res.company', string="Empresa Cliente", required=True)
    communicator_id = fields.Many2one('hr.employee', string='Comunicador', tracking=True)
    has_ios_support = fields.Boolean(string='Soporte iOS', tracking=True)
    project_hours_ids = fields.One2many('sge.project.hours', 'project_id', string='Horas Trabajadas')
    total_hours = fields.Float(string='Total de Horas', compute='_compute_total_hours', store=True, tracking=True)
    start_date = fields.Date(string='Fecha de Inicio', default=fields.Date.context_today, tracking=True)
    end_date = fields.Date(string='Fecha de Fin', tracking=True)
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
        self.state = 'cancelled'
        self.end_date = date.today()

    def action_set_draft(self):
        self.state = 'draft'
        if self.end_date:
            self.end_date = False

    def action_set_in_progress(self):
        self.state = 'in_progress'
        if self.end_date:
            self.end_date = False

    def action_set_review(self):
        self.state = 'review'
        if self.end_date:
            self.end_date = False

    def action_set_done(self):
        self.state = 'done'
        self.end_date = date.today()

    @api.model
    def action_mark_all_ios_support(self):
        domain = [('state', 'not in', ['cancelled', 'done'])]
        projects = self.env['sge.project'].search(domain)
        if projects:
            projects.write({'has_ios_support': True})
            _logger.info("Todos los proyectos activos han sido marcados como compatibles con iOS.")
        else:
            _logger.info("No se encontraron proyectos con el dominio especificado.")
        return {
            'type': 'ir.actions.client',
            'tag': 'reload',
        }

    @api.model
    def action_unmark_all_ios_support(self):
        domain = [('state', 'not in', ['cancelled', 'done'])]
        projects = self.env['sge.project'].search(domain)
        if projects:
            projects.write({'has_ios_support': False})
            _logger.info("Todos los proyectos activos han sido desmarcados como compatibles con iOS.")
        else:
            _logger.info("No se encontraron proyectos con el dominio especificado.")
        return {
            'type': 'ir.actions.client',
            'tag': 'reload',
        }