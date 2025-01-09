{
    'name': "Gestión de proyectos",
    'summary': "Sistema de gestión de proyectos",
    'description': """
        Módulo para SGE kekw
    """,
    'author': "Son Janchez",
    'website': "https://github.com/S3Jon",
    'category': 'Services/Project',
    'version': '1.0',
    'depends': ['base', 'mail', 'hr'],
    'data': [
        'security/ir.model.access.csv',
        'views/create_employee.xml',
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