from django.urls import path
from .views import RegistroView, AdministradorPerfilView

urlpatterns = [
    path('registrarse/', RegistroView.as_view(), name='registrarse'),
    
    path('perfil/', AdministradorPerfilView.as_view(), name='perfil'),
    
]