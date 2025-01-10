{
    'name': "Gestión de proyectos",
    'summary': "Sistema de gestión de proyectos del grupo 2 de DAM",
    'description': """
        Módulo para TFG de SGE
    """,
    'author': "Grupo 2 DAM",
    'website': "https://fptxurdinaga.eus/",
    'category': 'Services/Project',
    'version': '1.0',
    'depends': ['base', 'mail', 'hr'],
    'data': [
        'security/ir.model.access.csv',
        'views/create_employee.xml',
        'views/create_company.xml',
        'views/project_views.xml',
        'views/company_views.xml',
        'views/employee_views.xml',
        'views/menus.xml',
    ],
    'demo': [
        'demo/demo.xml',
    ],
    'application': True,
    'installable': True,
}